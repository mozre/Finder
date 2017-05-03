package com.mozre.find.module.main;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mcxiaoke.next.recycler.EndlessRecyclerView;
import com.mcxiaoke.next.utils.ThreadUtils;
import com.mozre.find.R;
import com.mozre.find.app.RecyclerItemClickListener;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.module.middle.ShowAllInfoForRecycleItem;
import com.mozre.find.module.part.HomeRecyclerAdapter;
import com.mozre.find.presenter.PostDataDao;
import com.mozre.find.presenter.PostDetailPresenter;
import com.mozre.find.profile.CommonHomeFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by MOZRE on 2016/6/20.
 */
public class ArticleFragment extends CommonHomeFragment implements RecyclerItemClickListener, SwipeRefreshLayout.OnRefreshListener, CommonFragmentView, EndlessRecyclerView.OnLoadMoreListener {
    private static final String TAG = "ArticleFragment";
    private EndlessRecyclerView mRecycleView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HomeRecyclerAdapter mAdapter;
    private Context mContext;
    private LinkedList<PostArticleData> mData;

    @Override
    public void noMoreNewMessage() {
        Toast.makeText(mContext, "已经是最新了~~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyNewData(List<PostArticleData> mDatas) {
        for (int i = 0; i < mDatas.size(); ++i) {
            mData.addFirst(mDatas.get(i));
        }
        Log.d(TAG, "notifyNewData: mdata = " + mData.size());
        if (mData.size() > 0) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void noMoreMessage() {
        Log.d(TAG, "noMoreMessage: here------------------------");
        mRecycleView.setLoading(false);
//        Snackbar.make(getView(), "已经到最低了！", Snackbar.LENGTH_SHORT).show();
        Toast.makeText(mContext, "已经到最下面了！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyMoreData(List<PostArticleData> mDatas) {
        Log.d(TAG, "notifyMoreData: here");
        for (int i = 0; i < mDatas.size(); ++i) {
            mData.addLast(mDatas.get(i));
        }
        if (mData.size() > 0) {
            mAdapter.notifyDataSetChanged();
            mRecycleView.setLoading(false);
        }
    }

    //    private
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mData = new LinkedList<>();
        this.mContext = context;
        mAdapter = new HomeRecyclerAdapter(mContext, mData);

    }

    @Override
    public void onStart() {
        super.onStart();
//        PostDataDao dao = new PostDataDao(mContext);
//
//        try {
//            long seconds = dao.selectArticleFlags();
//            Log.d(TAG, "onAttach: lo = " + seconds);
//            PostDetailPresenter presenter = new PostDetailPresenter(mContext, this);
//            presenter.postArtcleData(seconds);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mRecycleView = (EndlessRecyclerView) getView().findViewById(R.id.common_home_fragment_end_recycler);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.common_home_fragment_refresh);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mRecycleView.setOnLoadMoreListener(this);
//        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
//        mRecycleView.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onStop() {
        PostDataDao dao = new PostDataDao(mContext);
        try {
            dao.updateArticleFlags(mData.get(mData.size() - 1).getSeconds());
//            Log.d(TAG, "onStop: ------------------------------------------------------");
//            Log.d(TAG, "onStop: " + seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(mContext, "Article Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, ShowAllInfoForRecycleItem.class);
        intent.putExtra("data", mData.get(position));
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(mContext, "刷新！", Toast.LENGTH_SHORT).show();
        PostDetailPresenter presenter = new PostDetailPresenter(mContext, this);
        long seconds = 0;
        if (mData.size() > 0) {
            seconds = mData.get(0).getSeconds();
        }
        presenter.postArtcleData(seconds);
        Log.d(TAG, "onRefresh: seconds = " + seconds);
        Toast.makeText(mContext, "刷新！", Toast.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onLoadMore(EndlessRecyclerView view) {
        Long seconds = null;
        PostDetailPresenter presenter = new PostDetailPresenter(mContext, this);
        if (mData.size() > 0) {
            Log.d(TAG, "onLoadMore: max = " + mData.get(0).getSeconds());
            seconds = mData.get(mData.size() - 1).getSeconds();
            Log.d(TAG, "onLoadMore: seconds = " + seconds);
            try {
                presenter.loadDetailData(seconds);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            mRecycleView.setLoading(false);
        }
        if (seconds != null && seconds > 0) {

        }

    }

    @Override
    public void init(View mView) {
        PostDataDao dao = new PostDataDao(mContext);

        try {
            long seconds = dao.selectArticleFlags();
            Log.d(TAG, "onAttach: lo = " + seconds);
            PostDetailPresenter presenter = new PostDetailPresenter(mContext, this);
            presenter.postArtcleData(seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecycleView = (EndlessRecyclerView) getView().findViewById(R.id.common_home_fragment_end_recycler);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.common_home_fragment_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecycleView.setOnLoadMoreListener(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }
}
