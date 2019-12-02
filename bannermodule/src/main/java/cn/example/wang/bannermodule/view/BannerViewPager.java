package cn.example.wang.bannermodule.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.lang.reflect.Field;

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
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
