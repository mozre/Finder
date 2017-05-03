package com.mozre.find.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mozre.find.R;
import com.mozre.find.module.main.FilmFragment;

/**
 * Created by MOZRE on 2016/6/20.
 */
public abstract class CommonHomeFragment extends Fragment {

    private View mView;


//    public CommonHomeFragment() {
//        super();
//        Bundle bundle=new Bundle();
//        this.setArguments(bundle);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.common_home_fragment_view, container, false);
        init(mView);


        return mView;
    }

    @Nullable
    @Override
    public View getView() {
        return mView;
    }

    public abstract void init(View mView);

}
