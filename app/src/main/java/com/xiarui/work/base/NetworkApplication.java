package com.xiarui.work.base;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import androidx.multidex.MultiDex;

import com.enjoy.network.base.NetworkApi;
import com.tencent.bugly.beta.Beta;
import com.xiarui.base.BaseApplication;
import com.xiarui.base.preference.PreferencesUtil;
import com.xiarui.base.utlis.AppManagerUtil;
import java.util.List;
/**
 * application 开关
 */
public class NetworkApplication extends BaseApplication {

    private Context mContext;
    private NetworkApplication mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mApp=this;
        PreferencesUtil.init(mApp);
        initAppLication();
        initAppOperation();

    }


    private void initAppOperation() {
        NetworkApi.chooseEnvironment(this);
        NetworkApi.init(new NetworkRequestInfo(this));

        PreferencesUtil.init(this);
        //专为扩展库所用
        AppManagerUtil.getInstance().setApplication(this);
    }

    @Override
    protected void initAppLication() {
        super.initAppLication();
        if (!shouldInit()) {
            return;
        }
        //解决android N（>=24）系统以上分享 路径为file://时的 android.os.FileUriExposedException异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

    }
    /**
     * 判断主进程是否初始化过
     *
     * @return
     */
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

        // 安装tinker
        //TinkerManager.installTinker(this); //替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }
}
