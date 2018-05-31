package cn.example.wang.bannermodule;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.example.wang.bannermodule.base.BaseIndicatorIF;

/**
 * Created by WANG on 2018/5/25.
 * 这个类可以让你设置指示器容器的大小跟指示器的位置,已经定制你的指示器的样式.
 * indicatorContainerLayout :
 * defaultIndicatorLayout : 该方法返回的View为指示器显示的View. 这里主要是规定了各个指示器的margin 大小等值.
 * selectedIndicatorRec : 该方法返回的是当前被选择的指示器的样式 也就是Drawable对象,你也可以用一个View去获取Drawable对象...
 */

public class BannerIndicatorManager implements BaseIndicatorIF {
    private LayoutInflater inflater;

    public BannerIndicatorManager(Context context) {
        inflater = LayoutInflater.from(context.getApplicationContext());
    }

    @Override
    public View indicatorContainerLayout(ViewGroup parent) {
        return inflater.inflate(R.layout.layout_indicator, parent, false);
    }

    @Override
    public View defaultIndicatorLayout(ViewGroup parent) {
        return inflater.inflate(R.layout.layout_indicator_default, parent, false);
    }

    @Override
    public Drawable selectedIndicatorRec(Context context,ViewGroup  parent) {
        Resources resources = context.getResources();
        Drawable drawable = resources.getDrawable(R.drawable.bg_circle_red);
        //Or
       /* View inflate = inflater.inflate(R.layout.layout_indicator_circle_selected, parent, false);
        Drawable background = inflate.getBackground();*/
        return drawable;
    }
}
