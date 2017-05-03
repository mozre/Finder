package com.mozre.find.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.mozre.find.app.BasePresenter;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.domain.User;
import com.mozre.find.module.middle.NewMessageView;
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
 * Created by MOZRE on 2016/6/27.
 */
public class NewMessagePresenter extends BasePresenter {

    private static final String TAG = "NewMessagePresenter";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final String ARTICLE = "articlepost";
    private static final String FILM = "filmpost";
    private NewMessageView mView;
    private Context mContext;

    public NewMessagePresenter(NewMessageView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    public void postNewArticleMessage(final PostArticleData mData) {
        final User user = new User(mContext);
//        Log.d(TAG, "postNewArticleMessage: " + user.getUserName());
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        String username = user.getUserName();
                        String token = user.getToken();
//                        Log.d(TAG, "call: uri = " + uri);
//                        Log.d(TAG, "call: file = " + file + " " + file.getAbsolutePath());
//                        mData.setImageUri(null);
                        JSONObject object = new JSONObject();
                        object.put("data", mData);
                        OkHttpClient client = HttpUtils.getClient();
//                        client.
                        RequestBody requestBody = null;
                        if (mData.getImageUri() != null) {
                            File file = new File(mData.getImageUri());
                            requestBody = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("file", username + String.valueOf(mData.getSeconds()) + ".png", RequestBody.create(MEDIA_TYPE_PNG, file))
                                    .addFormDataPart("username", user.getUserName())
                                    .addFormDataPart("token", user.getToken())
                                    .addFormDataPart("iconaddress", user.getUserIconAddress())
                                    .addFormDataPart("data", object.toJSONString())
                                    .addFormDataPart("table", ARTICLE)
                                    .build();
                        } else {
                            Log.d(TAG, "call: " + user.getUserIconAddress());
                            requestBody = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("username", user.getUserName())
                                    .addFormDataPart("token", user.getToken())
                                    .addFormDataPart("iconaddress", user.getUserIconAddress())
                                    .addFormDataPart("data", object.toJSONString())
                                    .addFormDataPart("table", ARTICLE)
                                    .build();
                        }
                        final Request request = HttpUtils.getCommonBuilder("/newarticle").post(requestBody).tag(NewMessagePresenter.this).build();
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
                    public Boolean call(String resultStr) {
                        JSONObject jsonObject = JSONObject.parseObject(resultStr);
                        Log.d(TAG, "call: ResultStr = " + resultStr);
                        Boolean result = jsonObject.getBoolean("result");
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
                    public void onNext(Boolean o) {
                        mView.publishState(o);
                    }
                });
    }
    public void postNewFlimMessage(final PostArticleData mData) {
        final User user = new User(mContext);
//        Log.d(TAG, "postNewArticleMessage: " + user.getUserName());
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        String username = user.getUserName();
                        String token = user.getToken();
//                        Log.d(TAG, "call: uri = " + uri);
//                        Log.d(TAG, "call: file = " + file + " " + file.getAbsolutePath());
//                        mData.setImageUri(null);
                        JSONObject object = new JSONObject();
                        object.put("data", mData);
                        OkHttpClient client = HttpUtils.getClient();
//                        client.
                        RequestBody requestBody = null;
                        if (mData.getImageUri() != null) {
                            File file = new File(mData.getImageUri());
                            requestBody = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("file", username + String.valueOf(mData.getSeconds()) + ".png", RequestBody.create(MEDIA_TYPE_PNG, file))
                                    .addFormDataPart("username", user.getUserName())
                                    .addFormDataPart("token", user.getToken())
                                    .addFormDataPart("iconaddress", user.getUserIconAddress())
                                    .addFormDataPart("data", object.toJSONString())
                                    .addFormDataPart("table", FILM)
                                    .build();
                        } else {
                            requestBody = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("username", user.getUserName())
                                    .addFormDataPart("token", user.getToken())
                                    .addFormDataPart("iconaddress", user.getUserIconAddress())
                                    .addFormDataPart("data", object.toJSONString())
                                    .addFormDataPart("table", FILM)
                                    .build();
                        }
                        final Request request = HttpUtils.getCommonBuilder("/newarticle").post(requestBody).tag(NewMessagePresenter.this).build();
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
                    public Boolean call(String resultStr) {
                        JSONObject jsonObject = JSONObject.parseObject(resultStr);
                        Log.d(TAG, "call: ResultStr = " + resultStr);
                        Boolean result = jsonObject.getBoolean("result");
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
                    public void onNext(Boolean o) {
                        mView.publishState(o);
                    }
                });

    }


    @Override
    public void onDestroy() {
        HttpUtils.cancel(this);
    }


}
