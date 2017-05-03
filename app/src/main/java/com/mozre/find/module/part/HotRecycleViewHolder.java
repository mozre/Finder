package com.mozre.find.module.part;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mozre.find.R;
import com.mozre.find.app.RecyclerItemClickListener;

/**
 * Created by MOZRE on 2016/6/22.
 */
public class HotRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    RecyclerItemClickListener mClickListener;
    SimpleDraweeView mSimpleDraweeViewIcon;
    TextView mTextViewUsername;
    TextView mTextViewTime;
    SimpleDraweeView mSimpleDraweeViewImage;
    TextView mTextViewContent;


    public HotRecycleViewHolder(View itemView, RecyclerItemClickListener listener) {
        super(itemView);
        mSimpleDraweeViewIcon = (SimpleDraweeView) itemView.findViewById(R.id.hot_recycle_item_icon);
        mTextViewUsername = (TextView) itemView.findViewById(R.id.hot_recycle_item_username);
        mTextViewTime = (TextView) itemView.findViewById(R.id.hot_recycle_item_time);
        mSimpleDraweeViewImage = (SimpleDraweeView) itemView.findViewById(R.id.hot_recycle_item_image);
        mTextViewContent = (TextView) itemView.findViewById(R.id.hot_recycle_item_content);
        this.mClickListener = listener;
        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        mClickListener.onItemClick(view, getPosition());
    }
}
