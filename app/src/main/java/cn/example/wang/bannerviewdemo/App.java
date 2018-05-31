package cn.example.wang.bannerviewdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by WANG on 2018/5/21.
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        leakCarry();
    }

    private void leakCarry() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
