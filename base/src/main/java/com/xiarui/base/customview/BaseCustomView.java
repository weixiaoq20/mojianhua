package com.xiarui.base.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


//创建工厂方法
public abstract class BaseCustomView<VIEW extends ViewDataBinding,DATA extends BaseCustomViewModel> extends LinearLayout implements IBaseCustomView<DATA>{

    protected VIEW mBinding;
    protected DATA data;
    public BaseCustomView(Context context) {
        super(context);
        init();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public  void init(){
        LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding= DataBindingUtil.inflate(inflater,getLayoutId(),this,false);
        addView(mBinding.getRoot());
    }

    protected abstract int getLayoutId();

    public void setData(DATA data){
        this.data = data;
        setDataToView(data);
        mBinding.executePendingBindings();
    }

    protected abstract void setDataToView(DATA data);

    public VIEW getBinding() {
        return mBinding;
    }

    public void setBinding(VIEW mBinding) {
        this.mBinding = mBinding;
    }
}
