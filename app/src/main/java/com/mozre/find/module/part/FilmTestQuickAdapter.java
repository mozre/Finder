package com.mozre.find.module.part;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mozre.find.R;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.util.HttpUtils;

import java.util.List;

/**
 * Created by MOZRE on 2016/7/10.
 */
public class FilmTestQuickAdapter extends BaseQuickAdapter<PostArticleData> {

    private static final String TAG = "FilmTestQuickAdapter";
    private Context mContext;
    private int count;
    private BaseViewHolder holder;

    public FilmTestQuickAdapter(Context context, List<PostArticleData> data) {
        super(R.layout.common_home_list_item, data);
        this.mContext = context;
    }


//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            Log.d(TAG, "getItemViewType: ----------- ");
//            return R.layout.hot_recycle_head_view;
//
//        }
//        return super.getItemViewType(position);
//
//        //自己写头结点
//
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
//        if (position == 0) {
//            return;
//        }
//        this.count = getItemCount();
//        if (position - 1 < this.count) {
//            super.onBindViewHolder(holder, position - 1, payloads);
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
////        int i =
//        return super.getItemCount() + 1;
//    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PostArticleData data) {
        baseViewHolder.setText(R.id.common_home_list_username, data.getUsername())
                .setText(R.id.common_home_list_time, data.getPostTime())
                .setText(R.id.common_home_list_content, data.getDescription())
                .setText(R.id.common_home_list_forward, String.valueOf("转发：" + data.getForward()))
                .setText(R.id.common_home_list_review, String.valueOf("评论：" + data.getReview()))
                .setText(R.id.common_home_list_flower, String.valueOf("点赞：" + data.getFlower()));
        SimpleDraweeView mIcon = ((SimpleDraweeView) baseViewHolder.getConvertView().findViewById(R.id.common_home_list_icon));
        SimpleDraweeView mImage = ((SimpleDraweeView) baseViewHolder.getConvertView().findViewById(R.id.common_home_list_image));
        mIcon.setImageURI(HttpUtils.getCurrentURI(data.getImageUri()));
        mImage.setImageURI(HttpUtils.getCurrentURI(data.getImage()));
        baseViewHolder.setOnClickListener(R.id.common_home_list_image, new OnItemChildClickListener())
                .setOnClickListener(R.id.common_home_list_content, new OnItemChildClickListener());
    }
//----------------------------

    //    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;


//    public interface OnRecyclerViewItemClickListener {
//        public void onItemClick(View view, int position);
//    }
//
//    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
//        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
//    }
//
//    @Override
//    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (onRecyclerViewItemClickListener != null) {
//            Log.d(TAG, "onCreateViewHolder: " + this.holder);
//            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onRecyclerViewItemClickListener.onItemClick(view, holder.getLayoutPosition());
//                }
//            });
//        }
//        return super.onCreateViewHolder(parent, viewType);
//    }
}
