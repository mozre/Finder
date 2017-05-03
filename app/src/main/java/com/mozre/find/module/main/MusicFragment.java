package com.mozre.find.module.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mcxiaoke.next.recycler.EndlessRecyclerView;
import com.mozre.find.R;
import com.mozre.find.app.RecyclerItemClickListener;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.module.middle.ShowAllInfoForRecycleItem;
import com.mozre.find.module.part.HomeRecyclerAdapter;
import com.mozre.find.profile.CommonHomeFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MOZRE on 2016/6/20.
 */
public class MusicFragment extends Fragment {

    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_view, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}
