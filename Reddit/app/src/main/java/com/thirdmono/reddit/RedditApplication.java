package com.thirdmono.reddit;

import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.thirdmono.reddit.domain.di.AppComponent;
import com.thirdmono.reddit.domain.di.AppModule;
import com.thirdmono.reddit.domain.di.DaggerAppComponent;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Multidex application.
 *
 * @author <a href="mailto:vmmzn20@gmail.com">Victor Maldonado</a>
 * @since 1.0
 */
public class RedditApplication extends MultiDexApplication {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupCalligraphy();
        createAppComponent();
        setupLeakCanary();
    }


    private void setupCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    private void createAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return this.appComponent;
    }

}
