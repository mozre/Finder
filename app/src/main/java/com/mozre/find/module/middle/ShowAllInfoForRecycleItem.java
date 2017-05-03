package com.mozre.find.module.middle;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mozre.find.R;
import com.mozre.find.domain.PostArticleData;
import com.mozre.find.module.main.MainActivity;
import com.mozre.find.util.HttpUtils;

public class ShowAllInfoForRecycleItem extends com.mozre.find.app.SwipeBackActivity implements View.OnClickListener {

    private static final String TAG = ShowAllInfoForRecycleItem.class.getSimpleName();
    private RelativeLayout mRelativeLayoutForward;
    private RelativeLayout mRelativeLayoutReview;
    private RelativeLayout mRelativeLayoutFlower;
    private SimpleDraweeView mSimpleDraweeViewIcon;
    private TextView mTextViewUsername;
    private TextView mTextViewPostTime;
    private TextView mTextViewTitle;
    private SimpleDraweeView mSimpleDraweeViewImage;
    private TextView mTextViewDetail;
    private PostArticleData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_info_for_recycle_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("详情");
        mRelativeLayoutForward = (RelativeLayout) findViewById(R.id.show_all_info_forward);
        mRelativeLayoutReview = (RelativeLayout) findViewById(R.id.show_all_info_review);
        mRelativeLayoutFlower = (RelativeLayout) findViewById(R.id.show_all_info_flower);
        mSimpleDraweeViewIcon = (SimpleDraweeView) findViewById(R.id.show_all_info_icon);
        mTextViewUsername = (TextView) findViewById(R.id.show_all_info_username);
        mTextViewTitle = (TextView) findViewById(R.id.show_all_info_title);
        mTextViewPostTime = (TextView) findViewById(R.id.show_all_info_time);
        mSimpleDraweeViewImage = (SimpleDraweeView) findViewById(R.id.show_all_info_image);
        mTextViewDetail = (TextView) findViewById(R.id.show_all_info_detail);
        putData(getIntent());
        mRelativeLayoutForward.setOnClickListener(this);
        mRelativeLayoutReview.setOnClickListener(this);
        mRelativeLayoutFlower.setOnClickListener(this);
    }

    private void putData(Intent data) {
        mData = (PostArticleData) data.getSerializableExtra("data");
        Log.d(TAG, "putData: data = " + mData.toString() + " ");
        if (data == null) {
            return;
        }
        if (mData.getImageUri() != null && mData.getImageUri().length() > 0) {
            mSimpleDraweeViewIcon.setImageURI(HttpUtils.getCurrentURI(mData.getImageUri()));
        }
        if (mData.getUsername() != null) {
            mTextViewUsername.setText(mData.getUsername());
        }
        if (mData.getPostTime() != null) {
            mTextViewPostTime.setText(mData.getPostTime());
        }
        if (!TextUtils.isEmpty(mData.getTitle())) {
            Log.d(TAG, "putData: " + mData.getTitle());
            mTextViewTitle.setText(mData.getTitle());
        }
        if (mData.getImage() != null && mData.getImage().length() > 0) {
            mSimpleDraweeViewImage.setVisibility(View.VISIBLE);
            mSimpleDraweeViewImage.setImageURI(HttpUtils.getCurrentURI(mData.getImage()));
        } else {
            mSimpleDraweeViewImage.setVisibility(View.GONE);
        }
        if (mData.getDetail() != null) {
            mTextViewDetail.setText(mData.getDetail());
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_all_info_forward:
                Toast.makeText(ShowAllInfoForRecycleItem.this, "Clicked", Toast.LENGTH_SHORT).show();
                Intent forwardIntent = new Intent(getBaseContext(), ForwardMessage.class);
                forwardIntent.putExtra("data", mData);
                ShowAllInfoForRecycleItem.this.startActivity(forwardIntent);
                break;
            case R.id.show_all_info_review:
                Intent reviewIntent = new Intent(getBaseContext(), ReviewMessage.class);
                reviewIntent.putExtra("data", mData);
                Toast.makeText(ShowAllInfoForRecycleItem.this, "Clicked", Toast.LENGTH_SHORT).show();
                ShowAllInfoForRecycleItem.this.startActivity(reviewIntent);
                break;
            case R.id.show_all_info_flower:
                Toast.makeText(ShowAllInfoForRecycleItem.this, "Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
