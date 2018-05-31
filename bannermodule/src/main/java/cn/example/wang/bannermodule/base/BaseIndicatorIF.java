package cn.example.wang.bannermodule.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WANG on 2018/5/25.
 */

public interface BaseIndicatorIF {

    View indicatorContainerLayout(ViewGroup parent);

    View defaultIndicatorLayout(ViewGroup parent);

    Drawable selectedIndicatorRec(Context context, ViewGroup parent);
}
