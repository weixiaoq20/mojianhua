package com.enjoy.network;

import com.enjoy.network.base.NetworkApi;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;

public abstract class OwnerApi extends NetworkApi {
    @Override
    protected Interceptor getInterceptor() {
        return null;
    }

    @Override
    protected <T> Function<T, T> getAppErrorHandler() {
        return null;
    }

    @Override
    public String getFormal() {
        return "xxxx";
    }

    @Override
    public String getTest() {
        return null;
    }
}
