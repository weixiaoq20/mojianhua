package com.xiarui.base.mvvm.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.kingja.loadsir.callback.Callback;
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
import com.xiarui.base.utlis.AppManagerUtil;
import com.xiarui.base.utlis.LogUtil;
import com.xiarui.base.utlis.StatusBarUtil;
import com.xiarui.base.utlis.ToastUtil;
import com.xiarui.base.utlis.XiaoUtlis;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMvvmAppComAty<VIEW extends ViewDataBinding,
        VIEWMODEL extends BaseMvvmViewModel, DATA> extends AppCompatActivity implements Observer {

    protected VIEWMODEL viewmodel;
    protected VIEW viewDataBinding;
    private LoadService mLoadService;

    protected String TAG=this.getClass().getSimpleName();
    protected abstract String getAppcommAtyTag();

    public abstract void onNetworkResponded(List<DATA> dataList, boolean isDataUpdated);

    public abstract @LayoutRes
    int getLayoutId();

    public abstract VIEWMODEL getViewModel();

    protected BaseMvvmAppComAty mvvmAppComAty;
    public int screenWidth = 0;
    public int screenHeight = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(getAppcommAtyTag(), "onCreate...");
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mvvmAppComAty = this;
        initWindowBg();
        //???????????????
        mHandler = new Handler(mvvmAppComAty.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //????????????????????????????????????
                onHandlerMessage(msg);
            }
        };
        AppManagerUtil.getInstance().addActivity(mvvmAppComAty);
        //?????????????????????
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            onGetBundle(bundle);
        }
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new ErrorCallback())//?????????????????????
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//?????????????????????
                .build();
        mLoadService = loadSir.register(getLoadSirView(), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                viewmodel.refresh();
            }
        });
        initData(savedInstanceState);

        viewmodel = getViewModel();
        getLifecycle().addObserver(viewmodel);
        viewmodel.datalist.observe(this, this);
        viewmodel.viewStatusLiveData.observe(this, this);
        onViewCreated();
        initListener();

    }


    /**
     * ??????????????????????????????????????????
     */
    protected void onHandlerMessage(Message msg) {
    }

    protected void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) mvvmAppComAty.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mvvmAppComAty.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (mvvmAppComAty.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(mvvmAppComAty.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public Handler mHandler;
    protected abstract void initListener();

    protected boolean isChat = false;
    protected boolean dark = false;

    protected void initWindowBg() {
        if (isChat) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                View content = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
                if (content != null) {
                    content.setFitsSystemWindows(true);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(Color.TRANSPARENT);
                    //???????????????????????????????????????(?????????????????????)
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    //????????????????????????????????????????????????????????????????????????????????????????????????
                    //?????????????????????????????????:????????????????????????
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        } else {
            StatusBarUtil.setRootViewFitsSystemWindows(this, false);
            //?????????????????????
            StatusBarUtil.setTranslucentStatus(this);
            //?????????????????????????????????????????????????????????, ???????????????????????????????????????, ?????????????????????????????????
            //??????????????????????????????,?????????????????????, ??????????????????????????????????????????, ???????????????????????????????????????if??????
            if (!StatusBarUtil.setStatusBarDarkTheme(this, dark)) {
                //????????????????????????????????? ???????????????????????????????????????????????????, ?????????????????????????????????????????????,
                //???????????????+???=???, ??????????????????????????????
                StatusBarUtil.setStatusBarColor(this, Color.parseColor("#ffffff"));
            }
            XiaoUtlis.setLightStatusBarIcon(mvvmAppComAty, true);

        }

        // XiaoUtlis.setLightStatusBarIcon(mvvmAppComAty,true);
    }

    protected abstract void onGetBundle(Bundle bundle);

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract View getLoadSirView();


    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                viewmodel.refresh();
            }
        });
    }


    public View getView() {
        if (mvvmAppComAty != null) {
            View cv = getWindow().getDecorView();
            return cv;
        } else {
            return null;
        }

    }

    public void finishResult(Intent intent) {
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public void finishResult() {
        setResult(RESULT_OK);
        this.finish();
    }


    /**
     * ??????startactivity???????????????????????????
     */
    public void startActivity(Bundle bundle, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        atyInAccessAnim();
    }

    public void atyInAccessAnim() {
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    public void atyOutAccessAnim() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    /**
     * ??????startactivity???????????????????????????
     */
    public void startActivity(String bundleName, Bundle bundle, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtra(bundleName, bundle);
        }
        startActivity(intent);
        atyInAccessAnim();
    }

    /**
     * ??????????????????
     */
    public void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        atyInAccessAnim();
    }


    protected abstract void onViewCreated();


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
                    onToast(viewmodel.errorMessage.getValue().toString());
                    break;
                case SHOW_CONTENT:
                    mLoadService.showSuccess();
                    break;
                case NO_MORE_DATA:
                    onToast(getString(R.string.no_more_data));
                    break;
                case REFRESH_ERROR:
                    try {
                        if (viewmodel != null
                                && viewmodel.datalist != null &&
                                ((ObservableArrayList) viewmodel.datalist.getValue()).size() == 0) {
                            mLoadService.showCallback(ErrorCallback.class);
                        } else {
                            onToast(viewmodel.errorMessage.getValue().toString());
                        }
                    } catch (Exception ww) {
                        mLoadService.showSuccess();
                        if (!viewmodel.errorMessage.getValue().toString().contains("HTTP 401")) {
                            onToast(viewmodel.errorMessage.getValue().toString());
                        }
                    }

                    break;
                case LOAD_MORE_FAILED:
                    onToast(viewmodel.errorMessage.getValue().toString());
                    break;
            }
            onNetworkResponded(null, false);
        } else if (o instanceof List) {
            onNetworkResponded((List<DATA>) o, true);
        }
    }

    protected void onToast(String toast) {
        if (mvvmAppComAty != null) {
            mvvmAppComAty.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(toast);
                }
            });
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.e(getAppcommAtyTag(), "Activity:" + getLocalClassName() + " Fragment:" + this + ": " + "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e(getAppcommAtyTag(), "Activity:" + getLocalClassName() + " Fragment:" + this + ": " + "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e(getAppcommAtyTag(), "Activity:" + getLocalClassName() + " Fragment:" + this + ": " + "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e(getAppcommAtyTag(), "Activity:" + getLocalClassName() + " Fragment:" + this + ": " + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * ???????????????
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //??????????????????
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
        LogUtil.e(getAppcommAtyTag(), "Activity:" + getLocalClassName() + " Fragment:" + this + ": " + "onResume");
    }

    @Override
    public void onDestroy() {
        LogUtil.e(getAppcommAtyTag(), "Activity:" + getLocalClassName() + " Fragment:" + this + ": " + "onDestroy");
        if (mvvmAppComAty != null) {
            AppManagerUtil.getInstance().removeActivity(mvvmAppComAty);
        }

        super.onDestroy();


    }


    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }


    public void showSuccess() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }


    /**
     * ?????????????????????????????????  ????????????????????????  ?????????????????????????????????????????? ?????????
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//?????????????????????
            Manifest.permission.READ_EXTERNAL_STORAGE,//?????????????????????
            Manifest.permission.CAMERA,//????????????
            Manifest.permission.RECORD_AUDIO,//????????????
            Manifest.permission.READ_PHONE_STATE,//?????? ????????????
            Manifest.permission.ACCESS_COARSE_LOCATION,//??????????????????
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.CALL_PHONE,

            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,

    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * ????????????????????????????????????????????????
     */
    private boolean isNeedCheck = true;

    /**
     * ????????????
     */
    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }

    }


    /**
     * ?????????????????????????????????????????????
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param grantResults
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }


    /**
     * ???????????????, ????????????????????????
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // ????????????  ????????????
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        //????????????
        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);
        builder.show();
    }


    /**
     * ?????????????????????
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }


}
