//package com.xiarui.work;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.databinding.library.baseAdapters.BuildConfig;
//
//import android.app.Person;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.xiangxue.arouter_annotation.ARouter;
//import com.xiangxue.arouter_annotation.Parameter;
//import com.xiangxue.arouter_annotation.bean.RouterBean;
//import com.xiangxue.arouter_api.ARouterPath;
//import com.xiangxue.arouter_api.ParameterManager;
//import com.xiangxue.arouter_api.RouterManager;
//import com.xiangxue.common.bean.Student;
//import com.xiangxue.common.order.OrderDrawable;
//import com.xiangxue.common.order.user.IUser;
//import com.xiangxue.common.utils.Cons;
//import com.xiarui.work.order.contance.AddressUrl;
//
//import java.util.Map;
//
//@ARouter(path = "/app/MainActivity")
//public class MainActivity extends AppCompatActivity {
//
//    @Parameter(name = "/order/getDrawable")
//    OrderDrawable orderDrawable; // 公共基础库common
//
//    @Parameter(name = "/order/getUserInfo")
//    IUser iUser; // 公共基础库common
//
//    @Parameter
//    String next;
//
//    boolean isRelease;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        if (isRelease) {
//            Log.e(Cons.TAG, "当前为：集成化模式，除app可运行，其他子模块都是Android Library");
//        } else {
//            Log.e(Cons.TAG, "当前为：组件化模式，app/order/personal子模块都可独立运行");
//        }
//
//        // 懒加载方式，跳到哪加载哪个类
//        ParameterManager.getInstance().loadParameter(this);
//
//        // app模块本来就可以直接加载其他模块的资源   personal
//        // 拿到 order模块的图片 在app模块展示
//        int drawableId = orderDrawable.getDrawable();
//        ImageView img = findViewById(R.id.img);
//        img.setImageResource(drawableId);
//
//        // 我输出 order模块的Bean休息
//        Log.d(Cons.TAG, "order的Bean onCreate: " +  iUser.getUserInfo().toString());
//        Toast.makeText(MainActivity.this,iUser.getUserInfo().toString(),0).show();
//
//
//        Log.d(Cons.TAG, "/order/Order_MainActivity onCreate: " +  next);
//    }
//
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        Log.d(Cons.TAG, "/order/Order_MainActivity onNewIntent: " +  next);
//    }
//
//    public void jumpOrder(View view) {
//        /*Intent intent = new Intent(this, Order_MainActivity.class);
//        intent.putExtra("name", "derry");
//        startActivity(intent);*/
//
//        // 使用我们自己写的路由 跳转交互
//        RouterManager.getInstance()
//                .build(AddressUrl.ORDER_LOGINATY)
//                .withString("name", "杜子腾")
//                .navigation(this); // 组件和组件通信
//    }
//
//    public void jumpPersonal(View view) {
//        // 以前是这样跳转
//        /*Intent intent = new Intent(this, Personal_MainActivity.class);
//        intent.putExtra("name", "derry");
//        startActivity(intent);*/
//
//        // 现在是这样跳转  目前还要写这么多代码，是不是非常累
//
//        // TODO 最终的成效：用户 一行代码搞定，同时还可以传递参数，同时还可以懒加载
//        /*ARouter$$Group$$personal group$$personal = new ARouter$$Group$$personal();
//        Map<String, Class<? extends ARouterPath>> groupMap = group$$personal.getGroupMap();
//        Class<? extends ARouterPath> myClass = groupMap.get("personal");
//
//        try {
//            ARouter$$Path$$personal path = (ARouter$$Path$$personal) myClass.newInstance();
//            Map<String, RouterBean> pathMap = path.getPathMap();
//            RouterBean bean = pathMap.get("/personal/Personal_MainActivity");
//
//            if (bean != null) {
//                Intent intent = new Intent(this, bean.getMyClass());
//                startActivity(intent);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }*/
//
//        Student student = new Student("Derry大大", "男", 99);
//
//        // 使用我们自己写的路由 跳转交互
//        RouterManager.getInstance()
//                .build("/personal/Personal_MainActivity")
//                .withString("name", "史甄湘")
//                .withString("sex", "男")
//                .withInt("age", 99)
//                .withSerializable("student", student)
//                .navigation(this);
//    }
//}
