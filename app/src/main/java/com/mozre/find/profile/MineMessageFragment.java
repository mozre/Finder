package com.mozre.find.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mozre.find.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineMessageFragment extends Fragment {


    public MineMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mine_message_fragment, container, false);
    }

}
