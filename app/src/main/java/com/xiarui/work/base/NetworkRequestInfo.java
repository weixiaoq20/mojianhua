package com.xiarui.work.base;

import android.app.Application;

import com.enjoy.network.BuildConfig;
import com.enjoy.network.base.INetworkRequiredInfo;


/**
 * 请求需要的http头信息
 */
public class NetworkRequestInfo implements INetworkRequiredInfo {
    private Application mApplication;

    public NetworkRequestInfo(Application application) {
        this.mApplication = application;
    }

    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public Application getApplicationContext() {
        return mApplication;
    }
}
