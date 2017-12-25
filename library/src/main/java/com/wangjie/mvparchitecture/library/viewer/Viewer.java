package com.wangjie.mvparchitecture.library.viewer;

import android.content.Context;
import android.support.annotation.Nullable;

import com.wangjie.mvparchitecture.library.contract.OnViewerDestroyListener;
import com.wangjie.mvparchitecture.library.contract.OnViewerLifecycleListener;

/**
 * Viewer 层
 *
 * 该层是 MVP 的 View 层, UI相关. 所有 Viewer 都需要实现该接口,常见的 Viewer 有 Activity, Fragment, ViewGroup等
 *
 * 该层会与 Controller 或者 Presenter 交互
 *
 * 1. 如果与 Presenter 交互, 则当有时间发生时(比如点击按钮后),会调用 Presenter 层的方法
 *
 * 2. 如果与 Controller 交互, 则所有的事件绑定(如OnClickListener等都讲移动到 Controller 层, Viewer 层中所有的方法只会进行 UI 操作)
 *
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/12/16.
 */
public interface Viewer {
    /**
     * 提供对Viewer简单生命周期的绑定
     * 比如Controller中需要根据生命周期做不同的事件派发
     */
    Viewer bind(OnViewerLifecycleListener onViewerLifecycleListener);

    /**
     * 提供对Viewer销毁状态的绑定
     * 比如Presenter中task操作需要在Viewer销毁时停止对UI的回调
     */
    Viewer bind(OnViewerDestroyListener onViewerDestroyListener);

    /**
     * 获取当前Viewer的Context对象,一般为Activity的Context对象
     */
    @Nullable
    Context context();

    //************* Viewer层常用的UI操作 **************//

    /**
     * 显示一个Toast,每个Viewer应该只维护一个Toast对象
     */
    void showToast(String message);

    void showToast(int resStringId);

    /**
     * 显示一个加载进度条,每个Viewer应该只维护一个进度的Dialog对象
     */
    void showLoadingDialog(String message);

    void showLoadingDialog(int resStringId);

    /**
     * 取消掉加载进度条(加载成功或者加载失败时)
     */
    void cancelLoadingDialog();

}
