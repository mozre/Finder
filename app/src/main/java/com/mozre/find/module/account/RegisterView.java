package com.mozre.find.module.account;

import com.mozre.find.app.BaseView;

/**
 * Created by MOZRE on 2016/6/17.
 */
public interface RegisterView extends BaseView {
    void changeViewMode(Boolean check);

    void toLoginView(Boolean result);
}
