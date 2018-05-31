package cn.example.wang.bannerviewdemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class CeshiActivity extends AppCompatActivity {
    List<Integer> imageu;
    private ViewPager viewPager;
    private int mCurrentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceshi);

        viewPager = findViewById(R.id.viewPager);

        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1,false);
            }
        });


        imageu = new ArrayList<>();
        imageu.add(R.mipmap.f);
        imageu.add(R.mipmap.timg);
        imageu.add(R.mipmap.b);
        imageu.add(R.mipmap.c);
        imageu.add(R.mipmap.d);
        imageu.add(R.mipmap.f);
        imageu.add(R.mipmap.timg);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageu.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {

                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View inflate =  LayoutInflater.from(CeshiActivity.this).inflate(R.layout.layout_imageview, container, false);
                ImageView imageView = inflate.findViewById(R.id.iv);
                imageView.setImageResource(imageu.get(position));
                container.addView(inflate);
                return inflate;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

        });
        viewPager.setPageMargin(dip2px(this,10));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    //SCROLL_STATE_IDLE
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (mCurrentPosition == 5) {
                            viewPager.setCurrentItem(1, false);
                        }
                        break;
                    //SCROLL_STATE_DRAGGING
                    case ViewPager.SCROLL_STATE_DRAGGING: //开始
                        break;
                    //SCROLL_STATE_SETTLING
                    case ViewPager.SCROLL_STATE_SETTLING:
                        break;
                }
            }
        });

    }

    public int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



}
