package cn.example.wang.bannermodule.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.example.wang.bannermodule.listener.BannerPagerClickListener;

/**
 * 将ViewPager中每个界面的View交给使用者去处理，实现该接口，完成相关的逻辑。
 *
 * @author WANG
 * @date 2018/5/18
 */

public interface IBaseImageLoad {

    /**
     * 创建展示的View
     * @param context
     */
    View createImageView(Context context, ViewGroup parent, ImageView.ScaleType scaleType);

    /**
     * 设置图片
     * @param context
     * @param imageView
     * @param url
     */
    void loadImgForNet(Context context, ImageView imageView, String url);

    /**
     * 设置本地图片
     * @param context
     * @param imageView
     * @param res
     */
    void loadImgFoeRes(Context context, ImageView imageView, int res);

    /**
     * 设置轮播图的点击事件
     * @param imageView
     * @param index
     * @param bannerPagerClickListener
     */
    void clickListener(View imageView, int index, BannerPagerClickListener bannerPagerClickListener);

}
