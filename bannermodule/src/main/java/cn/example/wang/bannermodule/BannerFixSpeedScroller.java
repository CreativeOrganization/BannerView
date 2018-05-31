package cn.example.wang.bannermodule;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by WANG on 2018/5/31.
 */

public class BannerFixSpeedScroller extends Scroller {

    public int mDuration = BannerConstant.DURATION;

    public BannerFixSpeedScroller(Context context) {
        super(context);
    }

    public BannerFixSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerFixSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy,mDuration);

    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);

    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

}
