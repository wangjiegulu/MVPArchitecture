package com.wangjie.mvparchitecture.main;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wangjie.mvparchitecture.R;
import com.wangjie.mvparchitecture.base.BaseActivity;

import java.util.List;

import static com.wangjie.mvparchitecture.main.MainContract.IMainViewer;

public class MainActivity extends BaseActivity implements IMainViewer {

    private ListView dataLv;

    private MainController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: 6/27/16 Need inject use Dagger2
        mController = new MainController(this);
        dataLv = findViewById(R.id.activity_main_lv);

        dataLv.setOnItemClickListener(mController);
        findViewById(R.id.activity_main_load_data_btn).setOnClickListener(mController);

        mController.bind(this);

    }

    @Override
    public void onLoadData(List<String> dataList) {
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, dataList);
        dataLv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
