package com.enjoy.network;

import com.enjoy.network.base.NetworkApi;
import java.io.IOException;
import io.reactivex.functions.Function;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 实现不同的ip地址服务器访问网络框架的
 */
public class OwnerWorkApi extends NetworkApi {
    private static volatile OwnerWorkApi sInstance;

    public static OwnerWorkApi getInstance() {
        if (sInstance == null) {
            synchronized (OwnerWorkApi.class) {
                if (sInstance == null) {
                    sInstance = new OwnerWorkApi();
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
        };
    }

    @Override
    protected <T> Function<T, T> getAppErrorHandler() {
        return null;
    }


    @Override
    public String getFormal() {
        return "http://oa.abtu.edu.cn";
    }

    @Override
    public String getTest() {
        return "http://oa.abtu.edu.cn";
    }
}
