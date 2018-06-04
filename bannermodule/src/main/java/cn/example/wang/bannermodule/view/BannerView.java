package cn.example.wang.bannermodule.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.example.wang.bannermodule.BannerConstant;
import cn.example.wang.bannermodule.BannerImageLoad;
import cn.example.wang.bannermodule.BannerIndicatorManager;
import cn.example.wang.bannermodule.WeakHandler;
import cn.example.wang.bannermodule.base.BaseImageLoadIF;
import cn.example.wang.bannermodule.base.BaseIndicatorIF;
import cn.example.wang.bannermodule.listener.BannerOnPagerChangeListener;
import cn.example.wang.bannermodule.listener.BannerPagerClickListener;
import cn.example.wang.bannermodule.transformer.ABaseTransformer;

/**
 * Created by WANG on 2018/5/18.
 * 1.实现类似画廊特效的时候最好设置 setOffscreenPageLimit 为显示的图片的数量.
 * 2.reset 方法跟create方法不能同时调用.reset的时候会把你设置的mOffscreenPageLimit重置
 * 3.setIndicatorManager 方法只有在create之前有效
 */

public class BannerView extends FrameLayout {

    /*
    * 默认将图片集合手动扩容四张图片,配合轮播
    * */
    private final int EXPAND_SOURCE_ALL=4;

    /*
    * 图片集合单侧扩容的数量
    * */
    private final int EXPAND_SOURCE_ONE_SIDE=2;

