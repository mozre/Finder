package com.mozre.find.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mozre.find.R;
import com.mozre.find.util.HttpUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeLooperFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeLooperFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters

    private String mParam2;
    private int color;


    public ChangeLooperFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChangeLooperFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeLooperFragment newInstance(int color) {
        ChangeLooperFragment fragment = new ChangeLooperFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, color);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            color = getArguments().getInt(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_looper, container, false);
//        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.change_looper_frame);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.change_looper_drawee);
        Log.d("mozre", "onCreateView: "+(HttpUtils.getCurrentURI("E:\\loopimage\\" + color + ".png")));
        simpleDraweeView.setImageURI(HttpUtils.getCurrentURI("E:\\oopimage\\" + color + ".png"));
//        imageView.setImageBitmap();
//        frameLayout.setBackgroundColor(color);
        return view;
    }

}
