package com.enjoy.network;

import com.enjoy.network.base.NetworkApi;
import com.enjoy.network.beans.IBaseResponse;
import com.enjoy.network.errorhandler.ExceptionHandle;

import java.io.IOException;

import io.reactivex.functions.Function;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AllNetWorkApi extends NetworkApi {
    private static volatile AllNetWorkApi sInstance;

    public static AllNetWorkApi getInstance() {
        if (sInstance == null) {
            synchronized (AllNetWorkApi.class) {
                if (sInstance == null) {
                    sInstance = new AllNetWorkApi();
                }
            }
        }
        return sInstance;
    }

    public static <T> T getService(Class<T> service) {
        return getInstance().getRetrofit(service).create(service);
    }

    @Override
    protected Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //处理自己的拦截
                Request request = chain.request();
                if (request.method().equals("POST")) {
                    RequestBody body = request.body();
                    if (body instanceof FormBody) {
                        // 构造新的请求表单
                        FormBody.Builder builder = new FormBody.Builder();
                        FormBody formBody = (FormBody) body;
                        //将以前的参数添加
                        for (int i = 0; i < formBody.size(); i++) {
                            builder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                        }
                        //追加新的参数
                        request = request
                                .newBuilder()
                                .post(builder.build())
                                .build();
                    }
                } else {
                    HttpUrl url = request.url();
                    HttpUrl newUrl = url.newBuilder()
                            .build();
                    request = request.newBuilder()
                            .url(newUrl).build();
                }
                return chain.proceed(request);
            }
        }

                ;
    }

    @Override
    protected <T> Function<T, T> getAppErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T response) throws Exception {
                // 1代表成功，0代表失败
                if (response instanceof IBaseResponse && ((IBaseResponse) response).code != 1) {
                    ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
                    exception.code = ((IBaseResponse) response).code;
                    exception.message = ((IBaseResponse) response).msg != null ? ((IBaseResponse) response).msg : "";
                    throw exception;
                }
                return response;
            }
        };
    }

    @Override
    public String getFormal() {
        return "http://tyrz.abtu.edu.cn";
    }

    @Override
    public String getTest() {
        return "http://tyrz.abtu.edu.cn";
    }
}
