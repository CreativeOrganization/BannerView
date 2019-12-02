package cn.example.wang.bannermodule.view;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 *
 * @author WANG
 * @date 2018/5/31
 */

public class BannerFixSpeedScroller extends Scroller {

    public final static int DURATION = 500;

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
        super.startScroll(startX, startY, dx, dy,DURATION);

    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, DURATION);

    }

}
