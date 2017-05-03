package com.mozre.find.module.main;

import com.mozre.find.app.BaseView;
import com.mozre.find.domain.PostArticleData;

import java.util.List;

/**
 * Created by MOZRE on 2016/6/28.
 */
public interface CommonFragmentView extends BaseView {

    void noMoreNewMessage();

    void noMoreMessage();

    void notifyNewData(List<PostArticleData> mDatas);

    void notifyMoreData(List<PostArticleData> mDatas);
}
