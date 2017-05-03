package com.mozre.find.presenter;

import android.content.Context;
import android.util.Log;

import com.mozre.find.app.BasePresenter;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.module.main.HotView;
import com.mozre.find.util.HttpUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by MOZRE on 2016/7/4.
 */
public class PostHotPresenter extends BasePresenter {

    private static final String TAG = "PostHotPresenter";
    private Context mContext;
    private HotView mView;

    public PostHotPresenter(Context mContext, HotView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    public void postRefreshData() {

    }

    public void postRefreshDataFromServer() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        OkHttpClient client = HttpUtils.getClient();
                        StringBuilder builder = new StringBuilder("/hot?").append("flag=").append(true);
                        Request request = HttpUtils.getCommonBuilder(builder.toString()).tag(PostHotPresenter.this).build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                subscriber.onNext(response.body().string());
                            }
                        });


                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<PostArticleData>>() {
                    @Override
                    public List<PostArticleData> call(String s) {
                        List<PostArticleData> mDatas = null;
                        Log.d(TAG, "call: getit");
                        
                        
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PostArticleData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PostArticleData> postArticleDatas) {

                    }
                });
    }

    @Override
    public void onDestroy() {

    }
}
