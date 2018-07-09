package cn.example.wang.bannermodule.base;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by WANG on 2018/5/25.
 */

public interface IBaseIndicator {

    void attachBannerView(Context context, ViewGroup parent);

    void viewCount(int viewCount);

    void preSelectedPage(int preIndex);

    void currentSelectedPage(int currentIndex);
}
