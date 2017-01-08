package com.thirdmono.reddit.domain.di;

import com.thirdmono.reddit.presentation.details.DetailsActivity;
import com.thirdmono.reddit.presentation.list.view.ListActivity;
import com.thirdmono.reddit.presentation.splash.view.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(ListActivity target);

    void inject(SplashActivity target);

    void inject(DetailsActivity target);
}
