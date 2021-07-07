package com.enjoy.network.interceptors;

import android.util.Log;


import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 作者：夏瑞
 * 2017/8/22 19:09
 * 注释:网络请求拦截器
 * 邮箱:1970258244@qq.com
 */
public class RequestPrintInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();
        HttpUrl.Builder builder = originalHttpUrl.newBuilder();
        HttpUrl url = builder.build();
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .url(url)
                .method(originalRequest.method(), originalRequest.body());
        Request request = requestBuilder.build();
        String parameterString = null;
        if (request.body() != null) {
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            parameterString = buffer.readString(Charset.forName("UTF-8"));
        }

        Response response = chain.proceed(request);
        MediaType contentType = null;
        String bodyString = null;
        if (response.body() != null) {
            contentType = response.body().contentType();
            bodyString = response.body().string();
        }

        Log.e("RequestPrintInterceptor", "response: " + response.code() + "\n" + bodyString);
        if (response.body() != null) {// 深坑！打印body后原ResponseBody会被清空，需要重新设置body
            ResponseBody body = ResponseBody.create(contentType, bodyString);
            return response.newBuilder().body(body).build();
        } else {
            return response;
        }
    }

}
