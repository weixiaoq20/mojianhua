package com.xiarui.work.personal.debug;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xiangxue.common.utils.Cons;
import com.xiarui.work.personal.Personal_MainActivity;
import com.xiarui.work.personal.R;

// TODO 注意：这是 测试环境下的代码 Debug
public class Personal_DebugActivity extends Personal_DebugBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity_debug);

        Log.e(Cons.TAG, "personal/debug/Personal_DebugActivity");
    }

    public void jump(View view) {
        Intent intent = new Intent(this, Personal_MainActivity.class);
        startActivity(intent);
    }
}
