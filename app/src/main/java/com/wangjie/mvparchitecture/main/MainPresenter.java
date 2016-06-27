package com.wangjie.mvparchitecture.main;

import com.wangjie.mvparchitecture.library.rx.presenter.RxBasePresenter;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

import static com.wangjie.mvparchitecture.main.MainContract.IMainPresenter;
import static com.wangjie.mvparchitecture.main.MainContract.IMainViewer;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 6/27/16.
 */
public class MainPresenter extends RxBasePresenter implements IMainPresenter {
    private IMainViewer mViewer;

    public MainPresenter(IMainViewer viewer) {
        mViewer = viewer;
        bind(viewer);
    }

    @Override
    public void loadData() {
        goSubscription(
                Observable.from(Arrays.asList("item0", "item1", "item2", "item3", "item4", "item5", "item6"))
                        .toList()
                        .subscribe(new Subscriber<List<String>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<String> strings) {
                                mViewer.onLoadData(strings);
                            }
                        })
        );
    }
}
