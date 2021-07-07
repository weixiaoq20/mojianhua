package com.xiarui.base.mvvm.model;

public interface MvvmDataObserver<F> {
    void onSuccess(F t, boolean isFromCache);
    void onFailure(Throwable e);
}
