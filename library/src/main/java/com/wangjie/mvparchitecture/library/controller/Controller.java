package com.wangjie.mvparchitecture.library.controller;

import com.wangjie.mvparchitecture.library.contract.OnViewerLifecycleListener;
import com.wangjie.mvparchitecture.library.viewer.Viewer;

/**
 * Controller层
 * Viewer 应该持有Controller层的引用,Viewer层可以调用Controller来进行任何事件的绑定
 * 这样所有事件的回调都会在Controller层中进行,然后Controller层根据具体的回调来进行不同事件往Presenter层的派发
 *
 * 该层可选
 * 如果Viewer并不复杂,这一层可以省略,如果没有Controller, Viewer中就需要处理一些额外的事件回调派发工作
 * 可以考虑优先舍弃Controller,之后随着业务逻辑的复杂化再加上Controller层来进一步解耦
 *
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/12/16.
 */
public interface Controller extends OnViewerLifecycleListener {
    /**
     * 提供View简单生命周期的绑定
     *
     * 该方法还可以让子类重写用于做为Controller的初始化方法,但是注意重写的时候必须要调用super.bind()!
     */
    void bind(Viewer bindViewer);

//    void bindEvents();

}
