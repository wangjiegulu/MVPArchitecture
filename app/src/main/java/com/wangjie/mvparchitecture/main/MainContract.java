package com.wangjie.mvparchitecture.main;

import com.wangjiegulu.mvparchitecture.library.controller.Controller;
import com.wangjiegulu.mvparchitecture.library.presenter.Presenter;
import com.wangjiegulu.mvparchitecture.library.viewer.Viewer;

import android.view.View;
import android.widget.AdapterView;

import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 6/27/16.
 */
public interface MainContract {
    interface IMainViewer extends Viewer{
        void onLoadData(List<String> dataList);
    }

    interface IMainController extends Controller, View.OnClickListener, AdapterView.OnItemClickListener{

    }

    interface IMainPresenter extends Presenter{
        void loadData();
    }

}
