package com.xiarui.work.order.api;



import com.xiarui.work.order.contance.AddressUrl;
import com.xiarui.work.order.enity.LoginBean;
import com.xiarui.work.order.enity.OAResponseBean;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface OrderApiInterface {

    /**
     * 账号密码登陆
     *
     * @param mHash
     * @return
     */

    @FormUrlEncoded
    @POST(AddressUrl.APP_URL_V1 + "login/uservalid")
    Observable<LoginBean> getLoginUserVaild(@FieldMap HashMap<String, Object> mHash);


    @POST("/seeyon/rest/token")
    Observable<OAResponseBean> getTokenOaData(@Body RequestBody body);





}
