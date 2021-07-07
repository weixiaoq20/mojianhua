package com.xiarui.work.order.impl;

import com.xiangxue.arouter_annotation.ARouter;
import com.xiangxue.common.order.OrderDrawable;
import com.xiarui.work.order.R;


// order 自己决定 自己的暴漏
@ARouter(path = "/order/getDrawable")
public class OrderDrawableImpl implements OrderDrawable {
    @Override
    public int getDrawable() {
        return R.drawable.ic_ac_unit_black_24dp;
    }
}
