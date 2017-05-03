package com.mozre.find.module.main;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.mcxiaoke.next.recycler.EndlessRecyclerView;
import com.mcxiaoke.next.recycler.HeaderFooterRecyclerAdapter;
import com.mozre.find.R;
import com.mozre.find.app.RecyclerItemClickListener;
import com.mozre.find.module.part.HotRecycleAdapter;
import com.mozre.find.presenter.PostHotPresenter;
import com.mozre.find.profile.ChangeLooperFragment;
import com.mozre.find.module.part.HotChangeLooperViewPagerAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment implements HotView, RecyclerItemClickListener, EndlessRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HotFragment";
    private ViewPager mViewPagerLooperImage;
    private CircleIndicator mCircleIndicator;
    private RecyclerViewHeader mRecyclerViewHeader;
    private List<Fragment> mChangeLooperFragments;
    private SwipeRefreshLayout mSwipeReFreshHot;
    private EndlessRecyclerView mEndlessRecyclerViewHot;
    private HotRecycleAdapter mRecycleAdapter;
    private List<String> mIconUri;
    private List<String> mUsername;
    private List<String> mTime;
    private List<String> mImageUri;
    private List<String> mContent;
    private Context mContext;
    private final static int HEAD_VIEW = R.layout.hot_recycle_head_view;
    private final static int BODY_VIEW = R.layout.hot_fragment_recycle_item;

    public HotFragment() {

        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hot_home_fragment_view, container, false);
        mViewPagerLooperImage = (ViewPager) view.findViewById(R.id.hot_home_view_pager);
        mCircleIndicator = (CircleIndicator) view.findViewById(R.id.hot_home_view_indicator);
        mRecyclerViewHeader = (RecyclerViewHeader) view.findViewById(R.id.hot_home_view_header);
        mSwipeReFreshHot = (SwipeRefreshLayout) view.findViewById(R.id.hot_home_view_swipe);
        mEndlessRecyclerViewHot = (EndlessRecyclerView) view.findViewById(R.id.hot_home_view_endless);
        mChangeLooperFragments = new ArrayList<>();
        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(1));
        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(2));
        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(3));
        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(4));
        HotChangeLooperViewPagerAdapter mAdapter = new HotChangeLooperViewPagerAdapter(getChildFragmentManager(), mChangeLooperFragments);
        mViewPagerLooperImage.setAdapter(mAdapter);
        mCircleIndicator.setViewPager(mViewPagerLooperImage);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 2000);
        return view;
    }

    @Override
    public void onStart() {
        mRecycleAdapter = new HotRecycleAdapter(getContext());
        mRecyclerViewHeader.attachTo(mEndlessRecyclerViewHot);
        mEndlessRecyclerViewHot.setAdapter(mRecycleAdapter);
        mRecycleAdapter.setOnItemClickListener(this);
        mEndlessRecyclerViewHot.setOnLoadMoreListener(this);
        mSwipeReFreshHot.setOnRefreshListener(this);
        this.test();
        mRecycleAdapter.setData(mIconUri, mContent, mImageUri, mTime, mUsername);
        mRecycleAdapter.notifyDataSetChanged();
        Log.d(TAG, "onStart: here");
        Timer timer = new Timer(true);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {


                mViewPagerLooperImage.post(new Runnable() {
                    @Override
                    public void run() {
                        int i = mViewPagerLooperImage.getCurrentItem();
                        if (i == 0) {
                            mViewPagerLooperImage.setCurrentItem(1);
                        } else if (i == 1) {
                            mViewPagerLooperImage.setCurrentItem(2);
                        } else if (i == 2) {
                            mViewPagerLooperImage.setCurrentItem(3);
                        } else if (i == 3) {
                            mViewPagerLooperImage.setCurrentItem(0);
                        }
                    }
                });


            }
        };
        timer.schedule(timerTask, 5000, 5000);
        super.onStart();
    }

    @Override
    public void onItemClick(View view, int Position) {
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
    }

    public void test() {

        this.mContent = new ArrayList<>();
        this.mImageUri = new ArrayList<>();
        this.mTime = new ArrayList<>();
        this.mUsername = new ArrayList<>();
        this.mIconUri = new ArrayList<>();
        mContent.add("这也是一次测试");
        mImageUri.add("");
        mIconUri.add("");
        mTime.add(String.valueOf(new Date()));
        mUsername.add("mozre");

    }

    @Override
    public void onLoadMore(EndlessRecyclerView view) {
        Toast.makeText(mContext, "loading.......", Toast.LENGTH_SHORT).show();
        mEndlessRecyclerViewHot.setEnabled(true);
        PostHotPresenter presenter = new PostHotPresenter(mContext, this);
        mEndlessRecyclerViewHot.setEnabled(true);
        presenter.postRefreshDataFromServer();
        mEndlessRecyclerViewHot.setLoading(false);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(mContext, "refresh......", Toast.LENGTH_SHORT).show();
        mSwipeReFreshHot.setRefreshing(false);
    }
}
