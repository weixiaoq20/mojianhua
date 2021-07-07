package com.xiarui.base.mvvm.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadLayout;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.xiarui.base.R;
import com.xiarui.base.loadsir.CustomCallback;
import com.xiarui.base.loadsir.EmptyCallback;
import com.xiarui.base.loadsir.ErrorCallback;
import com.xiarui.base.loadsir.LoadingCallback;
import com.xiarui.base.loadsir.TimeoutCallback;
import com.xiarui.base.mvvm.viewmodel.BaseMvvmViewModel;
import com.xiarui.base.mvvm.viewmodel.ViewStatus;
import com.xiarui.base.utlis.ToastUtil;

import java.util.List;

public abstract class BaseMvvmFragment<VIEW extends ViewDataBinding,
        VIEWMODEL extends BaseMvvmViewModel, DATA> extends Fragment implements Observer {
    protected VIEWMODEL viewmodel;
    protected VIEW viewDataBinding;
    private LoadService mLoadService;

    protected BaseMvvmAppComAty mvvmAppComAty;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mvvmAppComAty=(BaseMvvmAppComAty)activity;

    }

    protected abstract String getFragmentTag();

    public abstract void onNetworkResponded(List<DATA> dataList, boolean isDataUpdated);

    public abstract @LayoutRes
    int getLayoutId();
    private View view;
    public abstract VIEWMODEL getViewModel();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getFragmentTag(), "onCreate...");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.e(getFragmentTag(), "onCreateView....");
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        if (view==null){
            view=viewDataBinding.getRoot();
        }

        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .build();
        mLoadService = loadSir.register(getLoadSirView(), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                viewmodel.refresh();
            }
        });

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;

    }

    Handler mHandler;
    protected abstract View getLoadSirView();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //消息处理器
        mHandler = new Handler(mvvmAppComAty.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //接受内部信息要重写该方法
                onHandlerMessage(msg);
            }
        };
        Bundle bundle = getArguments();
        if (bundle != null) {
            onGetBundle(bundle);
        }

        viewmodel = getViewModel();
        getLifecycle().addObserver(viewmodel);
        viewmodel.datalist.observe(this, this);
        viewmodel.viewStatusLiveData.observe(this, this);
        onViewCreated(bundle);
    }




    protected void onHandlerMessage(Message msg) {
    };


    protected void onGetBundle(Bundle bundle) {
    }
    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                viewmodel.refresh();
            }
        });
    }

    protected abstract void onViewCreated(Bundle savedInstanceState);

    @Override
    public void onChanged(Object o) {
        if (o instanceof ViewStatus && mLoadService != null) {
            switch ((ViewStatus) o) {
                case LOADING:
                    mLoadService.showCallback(LoadingCallback.class);
                    break;
                case EMPTY:
                    mLoadService.showCallback(EmptyCallback.class);
                    break;
                case SHOW_COENT_ERROR:
                    mLoadService.showSuccess();
                    ToastUtil.show(viewmodel.errorMessage.getValue().toString());
                    break;
                case SHOW_CONTENT:
                    mLoadService.showSuccess();
                    break;
                case NO_MORE_DATA:

                    ToastUtil.show(getString(R.string.no_more_data));
                    break;
                case REFRESH_ERROR:
                    try {
                        if (viewmodel!=null
                                &&viewmodel.datalist!=null&&
                                ((ObservableArrayList) viewmodel.datalist.getValue()).size() == 0) {
                            mLoadService.showCallback(ErrorCallback.class);
                        } else {
                            ToastUtil.show(viewmodel.errorMessage.getValue().toString());
                        }
                    }catch (Exception ww){
                        mLoadService.showSuccess();
                        if (!viewmodel.errorMessage.getValue().toString().contains("HTTP 401")){
                            ToastUtil.show(viewmodel.errorMessage.getValue().toString());
                        }
                    }

                    break;
                case LOAD_MORE_FAILED:
                    ToastUtil.show(viewmodel.errorMessage.getValue().toString());
                    break;
            }
            onNetworkResponded(null, false);
        } else if (o instanceof List) {
            onNetworkResponded((List<DATA>) o, true);
        }



    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onActivityCreated");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(getContext());
        Log.d(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onDetach");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onResume");
    }

    @Override
    public void onDestroy() {
        Log.d(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onDestroy");
        if (mLoadService != null) {
            ViewGroup parentView = mLoadService.getLoadLayout();
            if (parentView != null) {
                parentView.removeView(mLoadService.getLoadLayout());
            }
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.d(getFragmentTag(), "Activity:" + getActivity() + " Fragment:" + this + ": " + "onDestroyView");
        super.onDestroyView();
    }

    protected void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    protected void showSuccess() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }
}
