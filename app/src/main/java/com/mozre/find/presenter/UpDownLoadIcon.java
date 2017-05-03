package com.mozre.find.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mozre.find.app.BasePresenter;
import com.mozre.find.module.main.HomeView;
import com.mozre.find.util.HttpUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by MOZRE on 2016/6/23.
 */
public class UpDownLoadIcon extends BasePresenter {

    private static final String TAG = "UpDownLoadIcon";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private HomeView mView;

    public UpDownLoadIcon(HomeView mView) {
        this.mView = mView;
    }

    public void postIconToServer(final String uri, final String username) {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        Log.d(TAG, "call: uri = " + uri);
                        OkHttpClient client = HttpUtils.getClient();
                        File file = new File(uri);
                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("file", username+".png", RequestBody.create(MEDIA_TYPE_PNG, file))
                                .addFormDataPart("username", username)
                                .build();
                        Request request = HttpUtils.getCommonBuilder("/usericon").post(requestBody).tag(UpDownLoadIcon.this).build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });


                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        JSONObject object = JSON.parseObject(s);
                        Boolean result = object.getBoolean("result");
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
                        Log.d(TAG, "onError: ");
                        mView.makeMessage(false);
                    }

                    @Override
                    public void onNext(Boolean o) {
                        Log.d(TAG, "onNext: ");
                        mView.makeMessage(o);
                    }
                });
    }

    @Override
    public void onDestroy() {
        HttpUtils.cancel(this);
    }
}
