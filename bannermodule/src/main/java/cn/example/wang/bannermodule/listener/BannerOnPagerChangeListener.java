package cn.example.wang.bannermodule.listener;

/**
 * Created by WANG on 2018/5/23.
 */

public interface BannerOnPagerChangeListener {

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);

    void onPageScrollStateChanged(int state);
}
