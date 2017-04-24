package com.tangxb.basic.something.mvp.presenter;

import android.util.Log;

import com.tangxb.basic.something.bean.WelfareBean;
import com.tangxb.basic.something.mvp.model.BaseModel;
import com.tangxb.basic.something.mvp.model.BaseModelImpl;
import com.tangxb.basic.something.mvp.view.BaseActivityView;
import com.tangxb.basic.something.okhttp.OkHttpUtils;
import com.tangxb.basic.something.okhttp.callback.StringCallback;

import java.util.List;

import rx.Observable;

/**
 * Created by Tangxb on 2017/2/16.
 */

public class SimpleActivityPresenter extends BaseActivityPresenter {
    BaseModel<WelfareBean> model;

    public SimpleActivityPresenter(BaseActivityView baseActivityView) {
        super(baseActivityView);
        model = new BaseModelImpl();
        testOkHttpPostForm();
    }

    public Observable<List<WelfareBean>> createObservable(String category, int pageSize, int pageNum) {
        return model.getCommonList(category, pageSize, pageNum);
    }

    /**
     * <a href="https://github.com/jeasonlzy/okhttp-OkGo/blob/master/app/src/main/java/com/lzy/demo/utils/Urls.java">可以测试使用的地址</a>
     */
    private void testOkHttpPostForm() {
        String url = "http://server.jeasonlzy.com/OkHttpUtils/upload";
        OkHttpUtils.post().tag("tag").id(120).url(url).addParams("key1", "value").build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                Log.d("onError", "Exception===" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("onResponse", "response===" + response);
            }
        });
    }
}
