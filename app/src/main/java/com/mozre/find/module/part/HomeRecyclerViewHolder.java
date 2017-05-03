package com.mozre.find.module.part;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mozre.find.R;
import com.mozre.find.app.RecyclerItemClickListener;

/**
 * Created by MOZRE on 2016/6/20.
 */
public class HomeRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "HomeRecyclerViewHolder";
    SimpleDraweeView mSimpleDraweeViewIcon;
    TextView mTextViewUsername;
    TextView mTextViewTime;
    TextView mTextViewContent;
    SimpleDraweeView mSimpleDraweeViewImage;
    TextView mTextViewForward;
    TextView mTextViewReview;
    TextView mTextViewFlower;
    RecyclerItemClickListener mItemClickListener;

    public HomeRecyclerViewHolder(View itemView, RecyclerItemClickListener listener) {
        super(itemView);
        mSimpleDraweeViewIcon = (SimpleDraweeView) itemView.findViewById(R.id.common_home_list_icon);
        mTextViewUsername = (TextView) itemView.findViewById(R.id.common_home_list_username);
        mTextViewTime = (TextView) itemView.findViewById(R.id.common_home_list_time);
        mTextViewContent = (TextView) itemView.findViewById(R.id.common_home_list_content);
        mSimpleDraweeViewImage = (SimpleDraweeView) itemView.findViewById(R.id.common_home_list_image);
        mTextViewForward = (TextView) itemView.findViewById(R.id.common_home_list_forward);
        mTextViewReview = (TextView) itemView.findViewById(R.id.common_home_list_review);
        mTextViewFlower = (TextView) itemView.findViewById(R.id.common_home_list_flower);

        mItemClickListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mItemClickListener.onItemClick(view, getPosition());
    }
}
