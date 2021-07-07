package com.xiarui.work.order.debug;

import android.app.Application;
import android.util.Log;
import com.xiangxue.common.utils.Cons;

// TODO 注意：这是 测试环境下的代码 Debug
public class Order_DebugApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(Cons.TAG, "order/debug/Order_DebugApplication");
    }
}
