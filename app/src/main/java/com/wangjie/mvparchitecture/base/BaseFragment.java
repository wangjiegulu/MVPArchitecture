package com.wangjie.mvparchitecture.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.wangjiegulu.mvparchitecture.library.contract.OnViewerDestroyListener;
import com.wangjiegulu.mvparchitecture.library.contract.OnViewerLifecycleListener;
import com.wangjiegulu.mvparchitecture.library.viewer.Viewer;
import com.wangjiegulu.mvparchitecture.library.viewer.ViewerDelegateDefault;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 6/27/16.
 */
public class BaseFragment extends Fragment implements Viewer {
    private ViewerDelegateDefault mViewerDelegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewerDelegate = new ViewerDelegateDefault(getContext());
    }


    @Override
    public void onResume() {
        super.onResume();
        mViewerDelegate.onViewerResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewerDelegate.onViewerPause();
    }

    @Override
    public void onDestroy() {
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
