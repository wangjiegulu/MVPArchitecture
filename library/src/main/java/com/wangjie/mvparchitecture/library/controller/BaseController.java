package com.wangjie.mvparchitecture.library.controller;

import com.wangjie.mvparchitecture.library.viewer.Viewer;

/**
 * Controller层的默认空实现
 * 即在回调中不做任何处理
 *
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/12/16.
 */
public class BaseController implements Controller {
//    V viewer;
//    P presenter;

    //    public BaseController(V viewer) {
//        this.viewer = viewer;
//    }
//
//    public void setPresenter(P presenter) {
//        this.presenter = presenter;
//    }
//
//    public View getView(int resId) {
//        return viewer.findView(resId);
//    }
//
//    public <T> T getView(int resId, Class<T> clazz) {
//        return (T) getView(resId);
//    }

    /**
     * 提供View简单生命周期的绑定
     *
     * 该方法还可以让子类重写用于做为Controller的初始化方法,但是注意重写的时候必须要调用super.bind()!
     * @param bindViewer
     */
    public void bind(Viewer bindViewer) {
        bindViewer.bind(this);
    }

    @Override
    public void onViewerResume() {
        // ignore
    }

    @Override
    public void onViewerPause() {
        // ignore
    }

    @Override
    public void onViewerDestroy() {
        // ignore
    }
}
