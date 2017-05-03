package com.mozre.find.module.part;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.WrapperListAdapter;

import com.mozre.find.R;
import com.mozre.find.app.RecyclerItemClickListener;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.util.HttpUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MOZRE on 2016/6/20.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerViewHolder>  {

    private static final String TAG = "HomeRecyclerAdapter";
    private List<PostArticleData> mDatas;
    private Context mContext;
    private RecyclerItemClickListener mItemClickListener;

    public HomeRecyclerAdapter(Context mContext, List<PostArticleData> mData) {
        this.mContext = mContext;
        this.mDatas = mData;

    }

    @Override
    public HomeRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_home_list_item, parent, false);
        return new HomeRecyclerViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(HomeRecyclerViewHolder holder, int position) {


//        Log.d(TAG, "onBindViewHolder: " + mDatas.size());
        if (mDatas.get(position).getImageUri() != null && mDatas.get(position).getImageUri().length() > 0) {
            holder.mSimpleDraweeViewIcon.setImageURI(HttpUtils.getCurrentURI(mDatas.get(position).getImageUri()));
        } else {
            holder.mSimpleDraweeViewIcon.setImageURI("");
        }
//
        holder.mTextViewUsername.setText(mDatas.get(position).getUsername());
        holder.mTextViewTime.setText(mDatas.get(position).getPostTime());
        if (mDatas.get(position).getImage() != null && mDatas.get(position).getImage().length() > 0) {
            holder.mSimpleDraweeViewImage.setVisibility(View.VISIBLE);
            holder.mSimpleDraweeViewImage.setImageURI(HttpUtils.getCurrentURI(mDatas.get(position).getImage()));
        } else {
            holder.mSimpleDraweeViewImage.setVisibility(View.GONE);
        }
//        Log.d(TAG, "onBindViewHolder: image  =" + mDatas.get(position).getImage());
//        Log.d(TAG, "onBindViewHolder: paht = " + mDatas.get(position).getImageUri());

//
        holder.mTextViewContent.setText(mDatas.get(position).getDescription());
        holder.mTextViewForward.setText("转发：" + mDatas.get(position).getForward());
        holder.mTextViewReview.setText("评论：" + mDatas.get(position).getReview());
        holder.mTextViewFlower.setText("赞：" + mDatas.get(position).getFlower());

    }


    @Override
    public int getItemCount() {
//        Log.d(TAG, "getItemCount: mdatas = " + mDatas);
        return mDatas.size();
    }


    public void setOnItemClickListener(RecyclerItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}
