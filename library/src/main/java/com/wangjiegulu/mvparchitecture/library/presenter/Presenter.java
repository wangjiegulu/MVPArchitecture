package com.wangjiegulu.mvparchitecture.library.presenter;

import com.wangjiegulu.mvparchitecture.library.contract.OnViewerDestroyListener;
import com.wangjiegulu.mvparchitecture.library.viewer.Viewer;

/**
 * MVP的Presenter层，作为沟通 View 和 Model 的桥梁，它从 Model 层检索数据后，返回给 View 层，它也可以决定与 View 层的交互操作。
 * 它包含一个 View 层的引用和一个或者多个 Model 层的引用
 *
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/13/16.
 */
public interface Presenter extends OnViewerDestroyListener {
    /**
     * 提供对Viewer销毁状态的绑定
     *
     * 该方法还可以让子类重写用于做为Presenter的初始化方法,但是注意重写的时候必须要调用super.bind()!
     */
    void bind(Viewer bindViewer);

    /**
     * 所有task操作需要在Viewer销毁时(通过上面的bind方法绑定)停止对UI的回调
     */
    void closeAllTask();
}