    public BannerView(Context context) {
        super(context);
        init(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private BannerViewPager mViewPager;
    private List<Object> mImagUrls;
    private List<View> mViews;
    private BaseImageLoadIF mImageLoad;
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_CROP;
    private ImageAdapter mImageAdapter;
    private int mCount;
    private volatile int mCurrentPosition = 1;
    private int mStartPosition = 2;
    private boolean isAutoPlay = true;
    private volatile long mDelayTime = 2000;
    private boolean isRestData = false;
    private WeakHandler mHandler = new WeakHandler();
    private BannerPagerClickListener mBannerPagerClickListener;
    private BannerOnPagerChangeListener meOnPagerChangeListener;
    private BaseIndicatorIF mIndicatorManager;
    private boolean mShowIndicator = true;
    private ViewGroup mIndicatorContainer;
    private Drawable mSelectedIndicatorDrawable;
    private View mDefaultIndicator;
    private int mPrePosition = -1;
    private int mOffscreenPageLimit;

    public void setBannerPagerClickListener(BannerPagerClickListener bannerPagerClickListener) {
        this.mBannerPagerClickListener = bannerPagerClickListener;
    }

    public void setBannerOnPagerChangeListener(BannerOnPagerChangeListener bannerPagerClickListener) {
        this.meOnPagerChangeListener = bannerPagerClickListener;
    }

    public BannerView showIndicator(boolean mShowIndicator) {
        this.mShowIndicator = mShowIndicator;
        return this;
    }

    public BannerView setViewPagerOffscreenPageLimit(int mOffscreenPageLimit) {
        this.mOffscreenPageLimit = mOffscreenPageLimit + EXPAND_SOURCE_ALL;
        return this;
    }

    public BannerView setViewpagerMargin(int margin) {
        mViewPager.setPageMargin(margin);
        return this;
    }

    public BannerView autoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
        return this;
    }

    public BannerView setDelayTimeForMillis(long delayTime) {
        this.mDelayTime = delayTime;
        return this;
    }

    public BannerView setDelayTimeForSecond(int second) {
        this.mDelayTime = second * 1000;
        return this;
    }

    public BannerView setImageLoad(BaseImageLoadIF mImageLoad) {
        this.mImageLoad = mImageLoad;
        return this;
    }

    public BannerView setStartPosition(int startPosition) {
        this.mStartPosition = startPosition + 2;

        return this;
    }

    public BannerView setScaleType(ImageView.ScaleType scaleType) {
        this.mScaleType = scaleType;
        return this;
    }

    public BannerView setImagRecs(List<Integer> data) {
        clearData();
        this.mImagUrls.addAll(data);
        return this;
    }

    public BannerView setImagUrls(List<?> data) {
        clearData();
        this.mImagUrls.addAll(data);
        return this;
    }

    public BannerView setViewPagerLayoutParams(LayoutParams layoutParams) {
        layoutParams.gravity = Gravity.CENTER;
        mViewPager.setLayoutParams(layoutParams);
        return this;
    }

    public BannerView addPagerTransformer(ABaseTransformer transformer) {
        mViewPager.setPageTransformer(true, transformer);
        return this;
    }

    public BannerView setOffscreenPageLimit(int limit) {
        mViewPager.setOffscreenPageLimit(limit);
        return this;
    }

    private BaseImageLoadIF getImageLoad() {
        return new BannerImageLoad();
    }

    private void init(Context context, AttributeSet attrs) {
        initViews(context);
    }

    private void initViews(Context context) {
        if (null == mViewPager) {
            mViewPager = new BannerViewPager(context);
            LayoutParams ls = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ls.gravity = Gravity.CENTER;
            mViewPager.setLayoutParams(ls);
            mViewPager.setClipChildren(false);
            this.addView(mViewPager, 0);
            mImagUrls = new ArrayList<>();
            mViews = new ArrayList<>();
        }
    }

    public BannerView setIndicatorManager(BaseIndicatorIF indicatorManager) {
        mIndicatorManager = indicatorManager;
        createIndicator();
        return this;
    }


    public BannerView setIndicatorManager(String flag) {
        switch (flag) {
            case BannerConstant.INDICATOR_DEFAULT:
                mIndicatorManager = new BannerIndicatorManager(getContext().getApplicationContext());
                break;
        }
        createIndicator();
        return this;
    }

    private void createIndicator() {
        if (mIndicatorManager == null) return;

        mIndicatorContainer = (ViewGroup) mIndicatorManager.indicatorContainerLayout(this);

        if (mIndicatorContainer == null) return;

        LayoutParams indicatorLS = (LayoutParams) mIndicatorContainer.getLayoutParams();
        indicatorLS.gravity = Gravity.BOTTOM;
        mIndicatorContainer.setLayoutParams(indicatorLS);

        //再容器初始化之后 再初始化这俩
        mSelectedIndicatorDrawable = mIndicatorManager.selectedIndicatorRec(getContext(), mIndicatorContainer);

        mDefaultIndicator = mIndicatorManager.defaultIndicatorLayout(mIndicatorContainer);
    }


    public void reset(List<String> imageUrl) {
        if (null == imageUrl) return;
        mOffscreenPageLimit = imageUrl.size() + EXPAND_SOURCE_ALL;
        mStartPosition = EXPAND_SOURCE_ONE_SIDE;
        isRestData = true;
        clearData();
        this.mImagUrls.addAll(imageUrl);
        create();
    }

    public void onResume() {
        startAutoRuning();
    }

    public void onStop() {
        stopAutoRunging();
    }


    private void clearData() {
        stopAutoRunging();
        this.mViews.clear();
        this.mImagUrls.clear();
        this.mCount = -1;
    }

    private void startAutoRuning() {
        if (isAutoPlay) {
            mHandler.removeCallbacks(mRunnable);
            mHandler.postDelayed(mRunnable, mDelayTime);
        }
    }

    private void stopAutoRunging() {
        if (isAutoPlay) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    public void create() {
        try {
            if (mImagUrls == null || mImagUrls.size() <= 0) {
                throw new NullPointerException(" Error  图片资源为空");
            }
            addIndicator();
            setImageData(mImagUrls);
            setViewPagerData();
            if (isRestData) {
                startAutoRuning();
            }
        } catch (Exception e) {
            Log.e("WANG", "Error" + e);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void addIndicator() {
        if (!mShowIndicator || null == mIndicatorManager) return;
        View child = getChildAt(1);
        if (null == child) {
            this.addView(mIndicatorContainer, 1);
        }
        mIndicatorContainer.removeAllViews();
        for (int i = 0; i < mImagUrls.size(); i++) {
            View mDefaultIndicator = mIndicatorManager.defaultIndicatorLayout(mIndicatorContainer);
            mIndicatorContainer.addView(mDefaultIndicator);
        }
        int realPosition = getRealPosition(mStartPosition);
        mIndicatorContainer.getChildAt(realPosition).setBackground(mSelectedIndicatorDrawable);
        mPrePosition = realPosition;
    }

    private void setImageData(List<Object> data) {
        mCount = data.size();
        if (mCount <= 0) {
            throw new NullPointerException("Error  图片资源为空");
        }
        for (int i = 0; i < mCount + EXPAND_SOURCE_ALL; i++) {
            if (null == mImageLoad) {
                mImageLoad = getImageLoad();
            }
            ViewGroup view = (ViewGroup) mImageLoad.createImageView(getContext(), this, mScaleType);
            View child = view.getChildAt(0);
            if (null != child && !(child instanceof ImageView)) {
                throw new NullPointerException("图片布局有误");
            }
            ImageView imageView = (ImageView) child;
            Object url;
            //在数据源首位都重复添加两张图片,优化连贯自动轮播效果
            if (i == 0) {
                //最后一张图片
                url = data.get(data.size() - EXPAND_SOURCE_ONE_SIDE);
            } else if (i == 1) {
                url = data.get(data.size() - 1);
            } else if (i == mCount + EXPAND_SOURCE_ONE_SIDE) {
                url = data.get(0);
            } else if (i == mCount + 3) {
                url = data.get(1);
            } else {
                url = data.get(i - EXPAND_SOURCE_ONE_SIDE);
            }
            if (null != url) {
                if (url instanceof String) {
                    mImageLoad.loadImgForNet(getContext(), imageView, (String) url);
                } else if (url instanceof Integer) {
                    mImageLoad.loadImgFoeRes(getContext(), imageView, (Integer) url);
                }
            }

            if (!mViews.contains(view)) {
                mViews.add(view);
            }
        }
    }

    private void setViewPagerData() {
        if (null == mImageAdapter) {
            mImageAdapter = new ImageAdapter();
            initImageViewListener();
        }
        mViewPager.setAdapter(mImageAdapter);
        mViewPager.setCurrentItem(mStartPosition);
        mViewPager.setOffscreenPageLimit(mOffscreenPageLimit);
        mViewPager.setFocusable(true);
    }

    private void initImageViewListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int realPosition = position - EXPAND_SOURCE_ONE_SIDE;
                //总会先走一次
                if (hasWindowFocus() && meOnPagerChangeListener != null) {
                    meOnPagerChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
                }
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                if (!hasWindowFocus()) return;
                int realPosition = getRealPosition(position);
                if (realPosition == mPrePosition) return;
                if (mIndicatorContainer != null && mIndicatorContainer.getChildCount() > 0) {
                    mIndicatorContainer.getChildAt(realPosition).setBackground(mSelectedIndicatorDrawable);
                    if (mPrePosition >= 0) {
                        mIndicatorContainer.getChildAt(mPrePosition).setBackground(mDefaultIndicator.getBackground());
                    }
                    mPrePosition = realPosition;
                }
                if (meOnPagerChangeListener != null) {
                    meOnPagerChangeListener.onPageSelected(realPosition);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    //SCROLL_STATE_IDLE
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (mCurrentPosition == 1) {
                            mViewPager.setCurrentItem(mCount + 1, false);
                        } else if (mCurrentPosition == mCount + EXPAND_SOURCE_ONE_SIDE) {
                            mViewPager.setCurrentItem(EXPAND_SOURCE_ONE_SIDE, false);
                        }
                        break;
                    //SCROLL_STATE_DRAGGING
                    case ViewPager.SCROLL_STATE_DRAGGING: //开始
                        if (mCurrentPosition == 1) {
                            mViewPager.setCurrentItem(mCount + 1, false);
                        } else if (mCurrentPosition == mCount + EXPAND_SOURCE_ONE_SIDE) {
                            mViewPager.setCurrentItem(EXPAND_SOURCE_ONE_SIDE, false);
                        }
                        break;
                    //SCROLL_STATE_SETTLING
                    case ViewPager.SCROLL_STATE_SETTLING:
                        break;
                }

                if (meOnPagerChangeListener != null) {
                    meOnPagerChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    private int getRealPosition(int position) {
        int realPosition = (position - EXPAND_SOURCE_ONE_SIDE) % mCount;
        if (realPosition < 0) {
            realPosition += mCount;
        }
        return realPosition;
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCount > 1 && isAutoPlay) {
                mCurrentPosition = mCurrentPosition % (mCount + EXPAND_SOURCE_ONE_SIDE) + 1;
                if (mCurrentPosition == 1) {
                    mViewPager.setCurrentItem(EXPAND_SOURCE_ONE_SIDE, false);
                    mHandler.post(mRunnable);
                } else {
                    mViewPager.setCurrentItem(mCurrentPosition);
                    mHandler.postDelayed(mRunnable, mDelayTime);
                }
            }
        }
    };

    class ImageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViews.get(position);
            container.addView(view);
            int realPosition = (position - EXPAND_SOURCE_ONE_SIDE) % mCount;
            mImageLoad.clickListener(view, realPosition, mBannerPagerClickListener);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoRuning();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoRunging();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
