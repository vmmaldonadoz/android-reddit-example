package com.thirdmono.reddit.presentation.splash

import com.thirdmono.reddit.presentation.BasePresenter
import com.thirdmono.reddit.presentation.BaseView

interface SplashContract {

    interface Presenter : BasePresenter {

        fun setupTransition()
    }

    interface View : BaseView {

        fun gotoListActivity()
    }
}
