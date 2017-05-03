package com.mozre.find.module.part;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mozre.find.R;
import com.mozre.find.app.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MOZRE on 2016/6/22.
 */
public class HotRecycleAdapter extends RecyclerView.Adapter<HotRecycleViewHolder> {

    private static final String TAG = "HotRecycleAdapter";
    private List<String> mIconUri;
    private List<String> mUsername;
    private List<String> mTime;
    private List<String> mImageUri;
    private List<String> mContent;

    private RecyclerItemClickListener itemClickListener;
    private Context mContext;

    public HotRecycleAdapter(Context mContext) {
        this.mContext = mContext;
        mIconUri = new ArrayList<>();
        mUsername = new ArrayList<>();
        mTime = new ArrayList<>();
        mImageUri = new ArrayList<>();
        mContent = new ArrayList<>();
    }

    @Override
    public HotRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: here");
        View view = LayoutInflater.from(mContext).inflate(R.layout.hot_fragment_recycle_item, parent, false);
        return new HotRecycleViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(HotRecycleViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: p" + position);
        holder.mSimpleDraweeViewIcon.setImageURI(Uri.parse(mIconUri.get(position)));
        holder.mTextViewUsername.setText(mUsername.get(position));
        holder.mTextViewTime.setText(mTime.get(position));
        holder.mSimpleDraweeViewImage.setImageURI(Uri.parse(mImageUri.get(position)));
        holder.mTextViewContent.setText(mContent.get(position));
    }

    @Override
    public int getItemCount() {
//        return mContent.size();
        return mContent.size();
    }


    public void setData(List<String> mIconUri, List<String> mContent, List<String> mImageUri, List<String> mTime, List<String> mUsername) {
        this.mIconUri = mIconUri;
        this.mContent = mContent;
        this.mImageUri = mImageUri;
        this.mTime = mTime;
        this.mUsername = mUsername;
    }

    public void setOnItemClickListener(RecyclerItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
