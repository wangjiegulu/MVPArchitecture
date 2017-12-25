package com.wangjie.mvparchitecture.library.viewer;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;

import com.wangjie.mvparchitecture.library.contract.OnViewerDestroyListener;
import com.wangjie.mvparchitecture.library.contract.OnViewerLifecycleListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/2/16.
 */
public abstract class ViewerAbstractDelegate implements Viewer, OnViewerLifecycleListener {
    /**
     * 当 viewer 销毁时最好清空掉context
     */
    protected WeakReference<Context> mContextRef;

    public ViewerAbstractDelegate(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    /**
     * Viewer destroy 时的回调
     * 一般情况下只会有Presenter一个对象
     * 但是由于一个Viewer是可以有多个Presenter的,所以可能会维护一个Presenter列表
     * 还有可能是其他需要关心 Viewer destroy 的组件
     */
    private List<OnViewerDestroyListener> mOnViewerDestroyListeners;
    /**
     * Viewer 简单的生命周期监听对象
     * 一般情况下只有一个Controller一个对象
     * 但是一个Viewer并不限制只有一个Controller对象,所以可能会维护一个Controller列表
     * 还有可能是其他关心 Viewer 简单生命周期的组件
     */
    private List<OnViewerLifecycleListener> mOnViewerLifecycleListeners;

    /**
     * 增加一个Viewer 生命周期绑定
     */
    @Override
    public Viewer bind(OnViewerLifecycleListener onViewerLifecycleListener) {
        if (null == mOnViewerLifecycleListeners) {
            mOnViewerLifecycleListeners = new ArrayList<>();
            mOnViewerLifecycleListeners.add(onViewerLifecycleListener);
        } else {
            if (!mOnViewerLifecycleListeners.contains(onViewerLifecycleListener)) {
                mOnViewerLifecycleListeners.add(onViewerLifecycleListener);
            }
        }
        return this;
    }

    /**
     * 增加一个Viewer Destroy绑定
     */
    @Override
    public Viewer bind(OnViewerDestroyListener onViewerDestroyListener) {
        if (null == mOnViewerDestroyListeners) {
            mOnViewerDestroyListeners = new ArrayList<>();
            mOnViewerDestroyListeners.add(onViewerDestroyListener);
        } else {
            if (!mOnViewerDestroyListeners.contains(onViewerDestroyListener)) {
                mOnViewerDestroyListeners.add(onViewerDestroyListener);
            }
        }
        return this;
    }

    @Override
    @Nullable
    public Context context() {
        return null == mContextRef ? null : mContextRef.get();
    }

    @Override
    public void showToast(int resStringId) {
        if (!checkViewer()) {
            return;
        }
        showToast(mContextRef.get().getString(resStringId));
    }

    @Override
    public void showLoadingDialog(int resStringId) {
        if (!checkViewer()) {
            return;
        }
        showLoadingDialog(mContextRef.get().getString(resStringId));
    }

    /**
     * 检测context是否为null
     * 因为在Viewer销毁时会调用onViewerDestroy,会置空context,所以如果context不是null,则说明Viewer还存在
     */
    protected boolean checkViewer() {
        Context context;
        if (null == mContextRef || null == (context = mContextRef.get())) {
            return false;
        }
        if (context instanceof Activity && isActivityFinishingOrDestroy((Activity) context)) {
            return false;
        }
        return true;
    }

    @Override
    public void onViewerResume() {
        if (null != mOnViewerLifecycleListeners) {
            for (OnViewerLifecycleListener oll : mOnViewerLifecycleListeners) {
                oll.onViewerResume();
            }
        }
    }

    @Override
    public void onViewerPause() {
        if (null != mOnViewerLifecycleListeners) {
            for (OnViewerLifecycleListener oll : mOnViewerLifecycleListeners) {
                oll.onViewerPause();
            }
        }
    }

    @Override
    public void onViewerDestroy() {
        if (null != mOnViewerLifecycleListeners) {
            for (OnViewerLifecycleListener oll : mOnViewerLifecycleListeners) {
                oll.onViewerDestroy();
            }
        }
        if (null != mOnViewerDestroyListeners) {
            for (OnViewerDestroyListener odl : mOnViewerDestroyListeners) {
                odl.onViewerDestroy();
            }
        }
        if(null != mContextRef){
            if(null != mContextRef.get()){
                mContextRef.clear();
            }
            mContextRef = null;
        }
        mOnViewerDestroyListeners = null;
        mOnViewerLifecycleListeners = null;
    }

    protected boolean isActivityFinishingOrDestroy(Activity activity) {
        if (Build.VERSION.SDK_INT >= 17) {
            return activity.isFinishing() || activity.isDestroyed();
        } else {
            return activity.isFinishing();
        }
    }
}
