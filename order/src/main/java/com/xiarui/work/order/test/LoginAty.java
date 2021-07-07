package com.xiarui.work.order.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.enjoy.network.OwnerWorkApi;
import com.enjoy.network.observer.IBaseObserver;
import com.xiangxue.arouter_annotation.ARouter;
import com.xiangxue.arouter_api.RouterManager;
import com.xiarui.work.order.R;
import com.xiarui.work.order.api.OrderApiInterface;
import com.xiarui.work.order.contance.AddressUrl;
import com.xiarui.work.order.databinding.OrderLoginBinding;
import com.xiarui.work.order.enity.LoginBean;
import com.xiarui.work.order.enity.OAResponseBean;
import com.xiarui.work.order.enity.request.OaRequest;
import com.xiarui.base.mvvm.view.BaseMvvmAppComAty;
import com.xiarui.base.preference.JsonUtil;
import com.xiarui.base.utlis.LogUtil;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

@ARouter(path = AddressUrl.ORDER_LOGINATY)
public class LoginAty extends BaseMvvmAppComAty<OrderLoginBinding, LoginViewMainModel, LoginBean> {
    private LoginViewMainModel loginMainModel = null;

    @Override
    protected String getAppcommAtyTag() {
        return LoginAty.class.getSimpleName();
    }

    @Override
    public void onNetworkResponded(List<LoginBean> loginBeans, boolean isDataUpdated) {
        if (loginBeans != null&&loginBeans.size()>0){
            String result = loginBeans.get(0).obj.toString();
            LogUtil.e(TAG,"====>>>>>>>>>>>==="+result);
        }

    }


    @Override
    public int getLayoutId() {
        return R.layout.order_login;
    }


    @Override
    public LoginViewMainModel getViewModel() {
        if (loginMainModel == null) {
            loginMainModel = new LoginViewMainModel();
            loginMainModel.setType(1);
        }
        return loginMainModel;
    }

    @Override
    protected void initListener() {
        viewDataBinding.oa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OaRequest oaRequest=new OaRequest();
                oaRequest.setLoginName("20069618");
                oaRequest.setPassword("h512345");
                oaRequest.setUserName("H5JC");
                oaRequest.setUserAgentFrom("weixin");


                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        JsonUtil.toJson(oaRequest));
                OwnerWorkApi.getService(OrderApiInterface.class)
                        .getTokenOaData(body)
                        .compose(OwnerWorkApi.getInstance().applySchedulers(new IBaseObserver<OAResponseBean>() {

                            @Override
                            public void onSuccess(OAResponseBean weather) {
                                Log.i(TAG, "onSuccess : " + weather.toString());


                                RouterManager.getInstance()
                                        .build("/app/MainActivity")
                                        .withString("next", "1111")
                                        .navigation(LoginAty.this); // 组件和组件通信
                            }

                            @Override
                            public void onFailure(Throwable e) {

                            }

                        }));

            }
        });


        viewDataBinding.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginMainModel != null) {
                    loginMainModel.setPwd("11yy");
                    loginMainModel.setUsername("20069618");
                }
                viewmodel.refresh();
            }
        });
    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    EventLinstener eventListener;

    @Override
    protected void initData(Bundle savedInstanceState) {


    }

    @Override
    protected View getLoadSirView() {
        return viewDataBinding.allView;
    }

    @Override
    protected void onViewCreated() {
        if (eventListener == null) {
            eventListener = new EventLinstener();
        }
        viewDataBinding.setEventlistener(eventListener);
        loginMainModel.refresh();
        showSuccess();
    }

    public class EventLinstener {


    }
}
