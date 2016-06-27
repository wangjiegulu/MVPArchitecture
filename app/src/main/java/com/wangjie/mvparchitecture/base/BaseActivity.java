package com.wangjie.mvparchitecture.base;

import com.wangjie.mvparchitecture.library.contract.OnViewerDestroyListener;
import com.wangjie.mvparchitecture.library.contract.OnViewerLifecycleListener;
import com.wangjie.mvparchitecture.library.viewer.Viewer;
import com.wangjie.mvparchitecture.library.viewer.ViewerDelegate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 6/27/16.
 */
public class BaseActivity extends AppCompatActivity implements Viewer {
    private ViewerDelegate mViewerDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewerDelegate = new ViewerDelegate(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewerDelegate.onViewerResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewerDelegate.onViewerPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
