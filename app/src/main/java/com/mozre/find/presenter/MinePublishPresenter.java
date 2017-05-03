package com.mozre.find.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mozre.find.app.BasePresenter;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.domain.User;
import com.mozre.find.profile.MinePublishInfoView;
import com.mozre.find.util.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
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
 * Created by MOZRE on 2016/7/6.
 */
public class MinePublishPresenter extends BasePresenter {
    private static final String TAG = "MinePublishPresenter";
    private Context mContext;
    private MinePublishInfoView mView;
    private PostDataDao mDao;

    public MinePublishPresenter(Context mContext, MinePublishInfoView mView) {
        this.mContext = mContext;
        this.mView = mView;
        mDao = new PostDataDao(mContext);
    }


    public List<PostArticleData> refreshNewData() throws Exception {
        Log.d(TAG, "refreshNewData: hrer");
        List<PostArticleData> mDatas = null;
        mDatas = mDao.selectMineData();

        return mDatas;
    }

    public void postRefreshNewData(final long seconds) throws Exception {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        OkHttpClient client = HttpUtils.getClient();
                        User user = new User();
                        StringBuilder builder = new StringBuilder("/mine?").append("username=").append(user.getUserName())
                                .append("&token=").append(user.getToken()).append("&seconds=").append(seconds);
                        Request request = HttpUtils.getCommonBuilder(builder.toString()).tag(MinePublishPresenter.this).build();
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
                        JSONObject jsonObject = JSON.parseObject(s);
                        Log.d(TAG, "call: s = " + s);
                        boolean result = jsonObject.getBoolean("result");
                        if (result) {
                            String dataStr = jsonObject.getString("data");
//                            Log.d(TAG, "call: datastr = " + dataStr);
                            mDatas = new ArrayList<>(JSONArray.parseArray(dataStr, PostArticleData.class));
                            try {
                                mDao.insertMineData(mDatas);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return mDatas;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PostArticleData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<PostArticleData> resultData) {
                        if (resultData != null && resultData.size() > 0) {
                            Log.d(TAG, "onNext: resultData" + resultData.size());
                            mView.notifyRecentData(resultData);
                        } else {
                            mView.isRecentData();
                        }
                    }
                });
    }

    public void postLoadMoreData(final long seconds) {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        OkHttpClient client = HttpUtils.getClient();
                        User user = new User();
                        StringBuilder builder = new StringBuilder("/loadmine?")
                                .append("username=")
                                .append(user.getUserName())
                                .append("&token=")
                                .append(user.getToken())
                                .append("&seconds=")
                                .append(seconds);
                        Request request = HttpUtils.getCommonBuilder(builder.toString()).tag(MinePublishPresenter.this).build();
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
                        JSONObject jsonObject = JSON.parseObject(s);
                        Log.d(TAG, "call: s = " + s);
                        boolean result = jsonObject.getBoolean("result");
                        if (result) {
                            String dataStr = jsonObject.getString("data");
//                            Log.d(TAG, "call: datastr = " + dataStr);
                            mDatas = new ArrayList<>(JSONArray.parseArray(dataStr, PostArticleData.class));
//                            try {
//                                mDao.insertMineData(mDatas);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                        }
                        return mDatas;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PostArticleData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<PostArticleData> mDatas) {
                        if (mDatas == null || mDatas.size() < 1) {
                            mView.isLatestData();
                        } else {
                            mView.notifyLatestData(mDatas);
                        }
                    }
                });

    }

    @Override
    public void onDestroy() {
        HttpUtils.cancel(this);
    }

}
