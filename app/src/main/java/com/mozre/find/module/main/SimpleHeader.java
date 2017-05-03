package com.mozre.find.module.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.mozre.find.R;
import com.mozre.find.module.part.HotChangeLooperViewPagerAdapter;
import com.mozre.find.profile.ChangeLooperFragment;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by MOZRE on 2016/7/13.
 */
public class SimpleHeader extends FrameLayout {
    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private List<Fragment> mChangeLooperFragments;
    private HotChangeLooperViewPagerAdapter mViewPagerAdapter;
    private FragmentManager manager;

    private void init() {

        mCircleIndicator = (CircleIndicator) findViewById(R.id.film_home_view_indicator);
        mViewPager = (ViewPager) findViewById(R.id.film_home_view_pager);
        mChangeLooperFragments = new ArrayList<>();
        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(1));
        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(2));
        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(3));
        mChangeLooperFragments.add(ChangeLooperFragment.newInstance(4));
        mViewPagerAdapter = new HotChangeLooperViewPagerAdapter(this.manager, mChangeLooperFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        mCircleIndicator.setViewPager(mViewPager);
    }


    public SimpleHeader(Context context, FragmentManager manager) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.hot_recycle_head_view, this, true);
        this.manager = manager;
        init();
    }

    public SimpleHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.hot_recycle_head_view, this, true);
        init();


    }

    public SimpleHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.hot_recycle_head_view, this, true);
        init();
    }
}
