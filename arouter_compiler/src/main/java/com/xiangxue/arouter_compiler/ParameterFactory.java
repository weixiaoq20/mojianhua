package com.xiangxue.arouter_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.xiangxue.arouter_annotation.Parameter;
import com.xiangxue.arouter_compiler.utils.ProcessorConfig;
import com.xiangxue.arouter_compiler.utils.ProcessorUtils;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/*
   目的 生成以下代码：
        @Override
        public void getParameter(Object targetParameter) {
              Personal_MainActivity t = (Personal_MainActivity) targetParameter;
              t.name = t.getIntent().getStringExtra("name");
              t.sex = t.getIntent().getStringExtra("sex");
        }
 */
public class ParameterFactory {

    // 方法的构建
    private MethodSpec.Builder method;

    // 类名，如：MainActivity  /  Personal_MainActivity
    private ClassName className;

    // Messager用来报告错误，警告和其他提示信息
    private Messager messager;

    // type(类信息)工具类，包含用于操作TypeMirror的工具方法
    private Types typeUtils;

    // 获取元素接口信息（生成类文件需要的接口实现类）
    private TypeMirror callMirror;

    private Elements elementUtils; // 类信息

    // 不想用户使用此构造函数，必须使用Builder设计模式
    private ParameterFactory(Builder builder) {
        this.messager = builder.messager;
        this.className = builder.className;
        this.typeUtils = builder.typeUtils;

        // 生成此方法
        // 通过方法参数体构建方法体：public void getParameter(Object target) {
        method = MethodSpec.methodBuilder(ProcessorConfig.PARAMETER_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(builder.parameterSpec);

        // call自描述 Call接口的
        this.callMirror = builder.elementUtils
                .getTypeElement(ProcessorConfig.CALL)
                .asType();

        this.elementUtils = builder.elementUtils;
    }

    /**
     * 只有一行
     * Personal_MainActivity t = (Personal_MainActivity) targetParameter;
     */
    public void addFirstStatement() {
        method.addStatement("$T t = ($T) " + ProcessorConfig.PARAMETER_NAME, className, className);
    }

    public MethodSpec build() {
        return method.build();
    }

    /**
     * 多行 循环 复杂
     * 构建方体内容，如：t.s = t.getIntent.getStringExtra("s");
     *
     * @param element 被注解的属性元素
     *
     *
     *     Personal_MainActivity t = (Personal_MainActivity) targetParameter;
     *     t.name = t.getIntent().getStringExtra("name");
     *     t.sex = t.getIntent().getStringExtra("sex");
     *     t.age = t.getIntent().getIntExtra("age", t.age);
     *     t.student=(Student)t.getIntent().getSerializableExtra("student");
     *     t.orderDrawable = (OrderDrawable) RouterManager.getInstance().build("/order/getDrawable").navigation(t);
     *     t.orderAddress = (OrderAddress) RouterManager.getInstance().build("/order/getOrderBean").navigation(t);
     *     t.getIntent().getParcelableExtra("personal");
     */
    public void buildStatement(Element element) {
        // 遍历注解的属性节点 生成函数体
        TypeMirror typeMirror = element.asType();

        // 获取 TypeKind 枚举类型的序列号
        int type = typeMirror.getKind().ordinal();

        // 获取属性名  name  age  sex
        String fieldName = element.getSimpleName().toString();

        // 获取注解的值
        String annotationValue = element.getAnnotation(Parameter.class).name();

        // 配合： t.age = t.getIntent().getBooleanExtra("age", t.age ==  9);
        // 判断注解的值为空的情况下的处理（注解中有name值就用注解值）
        annotationValue = ProcessorUtils.isEmpty(annotationValue) ? fieldName : annotationValue;

        // TODO 最终拼接的前缀：
        String finalValue = "t." + fieldName;

        // t.s = t.getIntent().
        // TODO t.name = t.getIntent().getStringExtra("name");
        String methodContent = finalValue + " = t.getIntent().";

        // TypeKind 枚举类型不包含String
        if (type == TypeKind.INT.ordinal()) {
            // t.s = t.getIntent().getIntExtra("age", t.age);
            methodContent += "getIntExtra($S, " + finalValue + ")";  // 有默认值
        } else if (type == TypeKind.BOOLEAN.ordinal()) {
            // t.s = t.getIntent().getBooleanExtra("isSuccess", t.age);
            methodContent += "getBooleanExtra($S, " + finalValue + ")";  // 有默认值
        } else { // String 类型，没有序列号的提供 需要我们自己完成
            // t.s = t.getIntent.getStringExtra("s");
            // typeMirror.toString() java.lang.String

            System.out.println("typeMirror.toString()" + typeMirror.toString());

            if (typeMirror.toString().equalsIgnoreCase(ProcessorConfig.STRING)) {
                // String类型
                methodContent += "getStringExtra($S)"; // 没有默认值
            } else if (typeUtils.isSubtype(typeMirror, callMirror)) { // 你居然实现了Call接口
//                t.orderAddress = (OrderAddress) RouterManager.getInstance().build("/order/getOrderBean").navigation(t);
                // t.orderDrawable = (OrderDrawable) RouterManager.getInstance().build("/order/getDrawable").navigation(t);
                methodContent = "t." + fieldName + " = ($T) $T.getInstance().build($S).navigation(t)";
                method.addStatement(methodContent,
                        TypeName.get(typeMirror),
                        ClassName.get(ProcessorConfig.AROUTER_API_PACKAGE, ProcessorConfig.ROUTER_MANAGER),
                        annotationValue);
                return;

                //methodContent = "t.getIntent().getSerializableExtra($S)";
                //         Ab a=(Ab)getIntent().getParcelableExtra("aa");

            } else { // 对象的传输
                //  Parcelable
                TypeMirror typeParcelable = elementUtils.getTypeElement(ProcessorConfig.PARCELABLE).asType();
                if (typeUtils.isSubtype(typeMirror, typeParcelable)) {
                    methodContent = "t.getIntent().getParcelableExtra($S)";
                    String resultline = finalValue + "=($T)" + methodContent;
                    method.addStatement(resultline, ClassName.get(element.asType()), annotationValue);
                    messager.printMessage(Diagnostic.Kind.NOTE,"======pppp====="+ resultline);
                    return;

                } else {
                    //Serializable
                    methodContent = "t.getIntent().getSerializableExtra($S)";
                }

            }
        }
        if (methodContent.contains("Serializable")) { // 健壮代码
            // t.student=(Student) t.getIntent().getSerializableExtra("student");  注意：为了强转
            method.addStatement(finalValue + "=($T)" + methodContent, ClassName.get(element.asType()), annotationValue);
        }    // 健壮代码
        else if (methodContent.endsWith(")")) { // 抱歉  全部的 getBooleanExtra  getIntExtra   getStringExtra
            // 参数二 9 赋值进去了
            // t.age = t.getIntent().getBooleanExtra("age", t.age ==  9);
            method.addStatement(methodContent, annotationValue);
        } else {
            messager.printMessage(Diagnostic.Kind.ERROR, "目前暂支持String、int、boolean传参");

        }
    }

    /**
     * 为了完成Builder构建者设计模式
     */
    public static class Builder {

        // Messager用来报告错误，警告和其他提示信息
        private Messager messager;

        // 操作Element工具类 (类、函数、属性都是Element)
        private Elements elementUtils;

        // type(类信息)工具类，包含用于操作TypeMirror的工具方法
        private Types typeUtils;

        // 类名，如：MainActivity
        private ClassName className;

        // 方法参数体
        private ParameterSpec parameterSpec;

        public Builder(ParameterSpec parameterSpec) {
            this.parameterSpec = parameterSpec;
        }

        public Builder setMessager(Messager messager) {
            this.messager = messager;
            return this;
        }

        public Builder setElementUtils(Elements elementUtils) {
            this.elementUtils = elementUtils;
            return this;
        }

        public Builder setTypeUtils(Types typeUtils) {
            this.typeUtils = typeUtils;
            return this;
        }

        public Builder setClassName(ClassName className) {
            this.className = className;
            return this;
        }

        public ParameterFactory build() {
            if (parameterSpec == null) {
                throw new IllegalArgumentException("parameterSpec方法参数体为空");
            }

            if (className == null) {
                throw new IllegalArgumentException("方法内容中的className为空");
            }

            if (messager == null) {
                throw new IllegalArgumentException("messager为空，Messager用来报告错误、警告和其他提示信息");
            }

            return new ParameterFactory(this);
        }
    }
}
