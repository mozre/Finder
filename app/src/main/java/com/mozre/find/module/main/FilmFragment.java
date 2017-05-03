package com.mozre.find.module.main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.mcxiaoke.next.recycler.EndlessRecyclerView;
import com.mozre.find.R;
import com.mozre.find.app.RecyclerItemClickListener;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.module.middle.ShowAllInfoForRecycleItem;
import com.mozre.find.module.part.FilmTestQuickAdapter;
import com.mozre.find.module.part.HomeRecyclerAdapter;
import com.mozre.find.module.part.HotChangeLooperViewPagerAdapter;
import com.mozre.find.profile.ChangeLooperFragment;
import com.mozre.find.profile.CommonHomeFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by MOZRE on 2016/6/20.
 */
public class FilmFragment extends CommonHomeFragment implements RecyclerItemClickListener, EndlessRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FilmFragment";
    private EndlessRecyclerView mRecycleView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FilmTestQuickAdapter mAdapter;
    private Context mContext;
    private List<PostArticleData> mData;
    private PostArticleData data;
    private FrameLayout mFrameLayout;
    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private List<Fragment> mChangeLooperFragments;
    private HotChangeLooperViewPagerAdapter mViewPagerAdapter;
    private View mHeadView;


//    public static CommonHomeFragment newInstance(){
//        Bundle bundle=new Bundle();
//        FilmFragment fragment=new FilmFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        data = new PostArticleData();
        data.setSeconds(1467081703685L);
        data.setUsername("mozre");
        data.setPostTime("2016/06/28 10:4");
        data.setImageUri("E:\\usericon\\mozre.png");
        data.setTitle("文艺书店");
        data.setDescription("文艺书店");
        data.setDetail("碌中，你是否曾有过这样的憧憬——在街角有一间小小的书店，有着纯净的书香，人们捧着心爱书籍阅读，安静而专注。读书，其实是与心灵的对话，推荐以下成都文艺宅书店");
        data.setImage("E:\\images\\mozre1467954872743.png");
        data.setForward(3);
        data.setReview(8);
        data.setFlower(32);
//        mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.hot_recycle_head_view, null);
    }

    @Override
    public void onStart() {
        super.onStart();
//        mRecycleView = (EndlessRecyclerView) getView().findViewById(R.id.common_home_fragment_end_recycler);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.common_home_fragment_refresh);
//        mFrameLayout = (FrameLayout) getView().findViewById(R.id.test_head);
//        Log.d(TAG, "onStart: " + mFrameLayout);
//        mRecycleView.setOnLoadMoreListener(this);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mData = new LinkedList<>();
//
//        for (int i = 0; i < 10; ++i) {
//            mData.add(data);
//        }
//        mAdapter = new FilmTestQuickAdapter(mContext, this.mData);
//        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
//        mRecycleView.setAdapter(mAdapter);
//        mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.hot_recycle_head_view, null);
//        mAdapter.addHeaderView(mHeadView);
//
//        mCircleIndicator = (CircleIndicator) mHeadView.findViewById(R.id.film_home_view_indicator);
//        mViewPager = (ViewPager) mHeadView.findViewById(R.id.film_home_view_pager);
//
//        mChangeLooperFragments = new ArrayList<>();
//        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(1));
//        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(2));
//        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(3));
//        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(4));
//        mViewPagerAdapter = new HotChangeLooperViewPagerAdapter(getChildFragmentManager(), mChangeLooperFragments);
//        mViewPager.setAdapter(mViewPagerAdapter);
//        mCircleIndicator.setViewPager(mViewPager);
//        mAdapter.openLoadAnimation();
//        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        mAdapter.openLoadAnimation(new BaseAnimation() {
//            @Override
//            public Animator[] getAnimators(View view) {
//                return new Animator[]{
//                        ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
//                        ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
//                };
//            }
//        });
//        mAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                switch (view.getId()) {
//                    case R.id.common_home_list_image:
//                        Toast.makeText(mContext, "Clicked Image", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.common_home_list_content:
//                        Toast.makeText(mContext, "Clicked Content", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        });
//
//
////        mAdapter.setOnRecyclerViewItemClickListener(this);
////        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
////            @Override
////            public void onItemClick(View view, int i) {
////                Toast.makeText(mContext, "Click" + view.getId(), Toast.LENGTH_SHORT).show();
////                switch (view.getId()) {
////                    case R.id.common_home_list_image:
////                        Toast.makeText(mContext, "Clicked Image", Toast.LENGTH_SHORT).show();
////                        break;
////                    case R.id.common_home_list_icon:
////                        Toast.makeText(mContext, "Clicked Icon", Toast.LENGTH_SHORT).show();
////                        break;
////                    case R.id.common_home_list_content:
////                        Toast.makeText(mContext, "Clicked Content", Toast.LENGTH_SHORT).show();
////                        break;
////                }
////            }
////        });
////        mAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
////            @Override
////            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
////                Toast.makeText(mContext, "ID = " + view.getId(), Toast.LENGTH_SHORT).show();
////            }
////        });
    }


    @Override
    public void onItemClick(View view, int Position) {
        Toast.makeText(mContext, "Film Clicked", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(mContext, ShowAllInfoForRecycleItem.class));
    }

    @Override
    public void onLoadMore(EndlessRecyclerView view) {
        mRecycleView.setLoading(false);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void init(View mView) {
        mRecycleView = (EndlessRecyclerView) getView().findViewById(R.id.common_home_fragment_end_recycler);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.common_home_fragment_refresh);
        mFrameLayout = (FrameLayout) getView().findViewById(R.id.test_head);
        Log.d(TAG, "onStart: " + mFrameLayout);
        mRecycleView.setOnLoadMoreListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mData = new LinkedList<>();

        for (int i = 0; i < 10; ++i) {
            mData.add(data);
        }
        mAdapter = new FilmTestQuickAdapter(mContext, this.mData);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleView.setAdapter(mAdapter);
//        mHeadView =  LayoutInflater.from(getContext()).inflate(R.layout.hot_recycle_head_view, null,true);
        mHeadView = getLayoutInflater(getArguments()).inflate(R.layout.hot_recycle_head_view, null);
        mAdapter.addHeaderView(mHeadView);

        mCircleIndicator = (CircleIndicator) mHeadView.findViewById(R.id.film_home_view_indicator);
        mViewPager = (ViewPager) mHeadView.findViewById(R.id.film_home_view_pager);

        mChangeLooperFragments = new ArrayList<>();
        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(1));
        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(2));
        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(3));
        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(4));
        mViewPagerAdapter = new HotChangeLooperViewPagerAdapter(getChildFragmentManager(), mChangeLooperFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        mCircleIndicator.setViewPager(mViewPager);
        mAdapter.openLoadAnimation();
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                        ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
                };
            }
        });
        mAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()) {
                    case R.id.common_home_list_image:
                        Toast.makeText(mContext, "Clicked Image", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.common_home_list_content:
                        Toast.makeText(mContext, "Clicked Content", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


//        mAdapter.setOnRecyclerViewItemClickListener(this);
//        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, int i) {
//                Toast.makeText(mContext, "Click" + view.getId(), Toast.LENGTH_SHORT).show();
//                switch (view.getId()) {
//                    case R.id.common_home_list_image:
//                        Toast.makeText(mContext, "Clicked Image", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.common_home_list_icon:
//                        Toast.makeText(mContext, "Clicked Icon", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.common_home_list_content:
//                        Toast.makeText(mContext, "Clicked Content", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        });
//        mAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                Toast.makeText(mContext, "ID = " + view.getId(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
