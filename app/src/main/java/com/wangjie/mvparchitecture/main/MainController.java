package com.wangjie.mvparchitecture.main;

import android.view.View;
import android.widget.AdapterView;

import com.wangjie.mvparchitecture.R;
import com.wangjiegulu.mvparchitecture.library.controller.BaseController;
import com.wangjiegulu.mvparchitecture.library.viewer.Viewer;

import static com.wangjie.mvparchitecture.main.MainContract.IMainController;
import static com.wangjie.mvparchitecture.main.MainContract.IMainPresenter;
import static com.wangjie.mvparchitecture.main.MainContract.IMainViewer;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 6/27/16.
 */
public class MainController extends BaseController implements IMainController {
    private IMainViewer mViewer;
    private IMainPresenter mPresenter;

    public MainController(IMainViewer viewer) {
        mViewer = viewer;
    }

    @Override
    public void bind(Viewer bindViewer) {
        super.bind(bindViewer);

        // TODO: 6/27/16 Need inject use Dagger2
        mPresenter = new MainPresenter(mViewer);
        mPresenter.bind(mViewer);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.activity_main_load_data_btn:
                mPresenter.loadData();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mViewer.showToast("click position: " + position);
    }
}
