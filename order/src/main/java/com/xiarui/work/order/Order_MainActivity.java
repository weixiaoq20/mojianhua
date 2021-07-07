package com.xiarui.work.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xiangxue.arouter_annotation.ARouter;
import com.xiangxue.arouter_annotation.Parameter;
import com.xiangxue.arouter_api.ParameterManager;
import com.xiangxue.arouter_api.RouterManager;
import com.xiangxue.common.utils.Cons;
import com.xiarui.work.order.test.LoginAty;

@ARouter(path = "/order/Order_MainActivity")
public class Order_MainActivity extends AppCompatActivity {

    @Parameter
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_main);

        // 用到才加载 == 懒加载
        ParameterManager.getInstance().loadParameter(this);

        Log.e(Cons.TAG, "order/Order_MainActivity name:" + name);
    }

    public void jumpApp(View view) {
        Toast.makeText(this, "路由还没有写好呢，别猴急...", Toast.LENGTH_SHORT).show();

        RouterManager.getInstance()
                .build("/app/MainActivity")
                .withString("next", "1111")
                .navigation(this); // 组件和组件通




    }

    public void jumpPersonal(View view) {
        // Toast.makeText(this, "路由还没有写好呢，别猴急...", Toast.LENGTH_SHORT).show();

        RouterManager.getInstance()
                .build("/personal/Personal_MainActivity")
                .withString("name", "李元霸")
                .withString("sex", "男")
                .withInt("age", 99)
                .navigation(this);
    }

    public void mvvmtiao(View view) {
        Order_MainActivity.this.startActivity(new Intent(Order_MainActivity.this, LoginAty.class));
        finish();

    }
}
