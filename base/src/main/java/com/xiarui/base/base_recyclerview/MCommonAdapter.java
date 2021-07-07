package com.xiarui.base.base_recyclerview;

import android.content.Context;
import android.view.LayoutInflater;


import com.xiarui.base.base_recyclerview.base.ItemViewDelegate;
import com.xiarui.base.base_recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class MCommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected LayoutInflater mInflater;

    public List<T> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas.addAll(mDatas);
        this.notifyDataSetChanged();
    }

    public void setData(List<T> mDatas){
        if (this.mDatas.size()>0){
            this.mDatas.clear();

        }
        this.mDatas=mDatas;
        this.notifyDataSetChanged();
    }

    public void addAll(List<T> mDatas) {
        this.mDatas.addAll(mDatas);
        this.notifyDataSetChanged();
    }

    public List<T> getData() {
        return this.mDatas;
    }

    public void add(T t) {
        this.mDatas.add(t);
        this.notifyDataSetChanged();
    }


    public void remove(T t) {
        this.mDatas.remove(t);
        this.notifyDataSetChanged();
    }

    public void remove(int pos) {
        this.mDatas.remove(pos);
        this.notifyDataSetChanged();
    }


    public void clear() {
        if (this.mDatas != null &&
                this.mDatas.size() != 0) {
            this.mDatas.clear();
        }
        this.notifyDataSetChanged();
    }

    public int getCount() {
        return this.mDatas.size();
    }


    public MCommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                MCommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);


}
