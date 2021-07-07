package com.xiarui.base;

import android.app.Application;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;


public class BaseApplication extends TinkerApplication {
    public BaseApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL,
                ApplicationLike.class.getName(),
                "com.tencent.tinker.loader.TinkerLoader", false);

    }

    public static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication=this;
    }


    protected void initAppLication(){

    }


}
