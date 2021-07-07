package com.xiarui.work;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.xiangxue.arouter_annotation.ARouter;

@ARouter(path = "/app/Main2Activity")
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
