package cn.example.wang.bannermodule.base;

import android.view.ViewGroup;

/**
 * 需要添加指示器的话，可以重写该接口，完成自定义指示器。
 *
 * @author WANG
 * @date 2018/5/25
 */

public interface IBaseIndicator {

    /**
     * 这里面要将你的指示器布局添加到parent中。
     *
     * @param parent
     */
    void attachBannerView(ViewGroup parent);

    /**
     * ViewPager中显示的页面数量。
     *
     * @param viewCount
     */
    void viewCount(int viewCount);

    /**
     * 上个被选中的界面下标。可以重置指示器。
     *
     * @param preIndex
     */
    void preSelectedPage(int preIndex);

    /**
     * 当前选中的界面下标。
     *
     * @param currentIndex
     */
    void currentSelectedPage(int currentIndex);
}
