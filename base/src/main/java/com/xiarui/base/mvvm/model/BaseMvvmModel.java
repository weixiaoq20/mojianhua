package com.xiarui.base.mvvm.model;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.xiarui.base.preference.BasicDataPreferenceUtil;
import com.xiarui.base.utlis.GenericUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseMvvmModel<NETWORK_DATA, RESULT_DATA> implements MvvmDataObserver<NETWORK_DATA> {
    protected int mPage = 1;
    protected WeakReference<IBaseModeListener> mReferenceBaseModelListener;
    private boolean mIsPaging;//是否是第一页
    private boolean mIsLoading;//是否加载
    private final int INT_PAGE_NUMBER;
    private String mCachedPreferenceKey;
    private String mApkPredefinedData;

    public void setmIsPaging(boolean mIsPaging) {
        this.mIsPaging = mIsPaging;
    }

    public boolean ismIsLoading() {
        return mIsLoading;
    }

    public void setmIsLoading(boolean mIsLoading) {
        this.mIsLoading = mIsLoading;
    }

    public boolean ismIsPaging() {
        return mIsPaging;
    }

    public BaseMvvmModel(boolean mIsPaging, String mCachedPreferenceKey, String mApkPredefinedData, int... initPageNumber) {
        this.mIsPaging = mIsPaging;
        if (mIsPaging && initPageNumber != null && initPageNumber.length > 0) {
            INT_PAGE_NUMBER = initPageNumber[0];
        } else {
            INT_PAGE_NUMBER = -1;
        }
        this.mCachedPreferenceKey = mCachedPreferenceKey;
        this.mApkPredefinedData = mApkPredefinedData;
    }

    public void register(IBaseModeListener listener) {
        if (listener != null) {
            mReferenceBaseModelListener = new WeakReference<>(listener);
        }
    }


    public void refresh() {
        Log.e("aa","11");
        try {
            // Need to throw exception if register is not called;
            if (!mIsLoading) {
                if (mIsPaging) {
                    mPage = INT_PAGE_NUMBER;
                }
                mIsPaging = true;
                load();
            }
        } catch (Exception ww) {

        }


    }

    public void loadNextPage() {
        try {
            if (!mIsLoading) {
                mIsLoading = true;
                load();
            }
        } catch (Exception ww) {

        }
    }

    /**
     * 第一默认加载是否有缓存数据
     * 第二在重新加载网络数据覆盖
     * 第三如果网络不好的情况下,加载预制的数据
     */

    public void getCachedDataAndLoad() {
        if (!mIsLoading) {
            mIsLoading = true;
            if (mCachedPreferenceKey != null) {
                String saveDataString = BasicDataPreferenceUtil.getInstance().getString(mCachedPreferenceKey);
                if (!TextUtils.isEmpty(saveDataString)) {
                    try {
                        NETWORK_DATA savedData = new Gson().fromJson(new JSONObject(saveDataString).getString("data"), (Class<NETWORK_DATA>) GenericUtils.getGenericType(this));
                        if (savedData != null) {
                            onSuccess(savedData, true);
                        }
                        long timeSlot = Long.parseLong(new JSONObject(saveDataString).getString("updateTimeInMillis"));
                        if (isNeedToUpdate(timeSlot)) {
                            load();
                            return;
                        }
                    } catch (JSONException e) {
                        Log.e("BaseMvvmModel", e.getMessage());
                        //e.printStackTrace();
                    }
                }


                if (mApkPredefinedData != null) {
                    NETWORK_DATA savedData = new Gson().fromJson(mApkPredefinedData, (Class<NETWORK_DATA>) GenericUtils.getGenericType(this));
                    if (savedData != null) {
                        onSuccess(savedData, true);
                    }

                }
            }
            load();
        }
    }


    public boolean isNeedToUpdate(long timeSlot) {
        return true;
    }


    protected void notifyResultToListener(NETWORK_DATA networkData, RESULT_DATA resultData, boolean isFromCache) {
        IBaseModeListener listener = mReferenceBaseModelListener.get();
        if (listener != null) {
            //notify
            if (mIsPaging) {
                listener.onLoadSuccess(this, resultData,
                        new PagingResult(mPage == INT_PAGE_NUMBER,
                                resultData == null ? true : ((List) resultData).isEmpty(),
                                ((List) resultData).size() > 0));
            } else {
                listener.onLoadSuccess(this, resultData);
            }


            //save resultData to preference
            if (mIsPaging) {
                if (mCachedPreferenceKey != null && mPage == INT_PAGE_NUMBER && !isFromCache) {
                    saveDataToPreference(networkData);
                } else {
                    if (mCachedPreferenceKey != null&&!isFromCache) {
                        saveDataToPreference(networkData);
                    }
                }

            }
            // update page number
            if (mIsPaging&&!isFromCache) {
                if (resultData != null && ((List) resultData).size() > 0
                        && !isFromCache) {
                    mPage++;
                }
            }
        }


        if (!isFromCache) {
            mIsLoading = false;
        }
    }


    protected void loadFail(final String errorMessage) {
        IBaseModeListener listener = mReferenceBaseModelListener.get();
        if (listener != null) {
            if (mIsPaging) {
                listener.onLoadFail(this, errorMessage, new PagingResult(mPage == INT_PAGE_NUMBER, true, false));
            } else {
                listener.onLoadFail(this, errorMessage);
            }
        }
        mIsLoading = false;
    }


    protected void saveDataToPreference(NETWORK_DATA data) {
        if (data != null) {
            BaseCacheData<NETWORK_DATA> cacheData = new BaseCacheData<>();
            cacheData.data = data;
            cacheData.updateTimeInMillis = System.currentTimeMillis();
            BasicDataPreferenceUtil.getInstance().setString(mCachedPreferenceKey, new Gson().toJson(cacheData));

        }
    }

    public abstract void load();

    public void cancel() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    CompositeDisposable compositeDisposable = null;

    public void addDisposable(Disposable d) {
        if (d == null) {
            return;
        }

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(d);
    }
}
