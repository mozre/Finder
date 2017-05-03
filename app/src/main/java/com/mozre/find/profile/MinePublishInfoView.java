package com.mozre.find.profile;

import com.mozre.find.app.BaseView;
import com.mozre.find.domain.PostArticleData;

import java.util.List;

/**
 * Created by MOZRE on 2016/7/6.
 */
public interface MinePublishInfoView extends BaseView {
    void isRecentData();
    void notifyRecentData(List<PostArticleData> mDatas);
    void isLatestData();
    void notifyLatestData(List<PostArticleData> mDatas);
}
