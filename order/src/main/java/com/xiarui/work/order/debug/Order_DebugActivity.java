package com.xiarui.work.order.debug;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xiangxue.common.utils.Cons;
import com.xiarui.work.order.Order_MainActivity;
import com.xiarui.work.order.R;

// TODO 注意：这是 测试环境下的代码 Debug
public class Order_DebugActivity extends Order_DebugBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_debug);

        Log.e(Cons.TAG, "order/debug/Order_DebugActivity");
    }

    public void jumpPersonal(View view) {
        Intent intent = new Intent(this, Order_MainActivity.class);
        startActivity(intent);
    }


}
