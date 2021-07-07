package com.xiarui.base.mvvm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseCacheData<DATA> {
    @SerializedName("updateTimeInMillis")
    @Expose
    public long updateTimeInMillis;

    @SerializedName("data")
    @Expose
    public DATA data;
}
