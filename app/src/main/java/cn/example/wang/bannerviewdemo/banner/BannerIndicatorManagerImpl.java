package cn.example.wang.bannerviewdemo.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.example.wang.bannermodule.base.IBaseIndicator;
import cn.example.wang.bannerviewdemo.R;

/**
 *
 *
 * @author WANG
 * @date 2018/5/25
 */

public class BannerIndicatorManagerImpl implements IBaseIndicator {
    private LayoutInflater inflater;
    private Drawable mSelectedDrawable;
    private Drawable mDefaultDrawable;
    private ViewGroup mIndicatorContainer;

    private void initRec(Context context) {
        mDefaultDrawable = context.getResources().getDrawable(R.drawable.bg_circle);
        mSelectedDrawable = context.getResources().getDrawable(R.drawable.bg_circle_red);
    }

    @Override
    public void attachBannerView(ViewGroup parent) {
        mIndicatorContainer = parent;
        inflater = LayoutInflater.from(parent.getContext().getApplicationContext());
        initRec(parent.getContext());
    }

    @Override
    public void viewCount(int viewCount) {
        for (int i = 0; i < viewCount; i++) {
            View view = defaultIndicatorLayout(mIndicatorContainer);
            mIndicatorContainer.addView(view);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void preSelectedPage(int preIndex) {
        mIndicatorContainer.getChildAt(preIndex).setBackground(mDefaultDrawable);
    }

    @SuppressLint("NewApi")
    @Override
    public void currentSelectedPage(int currentIndex) {
        mIndicatorContainer.getChildAt(currentIndex).setBackground(mSelectedDrawable);
    }

    public View defaultIndicatorLayout(ViewGroup parent) {
        return inflater.inflate(R.layout.layout_indicator_default, parent, false);
    }

}
