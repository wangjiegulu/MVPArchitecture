package com.wangjiegulu.mvparchitecture.library.viewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 真正的Viewer的委托的默认实现，子类可以自己自定义
 * 这里处理大部分Viewer层与Controller/Presenter层的绑定和回调
 * <p>
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/12/16.
 */
public class ViewerDelegateDefault extends ViewerAbstractDelegate {

    private Toast toast;
    private ProgressDialog loadingDialog;

    public ViewerDelegateDefault(Context context) {
        super(context);
    }

    @Override
    public void showToast(String message) {
        if (!checkViewer()) {
            return;
        }
        if (null == toast) {
            toast = Toast.makeText(mContextRef.get(), "", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.setText(message);
        toast.show();
    }

    @Override
    public void showLoadingDialog(String message) {
        if (!checkViewer()) {
            return;
        }

        if (null == loadingDialog) {
            loadingDialog = new ProgressDialog(mContextRef.get());
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        loadingDialog.setMessage(message);
        loadingDialog.show();
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

}