package com.wangjie.mvparchitecture.library.rx.presenter;

import com.wangjie.mvparchitecture.library.presenter.Presenter;

import rx.Subscription;

/**
 * 支持RxJava的Presenter
 *
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/12/16.
 */
public interface RxPresenter extends Presenter {
    /**
     * 使用RxJava订阅一个Observable时,需要调用该方法缓存本次订阅
     */
    void goSubscription(Subscription subscription);

    /**
     * 可以在订阅者 completed 时调用该方法移除掉缓存中的订阅
     */
    void removeSubscription(Subscription subscription);

}
