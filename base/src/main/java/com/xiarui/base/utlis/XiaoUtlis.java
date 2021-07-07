package com.xiarui.base.utlis;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class XiaoUtlis {

    /**
     * 小米刘海适配
     *
     * @param view_content
     */
    public static void xiaomiNotch(Context mContxt, View view_content) {
        int resourceId = mContxt.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int result = mContxt.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view_content.getLayoutParams();
            layoutParams.setMargins(0, result, 0, 0);
            view_content.requestLayout();
        }
    }

    public static int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static void setLightStatusBarIcon(Activity activity, boolean lightMode) {
        final int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010;
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int vis = window.getDecorView().getSystemUiVisibility();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//23 6.0
            if (lightMode) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//19  4.4
            if (lightMode) {
                vis |= SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
            } else {
                vis &= ~SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
            }
        }
        window.getDecorView().setSystemUiVisibility(vis);
    }



}
