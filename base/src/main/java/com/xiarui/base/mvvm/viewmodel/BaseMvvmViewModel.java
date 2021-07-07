package com.xiarui.base.mvvm.viewmodel;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.xiarui.base.mvvm.model.BaseMvvmModel;
import com.xiarui.base.mvvm.model.IBaseModeListener;
import com.xiarui.base.mvvm.model.PagingResult;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMvvmViewModel<MODEL extends BaseMvvmModel, DATA> extends ViewModel implements LifecycleObserver, IBaseModeListener<List<DATA>> {
    public MutableLiveData<List<DATA>> datalist = new MutableLiveData<>();
    protected MODEL model;

    public MutableLiveData<ViewStatus> viewStatusLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public BaseMvvmViewModel() {
    }

    public abstract MODEL createModel();


    public void refresh() {
        viewStatusLiveData.setValue(ViewStatus.LOADING);
        createAndRegisterModel();
        if (model != null) {
            model.refresh();
        }
    }

    private void createAndRegisterModel() {
        if (model == null) {
            model = createModel();
            if (model != null) {
                model.register(this);
            } else {

            }
        }
    }

    public void getCachedDataAndLoad() {
        viewStatusLiveData.setValue(ViewStatus.LOADING);
        createAndRegisterModel();
        if (model != null) {
            model.getCachedDataAndLoad();
        }
    }

    public void loadNextPage() {
        createAndRegisterModel();
        if (model != null) {
            model.loadNextPage();
        }
    }

    @Override
    public void onLoadSuccess(BaseMvvmModel model, List<DATA> data, PagingResult... result) {
        if (model.ismIsPaging()) {
            if (result[0].isEmpty) {
                if (result[0].isFirstPage) {
                    viewStatusLiveData.postValue(ViewStatus.EMPTY);
                } else {
                    viewStatusLiveData.postValue(ViewStatus.NO_MORE_DATA);
                }
            } else {
                if (result[0].isFirstPage && datalist != null) {
                    datalist.postValue(data);
                } else {
                    if (datalist != null && datalist.getValue() != null) {
                        datalist.getValue().addAll(data);
                        datalist.postValue(datalist.getValue());
                    }

                }
                viewStatusLiveData.postValue(ViewStatus.SHOW_CONTENT);
            }
        } else {
            datalist.postValue(data);
            viewStatusLiveData.postValue(ViewStatus.SHOW_CONTENT);
        }
    }

    @Override
    public void onLoadFail(BaseMvvmModel model, String message, PagingResult... result) {
        errorMessage.postValue(message);
        if (result == null || result.length == 0) {
            viewStatusLiveData.postValue(ViewStatus.SHOW_COENT_ERROR);
        } else {
            if (result != null && result[0].isFirstPage) {
                viewStatusLiveData.postValue(ViewStatus.REFRESH_ERROR);
            } else {
                viewStatusLiveData.postValue(ViewStatus.LOAD_MORE_FAILED);
            }
        }

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.cancel();
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void onCreate() {
        Log.e("ddd","onCreate");
        if (datalist == null || datalist.getValue() == null || datalist.getValue().size() == 0) {
            createAndRegisterModel();
            if (model != null) {
                model.getCachedDataAndLoad();
            }
        } else {
            datalist.postValue(datalist.getValue());
            viewStatusLiveData.postValue(viewStatusLiveData.getValue());
        }
    }
}