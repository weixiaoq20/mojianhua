package com.xiarui.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xiarui.base.R;


/**
 * Email 1634299032@qq.com
 * Created by guopengpeng on 2017/7/3.
 * Version 1.0
 * Description: 通用的dialog对象
 */

public class MyAlertDialog extends Dialog {
    private AlertController mAlert;

    public MyAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new AlertController(this, getWindow());
    }

    public void setText(int viewId, CharSequence charSequence) {
        mAlert.setText(viewId, charSequence);
    }


    public void setOnClickListener(int viewId, View.OnClickListener onClick) {
        mAlert.setOnClickListener(viewId, onClick);
    }


    public <T extends View> T getView(int viewId) {
        return mAlert.getView(viewId);
    }


    public static class Builder {
        private final AlertController.AlertParams P;

        public Builder(@NonNull Context context) {
            //没有设置给默认的背景
            this(context, R.style.ActionSheetDialogStyle);
        }

        public Builder(@NonNull Context context, int themeId) {
            P = new AlertController.AlertParams(context, themeId);
        }

        /**
         * 设置点击空白处是否dialog是否消失
         */
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * 取消监听
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * 隐藏监听
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * 设置按键监听
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * 设置view
         */

        public Builder setContentView(View view) {
            P.mView = view;
            P.mLayoutId = 0;
            return this;
        }


        /**
         * 设置布局id
         */
        public Builder setContentView(int layoutId) {
            P.mView = null;
            P.mLayoutId = layoutId;
            return this;
        }

        public Builder setText(int viewId, CharSequence text) {
            P.mText.put(viewId, text);
            return this;
        }

        public Builder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
            P.mOnClickListener.put(viewId, onClickListener);
            return this;
        }

        //设置一些属性

        /**
         * 全屏
         */
        public Builder fullWidth() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 从底部弹出
         */
        public Builder fromTop(boolean isAnimation) {
            if (isAnimation) {
                P.mAnimations = R.style.ActionSheetDialogStyle;
            }
            P.mGravity = Gravity.TOP;
            return this;
        }

        /**
         * 从底部弹出
         */
        public Builder fromBottom(boolean isAnimation) {
            if (isAnimation) {
                P.mAnimations = R.style.ActionSheetDialogStyle;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置Dialog的宽高
         *
         * @param width
         * @param height
         * @return
         */
        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        /**
         * 添加默认动画
         *
         * @return
         */
        public Builder addDefaultAnimation() {
            P.mAnimations = R.style.ActionSheetDialogStyle;
            return this;
        }

        /**
         * 设置动画
         *
         * @param styleAnimation
         * @return
         */
        public Builder setAnimations(int styleAnimation) {
            P.mAnimations = styleAnimation;
            return this;
        }


        //创建
        public MyAlertDialog create() {
            final MyAlertDialog dialog = new MyAlertDialog(P.mContext, P.mThemeId);
            //调用真正去执行的方法
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        public MyAlertDialog show() {
            final MyAlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

        public MyAlertDialog dismiss() {
            final MyAlertDialog dialog = create();
            dialog.dismiss();
            return dialog;
        }


    }


}
