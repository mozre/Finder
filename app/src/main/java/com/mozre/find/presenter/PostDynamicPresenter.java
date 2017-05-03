package com.mozre.find.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mozre.find.app.BasePresenter;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.domain.User;
import com.mozre.find.module.middle.ForwardView;
import com.mozre.find.module.middle.ReviewView;
import com.mozre.find.util.HttpUtils;

import java.io.IOException;

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
 * Created by MOZRE on 2016/7/1.
 */
public class PostDynamicPresenter extends BasePresenter {

    private Context mContext;
    private ReviewView mView;
    private ForwardView mForwardView;
    private User user;

    public PostDynamicPresenter(Context mContext, ReviewView mView) {
        user = new User();
        this.mContext = mContext;
        this.mView = mView;
    }

    public PostDynamicPresenter(Context mContext, ForwardView mForwardView) {
        user = new User();
        this.mContext = mContext;
        this.mForwardView = mForwardView;
    }

    public void postNewReview(final String review, PostArticleData data) {
        final User user = new User();
        final String id = data.getUsername() + data.getSeconds();
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        OkHttpClient client = HttpUtils.getClient();
                        StringBuilder builder = new StringBuilder("/postreview?")
                                .append("username=")
                                .append(user.getUserName()).append("&token=")
                                .append(user.getToken())
                                .append("&review=")
                                .append(review)
                                .append("&id=")
                                .append(id);
                        Request request = HttpUtils.getCommonBuilder(builder.toString())
                                .tag(PostDynamicPresenter.this).build();
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
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        Boolean result = false;
                        if (s != null && s.length() > 0) {
                            JSONObject object = JSON.parseObject(s);
                            result = object.getBoolean("result");
                        }
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        mView.reviewState(aBoolean);
                    }
                });
    }

    public void postNewForward(PostArticleData data) {

        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        OkHttpClient client = HttpUtils.getClient();
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {

                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                });
    }

    @Override
    public void onDestroy() {
        HttpUtils.cancel(this);
    }


}
