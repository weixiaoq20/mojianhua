package com.xiarui.base.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Email 1634299032@qq.com
 * Created by guopengpeng on 2017/7/3.
 * Version 1.0
 * Description: dialog 辅助类
 */

class DialogViewHelper {
    private View mContentView;
    //存放view对象集合
    private SparseArray<WeakReference<View>> mView = null;

    public DialogViewHelper(int layoutId, Context context) {
        this();
        mContentView = LayoutInflater.from(context).inflate(layoutId, null);
    }

    public DialogViewHelper() {
        mView = new SparseArray<>();
    }

    public DialogViewHelper(View view) {
        mContentView = view;
    }

    public View getContentView() {
        return mContentView;
    }

    public void setText(int viewId, CharSequence charSequence) {
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(charSequence);
        }
    }


    public void setOnClickListener(int viewId, View.OnClickListener onClick) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(onClick);
        }
    }

    /**
     * 减少findviewbyid的次数
     */

    public <T extends View> T getView(int viewId) {
        WeakReference<View> viewWeakReference = mView.get(viewId);
        View view = null;
        if (viewWeakReference .get()!= null) {
            view = viewWeakReference.get();
        }
        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null) {
                mView.put(viewId, new WeakReference<>(view));
            }
        }


        return (T) view;
    }

}
