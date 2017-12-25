package com.wangjie.mvparchitecture.main;

import android.util.Log;

import com.wangjie.mvparchitecture.library.rx.presenter.RxBasePresenter;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.wangjie.mvparchitecture.main.MainContract.IMainPresenter;
import static com.wangjie.mvparchitecture.main.MainContract.IMainViewer;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 6/27/16.
 */
public class MainPresenter extends RxBasePresenter implements IMainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();

    private WeakReference<IMainViewer> mViewer;

    public MainPresenter(IMainViewer viewer) {
        mViewer = new WeakReference<>(viewer);
        bind(viewer);
    }

    @Override
    public void loadData() {
        Observable.fromArray("item0", "item1", "item2", "item3", "item4", "item5", "item6")
                .subscribeOn(Schedulers.newThread())
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        attachDisposable(d);
                    }

                    @Override
                    public void onSuccess(List<String> strings) {
                        mViewer.get().onLoadData(strings);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "", e);
                    }
                });
    }
}
