# MVPArchitecture

Best Practices of MVP architecture. MVP framework for Android

Blog in Chinese: <http://www.cnblogs.com/tiantianbyconan/p/5422443.html>

![](https://raw.githubusercontent.com/wangjiegulu/wangjiegulu.github.com/master/images/mvp/MVP_Controller.jpg)

## How to use

### 1. Compile it in `build.gradle`

#### Gadle([Check newest version](http://search.maven.org/#search%7Cga%7C1%7Cmvparchitecture))

```groovy
compile "com.github.wangjiegulu:mvparchitecture:x.x.x"

compile "com.github.wangjiegulu:mvparchitecture-rx:x.x.x"
```

### 2. Viewer implementation

Viewer implementation use `ViewerDelegateDefault` in Activity/Fragment/ViewGroup/Dialog...

BaseActivity:

```java
public class BaseActivity extends AppCompatActivity implements Viewer {
    private ViewerDelegateDefault mViewerDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewerDelegate = new ViewerDelegateDefault(this);
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
```

### 3. Bind `controller` to `Viewer`

MainActivity:

```java
public class MainActivity extends BaseActivity implements IMainViewer {
    // ...
    private MainController mController;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: 6/27/16 Need inject use Dagger2
        mController = new MainController(this);
        recyclerView.setOnItemClickListener(mController);
        mController.bind(this);
    }
    
    @Override
    public void onLoadData(List<String> dataList) {
        // ...
    }
```

### 4. Bind `Presenter` to `Controller`

MainController:

```java
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
        // ...
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // ...
    }
}
```

### 5. Bind `Presenter` to `Viewer`

```java
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
```

License
=======

    Copyright 2016 Wang Jie

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



