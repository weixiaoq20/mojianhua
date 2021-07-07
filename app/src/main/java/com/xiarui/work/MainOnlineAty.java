package com.xiarui.work;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiangxue.arouter_annotation.ARouter;
import com.xiangxue.arouter_annotation.Parameter;
import com.xiangxue.arouter_api.ParameterManager;
import com.xiangxue.arouter_api.RouterManager;
import com.xiangxue.common.bean.Personal;
import com.xiangxue.common.bean.Student;
import com.xiangxue.common.order.OrderDrawable;
import com.xiangxue.common.order.user.IUser;
import com.xiangxue.common.utils.Cons;
import com.xiarui.work.databinding.MainOnlineViewBinding;
import com.xiarui.work.order.contance.AddressUrl;
import com.xiarui.work.order.enity.LoginBean;
import com.xiarui.work.order.test.LoginViewMainModel;
import com.xiarui.base.mvvm.view.BaseMvvmAppComAty;
import com.xiarui.base.utlis.XiaoUtlis;

import java.util.List;

import androidx.annotation.Nullable;


@ARouter(path = "/app/MainActivity")
public class MainOnlineAty extends BaseMvvmAppComAty<MainOnlineViewBinding, LoginViewMainModel, LoginBean> {

    @Parameter(name = "/order/getDrawable")
    OrderDrawable orderDrawable; // 公共基础库common

    @Parameter(name = "/order/getUserInfo")
    IUser iUser; // 公共基础库common

    @Parameter
    String next;

    boolean isRelease;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isRelease) {
            Log.e(Cons.TAG, "当前为：集成化模式，除app可运行，其他子模块都是Android Library");
        } else {
            Log.e(Cons.TAG, "当前为：组件化模式，app/order/personal子模块都可独立运行");
        }

        // 懒加载方式，跳到哪加载哪个类
        ParameterManager.getInstance().loadParameter(this);

        // app模块本来就可以直接加载其他模块的资源   personal
        // 拿到 order模块的图片 在app模块展示
        int drawableId = orderDrawable.getDrawable();
        ImageView img = findViewById(R.id.img);
        img.setImageResource(drawableId);

        // 我输出 order模块的Bean休息
        Log.d(Cons.TAG, "order的Bean onCreate: " +  iUser.getUserInfo().toString());
        Toast.makeText(MainOnlineAty.this,iUser.getUserInfo().toString(),Toast.LENGTH_LONG).show();

        Log.d(Cons.TAG, "/order/Order_MainActivity onCreate: " +  next);


    }

    @Override
    public void onResume() {
        super.onResume();

        // 懒加载方式，跳到哪加载哪个类
        ParameterManager.getInstance().loadParameter(this);

        // app模块本来就可以直接加载其他模块的资源   personal
        // 拿到 order模块的图片 在app模块展示
        int drawableId = orderDrawable.getDrawable();
        ImageView img = findViewById(R.id.img);
        img.setImageResource(drawableId);

        // 我输出 order模块的Bean休息
        Log.d(Cons.TAG, "order的Bean onResume: " +  iUser.getUserInfo().toString());
        Toast.makeText(MainOnlineAty.this,iUser.getUserInfo().toString(),Toast.LENGTH_LONG).show();

        Log.d(Cons.TAG, "/order/Order_MainActivity onResume: " +  next);
    }


    private LoginViewMainModel loginView;
    @Override
    protected String getAppcommAtyTag() {
        return "MainOnlineAty";
    }

    @Override
    public void onNetworkResponded(List list, boolean isDataUpdated) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.main_online_view;
    }

    @Override
    public LoginViewMainModel getViewModel() {
        if (loginView==null){
            loginView=new LoginViewMainModel();
            loginView.setType(-1);
        }
        return loginView;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected View getLoadSirView() {
        return viewDataBinding.viewAll;
    }
    OnLineLinstener eventListener;
    @Override
    protected void onViewCreated() {
        if (eventListener == null) {
            eventListener = new OnLineLinstener();
        }
        viewDataBinding.setEventlistener(eventListener);
        loginView.refresh();
        showSuccess();

        XiaoUtlis.xiaomiNotch(mvvmAppComAty,viewDataBinding.viewAll);
    }


    public void jumpOrder(View view) {
        /*Intent intent = new Intent(this, Order_MainActivity.class);
        intent.putExtra("name", "derry");
        startActivity(intent);*/

        // 使用我们自己写的路由 跳转交互
        RouterManager.getInstance()
                .build(AddressUrl.ORDER_LOGINATY)
                .withString("name", "杜子腾")
                .navigation(this); // 组件和组件通信
    }

    public void jumpPersonal(View view) {
        // 以前是这样跳转
        /*Intent intent = new Intent(this, Personal_MainActivity.class);
        intent.putExtra("name", "derry");
        startActivity(intent);*/

        // 现在是这样跳转  目前还要写这么多代码，是不是非常累

        // TODO 最终的成效：用户 一行代码搞定，同时还可以传递参数，同时还可以懒加载
        /*ARouter$$Group$$personal group$$personal = new ARouter$$Group$$personal();
        Map<String, Class<? extends ARouterPath>> groupMap = group$$personal.getGroupMap();
        Class<? extends ARouterPath> myClass = groupMap.get("personal");

        try {
            ARouter$$Path$$personal path = (ARouter$$Path$$personal) myClass.newInstance();
            Map<String, RouterBean> pathMap = path.getPathMap();
            RouterBean bean = pathMap.get("/personal/Personal_MainActivity");

            if (bean != null) {
                Intent intent = new Intent(this, bean.getMyClass());
                startActivity(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Student student = new Student("Derry大大", "男", 99);

        Personal personal=new Personal("tiantian",110);

        // 使用我们自己写的路由 跳转交互
        RouterManager.getInstance()
                .build("/personal/Personal_MainActivity")
                .withString("name", "史甄湘")
                .withString("sex", "男")
                .withInt("age", 99)
                .withSerializable("student", student)
                .withParcelable("personal",personal)
                .navigation(this);
    }

    public class OnLineLinstener{

    }
}
