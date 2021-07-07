package com.xiarui.base.utlis;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Activity管理类
 */
public class AppManagerUtil {

    private static Stack<Activity> activityStack;
    private static AppManagerUtil instance;
    private static Application mApplication;

    private AppManagerUtil() {
    }

    private  static final class AppManagerUtilsHolder {
        public static AppManagerUtil instance = new AppManagerUtil();
    }

    public static AppManagerUtil getInstance() {
        return AppManagerUtilsHolder.instance;
    }

    public void setApplication(Application application) {
        mApplication = application;
    }

    public Application getApplication() {
        if (mApplication == null)
            throw new UnsupportedOperationException("Application is null...");
            return mApplication;
    }

    /**
     * 是否包含某Activity
     *
     * @param cls 待移除实例
     */

    public boolean containActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加一个Activity实例到栈中
     *
     * @param activity 待添加实例
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 从栈中移除一个Activity实例
     *
     * @param activity 待移除实例
     * @return 移除成功/失败
     */
    public boolean removeActivity(Activity activity) {
        if (activity != null && activityStack != null && activityStack.contains(activity))
            return activityStack.remove(activity);
        else
            return false;
    }

    /**
     * 移除栈内所有Activity实例
     */
    public void removeAllActivity() {
        if (activityStack != null)
            activityStack.removeAllElements();
    }

    /**
     * 获取当前栈顶的Activity实例
     *
     * @return 栈顶的Activity实例
     */
    public Activity getPopActivity() {
        if (activityStack == null || activityStack.isEmpty())
            return null;
        return activityStack.lastElement();
    }

    /**
     * finish一个Activity并将其从栈中移除
     *
     * @param activity 待移除实例
     * @return 移除成功/失败
     */
    public boolean finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
            if (activityStack != null)
                return activityStack.remove(activity);
            else
                return false;
        } else {
            return false;
        }
    }

    /**
     * finish一个Activity并将其从栈中移除
     *
     * @param cls 待移除实例
     * @return 移除成功/失败
     */
    public boolean finishActivity(Class<?> cls) {
        boolean flag = false;
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                flag = finishActivity(activity);
                break;
            }
        }
        return flag;
    }

    /**
     * finish所有栈中Activity并从栈中移除
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                activity.finish();
            }
            activityStack.removeAllElements();
        }
    }

    /**
     * 除了某个特定的Activity之外finish所有栈中Activity并从栈中移除
     *
     * @param cls 不需要被移除的实例的类
     */
    public void finishAllActivityExceptMainActivity(Class<?> cls) {
        if (activityStack != null) {
            List<Activity> list = new ArrayList<>();
            for (Activity eachElement : activityStack) {
                if (!eachElement.getClass().equals(cls)) {
                    eachElement.finish();
                    list.add(eachElement);
                }
            }
            activityStack.removeAll(list);
        }
    }

}
