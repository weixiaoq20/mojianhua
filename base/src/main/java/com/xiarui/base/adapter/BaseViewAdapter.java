package com.xiarui.base.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

/**
 * 作者：夏瑞
 * 2017/8/4 12:22
 * 注释:自定义View适配器
 * 邮箱:1970258244@qq.com
 */

public class BaseViewAdapter extends PagerAdapter {
        //自定义viewpager的适配器
        List<View> list;
        public BaseViewAdapter(List<View> list) {
            this.list=list;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        //  判断  当前的view 是否是  Object 对象
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "1";  //暂时没用的
        }
}
