package com.xiarui.work.order.test;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.enjoy.network.AllNetWorkApi;
import com.enjoy.network.observer.BaseObserver;
import com.xiarui.work.order.api.OrderApiInterface;
import com.xiarui.work.order.enity.LoginBean;
import com.xiarui.base.mvvm.model.BaseMvvmModel;
import com.xiarui.base.mvvm.viewmodel.ViewStatus;
import com.xiarui.base.utlis.utlis.MD5Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginMainModel extends BaseMvvmModel<LoginBean, List<LoginBean>> {
    private String username, password;
    private int type=-1;

    private String refresh_token;

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MutableLiveData<ViewStatus> viewStatusLiveData;
    public LoginMainModel(MutableLiveData<ViewStatus> viewStatusLiveData) {
        super(false, null, null);
        this.viewStatusLiveData=viewStatusLiveData;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void load() {
        if (1==type||(-1==type)){
            if (!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
                HashMap<String, Object> mHashMap = new HashMap<>();
                mHashMap.put("username", "20069618");
                mHashMap.put("password", MD5Util.md5Decode32("11yy"));
                Log.e("password", "====pwd===" + MD5Util.md5Decode32("11yy"));
                AllNetWorkApi.getService(OrderApiInterface.class)
                        .getLoginUserVaild(mHashMap)
                        .compose(AllNetWorkApi.getInstance().applySchedulers(new BaseObserver<LoginBean>(this, this)));


            }else{
                this.viewStatusLiveData.setValue(ViewStatus.SHOW_CONTENT);
                setmIsLoading(false);
            }
        }
        else if (2==type){
            if (!TextUtils.isEmpty(username)){
                HashMap<String, Object> mHashMap = new HashMap<>();
                mHashMap.put("username", "20069618");
                mHashMap.put("password", MD5Util.md5Decode32("11yy"));
                Log.e("password", "====pwd===" + MD5Util.md5Decode32("11yy"));
                AllNetWorkApi.getService(OrderApiInterface.class)
                        .getLoginUserVaild(mHashMap)
                        .compose(AllNetWorkApi.getInstance().applySchedulers(new BaseObserver<LoginBean>(this, this)));

            }else{
                this.viewStatusLiveData.setValue(ViewStatus.SHOW_CONTENT);
                setmIsLoading(false);
            }

        }

    }

    @Override
    public void onSuccess(LoginBean login, boolean isFromCache) {
        List<LoginBean> loginBeans=new ArrayList<>();
        if (login!=null){
            loginBeans.add(login);
        }
        notifyResultToListener(login, loginBeans, isFromCache);
    }

    @Override
    public void onFailure(Throwable e) {
        loadFail(e.getMessage());
    }
}
