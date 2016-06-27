package com.wangjie.mvparchitecture.library.viewer;

import com.wangjie.mvparchitecture.library.contract.OnViewerDestroyListener;
import com.wangjie.mvparchitecture.library.contract.OnViewerLifecycleListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 真正的Viewer的委托
 * 这里处理大部分Viewer层与Controller/Presenter层的绑定和回调
 *
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/12/16.
 */
public class ViewerDelegate implements Viewer, OnViewerLifecycleListener {
    /**
     * 当 viewer 销毁时最好清空掉context
     */
    private Context mContext;

    public ViewerDelegate(Context context) {
        mContext = context;
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

    private Toast toast;
    private ProgressDialog loadingDialog;

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
    public Context context() {
        return mContext;
    }

    @Override
    public void showToast(String message) {
        if (!checkViewer()) {
            return;
        }
        if (null == toast) {
            toast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.setText(message);
        toast.show();
    }

    @Override
    public void showToast(int resStringId) {
        if (!checkViewer()) {
            return;
        }
        showToast(mContext.getString(resStringId));
    }

    @Override
    public void showLoadingDialog(String message) {
        if (!checkViewer()) {
            return;
        }

        if (null == loadingDialog) {
            loadingDialog = new ProgressDialog(mContext);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        loadingDialog.setMessage(message);
        loadingDialog.show();
    }

    @Override
    public void showLoadingDialog(int resStringId) {
        if (!checkViewer()) {
            return;
        }
        showLoadingDialog(mContext.getString(resStringId));
    }

    @Override
    public void cancelLoadingDialog() {
        if (!checkViewer()) {
            return;
        }
        if (null != loadingDialog) {
            loadingDialog.cancel();
        }
    }

    /**
     * 检测context是否为null
     * 因为在Viewer销毁时会调用onViewerDestroy,会置空context,所以如果context不是null,则说明Viewer还存在
     */
    private boolean checkViewer() {
        if (null == mContext) {
            return false;
        }
        if (mContext instanceof Activity && isActivityFinishingOrDestroy((Activity) mContext)) {
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
        mContext = null;
        mOnViewerDestroyListeners = null;
        mOnViewerLifecycleListeners = null;
    }

    private boolean isActivityFinishingOrDestroy(Activity activity) {
        if (Build.VERSION.SDK_INT >= 17) {
            return activity.isFinishing() || activity.isDestroyed();
        } else {
            return activity.isFinishing();
        }
    }
}