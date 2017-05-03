package com.mozre.find.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mozre.find.app.BasePresenter;
import com.mozre.find.module.account.RegisterView;
import com.mozre.find.domain.User;
import com.mozre.find.util.HttpUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by MOZRE on 2016/6/17.
 */
public class RegisterPresenter extends BasePresenter {

    private Context mContext;
    private RegisterView mRegisterView;
    private static final String TAG = "RegisterPresenter";
    private RegisterPresenter registerPresenter;

//    private RegisterActivity() {
//
//    }
//
//    public RegisterPresenter getInstance() {
//        if (registerPresenter == null) {
//            registerPresenter = new RegisterPresenter();
//        }
//        return registerPresenter;
//    }


    public RegisterPresenter(Context mContext, RegisterView mRegisterView) {
        this.mContext = mContext;
        this.mRegisterView = mRegisterView;
    }

    public void checkUserNameAvailable(final String tmpUserName) {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        StringBuilder sb = new StringBuilder("/check?");
                        sb.append("username=").append(tmpUserName);
                        OkHttpClient client = HttpUtils.getClient();
                        Request request = HttpUtils.getCommonBuilder(sb.toString()).get().tag(this).build();
                        try {
                            Response response = client.newCall(request).execute();
                            String resultStr = response.body().string();
                            subscriber.onNext(resultStr);
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
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

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.d(TAG, "onNext: result = " + aBoolean);
                        mRegisterView.changeViewMode(aBoolean);
                    }
                });
    }

    public void registerNewUser(final User user) throws Exception {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        OkHttpClient client = HttpUtils.getClient();
                        StringBuilder sb = new StringBuilder("/register");
                        JSONObject object = new JSONObject();
                        object.put("user", user);
                        Log.d(TAG, "call: object = " + object);
                        FormBody body = new FormBody.Builder().add("user", object.toJSONString()).build();
                        Request request = HttpUtils.getCommonBuilder(sb.toString()).post(body).tag(this).build();
                        try {
                            Response response = client.newCall(request).execute();
                            subscriber.onNext(response.body().string());
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        Log.d(TAG, "call: result = " + s);
                        if (s != null && s.length() > 0) {
                            JSONObject obj = JSONObject.parseObject(s);

                            Boolean result = obj.getBoolean("result");
                            return result;
                        }
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
                    public void onNext(Boolean result) {
                        if (result) {
                            mRegisterView.toLoginView(result);
                        }
                    }
                });


    }


    @Override
    public void onDestroy() {
        HttpUtils.cancel(this);
    }

}
