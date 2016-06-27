package com.wangjie.mvparchitecture.library.contract;

/**
 * View层简单生命周期的绑定
 *
 * 如Controller层,需要把自身与Viewer层的生命周期进行绑定,来对一些事件进行派发
 *
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/22/16.
 */
public interface OnViewerLifecycleListener extends OnViewerDestroyListener {
    /**
     * Viewer resume 时的回调
     */
    void onViewerResume();

    /**
     * Viewer pause 时的回调
     */
    void onViewerPause();
}
