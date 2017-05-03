package com.mozre.find.profile;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mcxiaoke.next.recycler.EndlessRecyclerView;
import com.mozre.find.R;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.module.part.HomeRecyclerAdapter;
import com.mozre.find.presenter.MinePublishPresenter;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MinePublishInfoFragment extends Fragment implements MinePublishInfoView, SwipeRefreshLayout.OnRefreshListener, EndlessRecyclerView.OnLoadMoreListener {

    private static final String TAG = "MinePublishInfoFragment";
    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EndlessRecyclerView mEndlessRecyclerView;
    private LinkedList<PostArticleData> mDatas;
    private HomeRecyclerAdapter mAdapter;
    private MinePublishPresenter mPresernter;
    private long seconds;

    public MinePublishInfoFragment() {
        // Required empty public constructor
        mDatas = new LinkedList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mAdapter = new HomeRecyclerAdapter(mContext, mDatas);
        mPresernter = new MinePublishPresenter(mContext, this);
        try {
            List<PostArticleData> Datas = mPresernter.refreshNewData();
            Log.d(TAG, "onAttach: " + mDatas);
            if (Datas != null && Datas.size() > 0) {
                for (int i = 0; i < Datas.size(); ++i) {
                    this.mDatas.addFirst(Datas.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.mine_publish_info_fragment, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.mine_publish_info_swipe_refresh);
        mEndlessRecyclerView = (EndlessRecyclerView) view.findViewById(R.id.mine_publish_info_end_recycler);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mEndlessRecyclerView.setOnLoadMoreListener(this);
        try {
            mPresernter.postRefreshNewData(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onStart() {
        mEndlessRecyclerView.setAdapter(mAdapter);
        super.onStart();
    }

    @Override
    public void isRecentData() {
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(mContext, "已经是最新了~~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyRecentData(List<PostArticleData> mDatas) {
        this.mDatas.clear();
        for (int i = 0; i < mDatas.size(); ++i) {
            this.mDatas.addLast(mDatas.get(i));
        }
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void isLatestData() {
        mEndlessRecyclerView.setLoading(false);
        Toast.makeText(mContext, "没有更多了~~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyLatestData(List<PostArticleData> mDatas) {
        for (int i = 0; i < mDatas.size(); ++i) {
            this.mDatas.addLast(mDatas.get(i));
        }
        mAdapter.notifyDataSetChanged();
        mEndlessRecyclerView.setLoading(false);
    }

    @Override
    public void onRefresh() {
        mEndlessRecyclerView.setLoading(false);
        if (mDatas.size() > 0) {
            seconds = mDatas.get(0).getSeconds();
            try {
                mPresernter.postRefreshNewData(seconds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoadMore(EndlessRecyclerView view) {
        if (this.mDatas == null || this.mDatas.size() < 1) {
            mEndlessRecyclerView.setLoading(false);
        } else {
            seconds = 0;
            seconds = this.mDatas.get(this.mDatas.size() - 1).getSeconds();
            mPresernter.postLoadMoreData(seconds);
            Log.d(TAG, "onLoadMore: here");
        }
    }

}
