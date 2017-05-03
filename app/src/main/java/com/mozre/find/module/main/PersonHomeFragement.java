package com.mozre.find.module.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.mozre.find.R;
import com.mozre.find.domain.User;
import com.mozre.find.module.part.MineHomeViewPagerAdapter;
import com.mozre.find.profile.MineInterestFragment;
import com.mozre.find.profile.MineMessageFragment;
import com.mozre.find.profile.MinePublishInfoFragment;
import com.mozre.find.util.HttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MOZRE on 2016/6/20.
 */
public class PersonHomeFragement extends Fragment {
    private static final String TAG = "PersonHomeFragement";
    private Context mContext;
    private ViewPager mViewPagerMine;
    private TabLayout mTabLayoutMine;
    private List<Fragment> fragments;
    private List<String> titles;
    private SimpleDraweeView mImageViewUserIcon;
    private TextView mTextViewUsername;
    private TextView mTextViewUserAge;
    private TextView mTextViewUserSex;
    private Dialog mDialog;
//    public interface

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        initDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    private void initDialog() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ListView listView = new ListView(mContext);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT

        );
        listView.setLayoutParams(layoutParams);

//        listView.setFadingEdgeLength(0);
        final List<String> mData = new ArrayList<>();
        mData.add("拍照");
        mData.add("相册");
        mData.add("返回");
        BaseAdapter mAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mData.size();
            }

            @Override
            public Object getItem(int i) {
                return mData.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null) {
                    view = LayoutInflater.from(mContext).inflate(R.layout.dialog_item, null);
                }
                TextView tv = (TextView) view.findViewById(R.id.dialog_item_text_view);
                tv.setText((String) getItem(i));
                return view;
            }
        };
        listView.setAdapter(mAdapter);
        linearLayout.addView(listView);
        mDialog = new AlertDialog.Builder(mContext)
                .setTitle("选择图片")
                .setView(linearLayout)
                .create();
        mDialog.setCanceledOnTouchOutside(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(ChangeUserIconActivity.this, ShowActivity.class);
                switch (i) {
                    case 0:
//                        Intent intent1 = new Intent();
//                        intent1.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(intent1, GET_IMAGE_REQUEST_CODE);
                        break;
                    case 1:
//                        startActivity(new Intent(mContext, UpdateUserIcon.class));
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        mDialog.dismiss();
                        startActivityForResult(intent, 100);
                        break;
                    case 2:
                        mDialog.dismiss();
                        break;
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_home_fragment_view, container, false);
        mImageViewUserIcon = (SimpleDraweeView) view.findViewById(R.id.mine_home_fragment_user_icon);
        mTextViewUsername = (TextView) view.findViewById(R.id.mine_home_fragment_username);
        mTextViewUserAge = (TextView) view.findViewById(R.id.mine_home_fragment_user_age);
        mTextViewUserSex = (TextView) view.findViewById(R.id.mine_home_fragment_user_sex);
        User user = new User();
        if (user.getUserIconAddress() != null) {
            mImageViewUserIcon.setImageURI(HttpUtils.getCurrentURI(user.getUserIconAddress()));
        }
        if (user.getUserName() != null && user.getUserName().length() > 0) {
            mTextViewUsername.setText(user.getUserName());
        }
        mImageViewUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fragments = new ArrayList<>();
        fragments.add(new MinePublishInfoFragment());
        fragments.add(new MineMessageFragment());
        fragments.add(new MineInterestFragment());
        titles = new ArrayList<>();
        titles.add("主页");
        titles.add("消息");
        titles.add("关注");
        mViewPagerMine = (ViewPager) getView().findViewById(R.id.mine_home_fragment_view_pager);
        mTabLayoutMine = (TabLayout) getView().findViewById(R.id.mine_home_fragment_view_tab);
        MineHomeViewPagerAdapter mAdapter = new MineHomeViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        mViewPagerMine.setAdapter(mAdapter);
        Log.d(TAG, "onStart: " + mTabLayoutMine);
        mTabLayoutMine.setupWithViewPager(mViewPagerMine);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onSubscribe(File file) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
        mImageViewUserIcon.setImageURI(Uri.fromFile(file));
//        Log.d(TAG, "onSubscribe: here = " + uri);
//        if (object instanceof Uri) {
//            Uri uri = (Uri) object;
//            Log.d(TAG, "onSubscribe: uri = " + uri);
//            mImageViewUserIcon.setImageURI(uri);
//        }
    }
}
