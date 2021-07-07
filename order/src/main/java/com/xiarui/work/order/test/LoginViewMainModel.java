package com.xiarui.work.order.test;


import com.xiarui.work.order.enity.LoginBean;
import com.xiarui.base.mvvm.viewmodel.BaseMvvmViewModel;


public class LoginViewMainModel extends BaseMvvmViewModel<LoginMainModel, LoginBean> {

    private LoginMainModel loginModel = null;

    public LoginViewMainModel() {

    }


    public void setUsername(String username) {
        if (loginModel!=null){
            loginModel.setUsername(username);
        }
    }


    public void setPwd(String pwd) {
        if (loginModel!=null){
            loginModel.setPassword(pwd);
        }
    }

    public void setType(int type) {
        if (loginModel!=null){
            loginModel.setType(type);
        }
    }




    @Override
    public LoginMainModel createModel() {
        if (loginModel == null) {
            loginModel = new LoginMainModel(viewStatusLiveData);
        }
        return loginModel;
    }

}
