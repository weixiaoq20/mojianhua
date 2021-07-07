package com.enjoy.network.observer;

import android.util.Log;


import com.enjoy.network.errorhandler.ExceptionHandle;
import com.xiarui.base.mvvm.model.BaseMvvmModel;
import com.xiarui.base.mvvm.model.MvvmDataObserver;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BaseObserver<MMM> implements Observer<MMM> {
    BaseMvvmModel baseModel;
    MvvmDataObserver<MMM> mvvmDataObserver;
    public BaseObserver(BaseMvvmModel baseModel, MvvmDataObserver<MMM> mvvmDataObserver) {
        this.baseModel = baseModel;
        this.mvvmDataObserver = mvvmDataObserver;
    }


    @Override
    public void onSubscribe(Disposable d) {
        if(baseModel != null){
            baseModel.addDisposable(d);
        }
    }

    @Override
    public void onNext(MMM mm) {
        mvvmDataObserver.onSuccess(mm, false);
    }

    @Override
    public void onError(Throwable e) {
        Log.e("json",e.getMessage());
        if(e instanceof ExceptionHandle.ResponeThrowable){
            mvvmDataObserver.onFailure(e);
        } else {
            mvvmDataObserver.onFailure(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {

    }
}
