package com.wangjie.mvparchitecture.library.rx.presenter;

import com.wangjie.mvparchitecture.library.viewer.Viewer;

import android.util.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

import rx.Subscription;

/**
 * 对Presenter的RxJava的实现
 * 在这里,所有的异步操作都应该使用RxJava
 *
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/12/16.
 */
public class RxBasePresenter implements RxPresenter {
    private static final String TAG = RxBasePresenter.class.getSimpleName();
    /**
     * 订阅的缓存集合
     * 理论上子类中所有的RxJava订阅都需要加入到该Set中进行缓存
     */
    private final Set<Subscription> subscriptions = Collections.newSetFromMap(new WeakHashMap<Subscription, Boolean>());

    /**
     * 在Viewer被销毁时调用该方法进行关闭所有Task
     */
    @Override
    public void closeAllTask() {
        synchronized (subscriptions) {
            Iterator iter = this.subscriptions.iterator();
            while (iter.hasNext()) {
                Subscription subscription = (Subscription) iter.next();
                Log.i(TAG, "closeAllTask[subscriptions]: " + subscription);
                if (null != subscription && !subscription.isUnsubscribed()) {
                    subscription.unsubscribe();
                }
                iter.remove();
            }
        }
    }

    /**
     * 把订阅加入到Set缓存中
     */
    @Override
    public void goSubscription(Subscription subscription) {
        synchronized (subscriptions) {
            this.subscriptions.add(subscription);
        }
    }

    /**
     * 订阅完成后冲Set缓存中移除掉该订阅
     * 但是注意这里不需要手动去调用了,因为Set中使用了弱引用,订阅完成后,gc会自动回收
     */
    @Override
    public void removeSubscription(Subscription subscription) {
        synchronized (subscriptions) {
            Log.i(TAG, "removeSubscription: " + subscription);
            if (null != subscription && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
            this.subscriptions.remove(subscription);
        }
    }

    /**
     * 提供对Viewer销毁状态的绑定
     *
     * 该方法还可以让子类重写用于做为Presenter的初始化方法,但是注意重写的时候必须要调用super.bind()!
     * @param bindViewer
     */
    @Override
    public void bind(Viewer bindViewer) {
        bindViewer.bind(this);
    }

    @Override
    public void onViewerDestroy() {
        closeAllTask();
    }
}
