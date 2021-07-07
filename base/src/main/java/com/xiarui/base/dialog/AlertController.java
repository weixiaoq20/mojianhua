package com.xiarui.base.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Email 1634299032@qq.com
 * Created by guopengpeng on 2017/7/3.
 * Version 1.0
 * Description: 放置一些参数
 */

class AlertController {


    private MyAlertDialog mDailog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    public AlertController(MyAlertDialog dailog, Window window) {
        this.mDailog = dailog;
        this.mWindow = window;
    }

    public void setText(int viewId, CharSequence charSequence) {
        mViewHelper.setText(viewId, charSequence);
    }


    public void setOnClickListener(int viewId, View.OnClickListener onClick) {
        mViewHelper.setOnClickListener(viewId, onClick);
    }


    public <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }

    /**
     * 得到dialog对象
     */
    public MyAlertDialog getDailog() {
        return mDailog;
    }

    /**
     * 得到window对象
     */
    public Window getWindow() {
        return mWindow;
    }

    /**
     * 各种参数存放的类
     */

    public static class AlertParams {

        public Context mContext;
        public int mThemeId;
        //点击空白处dialog是否能消失
        public boolean mCancelable = false;
        //取消监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //dialog隐藏监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //按键监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        //设置view
        public View mView;
        //布局id
        public int mLayoutId;
        //存放设置文本的对象和内容  用SparseArray 因为比HashMap更高效
        public SparseArray<CharSequence> mText = new SparseArray<>();
        // 宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 动画
        public int mAnimations = 0;
        // 位置
        public int mGravity = Gravity.CENTER;
        // 高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        //存放点击事件的对象
        public SparseArray<View.OnClickListener> mOnClickListener = new SparseArray<>();


        public AlertParams(Context context, int themeId) {
            this.mContext = context;
            this.mThemeId = themeId;
        }

        /**
         * 绑定和设置参数
         */
        public void apply(AlertController alert) {
            DialogViewHelper viewHelper = null;
            if (mLayoutId != 0) {
                viewHelper = new DialogViewHelper(mLayoutId, mContext);
            }

            if (mView != null) {
                viewHelper = new DialogViewHelper(mView);
            }
            if (viewHelper == null) {
                throw new IllegalArgumentException("setContentView can't be null");
            }

            //为dialog设置布局
            alert.getDailog().setContentView(viewHelper.getContentView());
            alert.setViewHelper(viewHelper);

            //设置文本
            int textSize = mText.size();
            if (textSize != 0) {
                for (int i = 0; i < textSize; i++) {
                    alert.setText(mText.keyAt(i), mText.valueAt(i));
                }
            }
            //设置点击事件
            int onClick = mOnClickListener.size();
            if (onClick != 0) {
                for (int i = 0; i < onClick; i++) {
                    alert.setOnClickListener(mOnClickListener.keyAt(i), mOnClickListener.valueAt(i));
                }
            }

            // 4.配置自定义的效果  全屏  从底部弹出    默认动画
            Window window = alert.getWindow();
            // 设置位置
            window.setGravity(mGravity);

            // 设置动画
            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }

            // 设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);

        }
    }
}
