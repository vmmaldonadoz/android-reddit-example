package com.thirdmono.reddit.presentation.splash.view

import android.content.Intent
import android.os.Bundle

import com.thirdmono.reddit.R
import com.thirdmono.reddit.RedditApplication
import com.thirdmono.reddit.presentation.BaseActivity
import com.thirdmono.reddit.presentation.list.view.ListActivity
import com.thirdmono.reddit.presentation.splash.SplashContract

import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashContract.View {

    @Inject
    lateinit var presenter: SplashContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencyInjection()
        setContentView(R.layout.activity_splash)
        presenter.setView(this)
        presenter.setupTransition()
    }

    override fun gotoListActivity() {
        startActivity(Intent(this@SplashActivity, ListActivity::class.java))
        finish()
    }

    private fun setupDependencyInjection() {
        (application as RedditApplication).appComponent.inject(this)
    }
}
