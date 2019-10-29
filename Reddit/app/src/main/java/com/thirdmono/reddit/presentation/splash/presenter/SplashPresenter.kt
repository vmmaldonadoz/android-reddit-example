package com.thirdmono.reddit.presentation.splash.presenter

import com.thirdmono.reddit.presentation.BaseView
import com.thirdmono.reddit.presentation.splash.SplashContract

class SplashPresenter : SplashContract.Presenter {

    private var view: SplashContract.View? = null

    override fun setView(view: BaseView) {
        this.view = view as SplashContract.View
    }

    override fun setupTransition() {
        view!!.gotoListActivity()
    }
}
