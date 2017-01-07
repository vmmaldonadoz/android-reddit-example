package com.thirdmono.reddit.presentation.splash;

import com.thirdmono.reddit.presentation.BasePresenter;
import com.thirdmono.reddit.presentation.BaseView;

/**
 * Contract between view and presenter for the Splash in the MVP pattern.
 *
 * @author <a href="mailto:vmmzn20@gmail.com">Victor Maldonado</a>
 * @since 1.0
 */
public interface SplashContract {

    interface Presenter extends BasePresenter {

        void setupTransition();
    }

    interface View extends BaseView {

        void gotoListActivity();
    }
}
