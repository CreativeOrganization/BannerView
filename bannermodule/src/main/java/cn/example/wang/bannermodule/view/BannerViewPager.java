package cn.example.wang.bannermodule.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.reflect.Field;

import cn.example.wang.bannermodule.BannerConstant;
import cn.example.wang.bannermodule.BannerFixSpeedScroller;

/**
 * Created by WANG on 2018/5/31.
 * 增强自己的ViewPager
 */

public class BannerViewPager extends ViewPager{


    public BannerViewPager(Context context) {
        super(context);
        init(context,null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private BannerFixSpeedScroller speedScroller;

    private void init(Context context, AttributeSet attrs){
        try {
            Field filed = ViewPager.class.getDeclaredField("mScroller");
            filed.setAccessible(true);
            speedScroller = new BannerFixSpeedScroller(context);
            filed.set(this,speedScroller);
            speedScroller.setDuration(BannerConstant.DURATION);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setScrollerDuration(int duration){
     if(null != speedScroller){
         Log.e("WANG","BannerViewPager.setScrollerDuration." );
         speedScroller.setDuration(duration);
     }
    }
}
