package com.wangjie.mvparchitecture.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.wangjiegulu.mvparchitecture.library.contract.OnViewerDestroyListener;
import com.wangjiegulu.mvparchitecture.library.contract.OnViewerLifecycleListener;
import com.wangjiegulu.mvparchitecture.library.viewer.Viewer;
import com.wangjiegulu.mvparchitecture.library.viewer.ViewerDelegateDefault;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 6/27/16.
 */
public class BaseFrameLayout extends FrameLayout implements Viewer {
    private ViewerDelegateDefault mViewerDelegate;

    public BaseFrameLayout(Context context) {
        super(context);
        initialize();
    }

    public BaseFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }


    public void initialize() {
        mViewerDelegate = new ViewerDelegateDefault(getContext());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mViewerDelegate.onViewerResume();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mViewerDelegate.onViewerDestroy();
    }

    /**
     * Need call when destroy
     */
    public void onDestroy() {
        mViewerDelegate.onViewerDestroy();
    }

    @Override
    public Viewer bind(OnViewerLifecycleListener onViewerLifecycleListener) {
        return mViewerDelegate.bind(onViewerLifecycleListener);
    }

    @Override
    public Viewer bind(OnViewerDestroyListener onViewerDestroyListener) {
        return mViewerDelegate.bind(onViewerDestroyListener);
    }

    @Override
    public Context context() {
        return mViewerDelegate.context();
    }

    @Override
    public void showToast(String message) {
        mViewerDelegate.showToast(message);
    }

    @Override
    public void showToast(int resStringId) {
        mViewerDelegate.showToast(resStringId);
    }

    @Override
    public void showLoadingDialog(String message) {
        mViewerDelegate.showLoadingDialog(message);
    }

    @Override
    public void showLoadingDialog(int resStringId) {
        mViewerDelegate.showLoadingDialog(resStringId);
    }

    @Override
    public void cancelLoadingDialog() {
        mViewerDelegate.cancelLoadingDialog();
    }
}
