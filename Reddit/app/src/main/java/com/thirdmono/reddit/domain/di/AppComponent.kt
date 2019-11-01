package com.thirdmono.reddit.domain.di

import com.thirdmono.reddit.presentation.details.DetailsActivity
import com.thirdmono.reddit.presentation.list.view.ListActivity
import com.thirdmono.reddit.presentation.splash.view.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(target: ListActivity)

    fun inject(target: SplashActivity)

    fun inject(target: DetailsActivity)
}
