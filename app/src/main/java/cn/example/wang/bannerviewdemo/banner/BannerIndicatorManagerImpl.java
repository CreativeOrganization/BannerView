package cn.example.wang.bannerviewdemo.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.example.wang.bannermodule.base.IBaseIndicator;

/**
 * Created by WANG on 2018/5/25.
 * 自定义指示器 BannerView不再去管理你的指示器
 */

public class BannerIndicatorManagerImpl implements IBaseIndicator {
    private LayoutInflater inflater;
    private Drawable mSelectedDrawable;
    private Drawable mDefaultDrawable;
    private ViewGroup mIndicatorContainer;

    private void initRec(Context context, ViewGroup parent) {
        mDefaultDrawable = context.getResources().getDrawable(cn.example.wang.bannermodule.R.drawable.bg_circle);
        mSelectedDrawable = context.getResources().getDrawable(cn.example.wang.bannermodule.R.drawable.bg_circle_red);
    }

    @Override
    public void attachBannerView(Context context, ViewGroup parent) {
        mIndicatorContainer = parent;
        inflater = LayoutInflater.from(context.getApplicationContext());
        initRec(context, parent);
    }

    @Override
    public void viewCount(int viewCount) {
        Log.e("WANG","BannerIndicatorManagerImpl.ViewCount."+viewCount );
        for (int i = 0; i < viewCount; i++) {
            View view = defaultIndicatorLayout(mIndicatorContainer);
            mIndicatorContainer.addView(view);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void preSelectedPage(int preIndex) {
        Log.e("WANG","BannerIndicatorManagerImpl.preSelectedPage."+preIndex );
        mIndicatorContainer.getChildAt(preIndex).setBackground(mDefaultDrawable);
    }

    @SuppressLint("NewApi")
    @Override
    public void currentSelectedPage(int currentIndex) {
        Log.e("WANG","BannerIndicatorManagerImpl.currentSelectedPage."+currentIndex );
        mIndicatorContainer.getChildAt(currentIndex).setBackground(mSelectedDrawable);
    }

    public View defaultIndicatorLayout(ViewGroup parent) {
        return inflater.inflate(cn.example.wang.bannermodule.R.layout.layout_indicator_default, parent, false);
    }

}
