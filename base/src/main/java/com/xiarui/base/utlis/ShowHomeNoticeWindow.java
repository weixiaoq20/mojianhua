package com.xiarui.base.utlis;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xiarui.base.R;
import com.xiarui.base.mvvm.view.BaseMvvmAppComAty;


public class ShowHomeNoticeWindow extends PopupWindow {

    private View conentView;
    private BaseMvvmAppComAty baseActivity;

    public ShowHomeNoticeWindow(BaseMvvmAppComAty context, int layout, int color) {
        baseActivity = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(layout, null);

        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);

        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        this.setBackgroundDrawable(new BitmapDrawable());
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.ActionSheetDialogStyle);


    }

    public <T extends View> T getView(int id) {
        View view = conentView.findViewById(id);
        return (T) view;
    }

    public ShowHomeNoticeWindow setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        if (TextUtils.isEmpty(value)) {
            view.setText("");
        } else {
            view.setText(value);
        }
        return this;
    }

    public ShowHomeNoticeWindow setViewOnClick(int viewId, final OnClick onClickListener) {
        View view = getView(viewId);
        if (onClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v, ShowHomeNoticeWindow.this);
                }
            });
        }
        return this;
    }

    public View getConentView() {
        return conentView;
    }

    public void showAtLocationBottom(View view) {

    }

    public interface OnClick {
        void onClick(View view, ShowHomeNoticeWindow window);
    }
}
