package com.xiarui.base.mvvm.model;


public interface IBaseModeListener<DATA> {
    void onLoadSuccess(BaseMvvmModel model, DATA data, PagingResult... result);
    void onLoadFail(BaseMvvmModel model, String message, PagingResult... result);
}
