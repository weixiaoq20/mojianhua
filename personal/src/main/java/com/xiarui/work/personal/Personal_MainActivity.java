package com.xiarui.work.personal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.versionedparcelable.ParcelField;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiangxue.arouter_annotation.ARouter;
import com.xiangxue.arouter_annotation.Parameter;
import com.xiangxue.arouter_api.ParameterManager;
import com.xiangxue.common.bean.Personal;
import com.xiangxue.common.bean.Student;
import com.xiangxue.common.order.OrderDrawable;
import com.xiangxue.common.order.net.OrderAddress;
import com.xiangxue.common.order.net.OrderBean;
import com.xiangxue.common.utils.Cons;

import java.io.IOException;

@ARouter(path = "/personal/Personal_MainActivity")
public class Personal_MainActivity extends AppCompatActivity {

    @Parameter
    String name; // 序列号 String

    @Parameter
    String sex;

    @Parameter
    int age = 9;   // 序列号  int

    @Parameter
    Student student;

    @Parameter(name = "/order/getDrawable")
    OrderDrawable orderDrawable; // 公共基础库common

    // 拿order模块的 网络请求功能
    @Parameter(name = "/order/getOrderBean")
    OrderAddress orderAddress;


    @Parameter
    Personal personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity_main);

        // 模仿人家了
        // bind(this);

        ParameterManager.getInstance().loadParameter(this);

        Log.d(Cons.TAG, "onCreate: Personal_MainActivity name:" + name + ", sex:" + sex + ", age:" + age);


        // app模块本来就可以直接加载其他模块的资源   personal
        // 拿到 order模块的图片 在app模块展示
        int drawableId = orderDrawable.getDrawable();
        ImageView img = findViewById(R.id.img);
        img.setImageResource(drawableId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OrderBean orderBean = orderAddress.getOrderBean("aa205eeb45aa76c6afe3c52151b52160", "144.34.161.97");
                    Log.e(Cons.TAG, "从Personal跨组件到Order，并使用Order网络请求功能：" + orderBean.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 输出 Student
        Log.d(Cons.TAG, "我的Personal onCreate 对象的传递:" + student.toString());
        Log.d(Cons.TAG, "我的Personal onCreate 对象的传递:personal!=null" + personal.toString());
    }

    public void jumpApp(View view) {
        Toast.makeText(this, "路由还没有写好呢，别猴急...", Toast.LENGTH_SHORT).show();
    }

    public void jumpOrder(View view) {
        Toast.makeText(this, "路由还没有写好呢，别猴急...", Toast.LENGTH_SHORT).show();
    }
}
