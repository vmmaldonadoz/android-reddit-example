package com.thirdmono.reddit.presentation.splash.presenter;

import com.thirdmono.reddit.presentation.BaseView;
import com.thirdmono.reddit.presentation.splash.SplashContract;

import javax.inject.Inject;

/**
 * Implementation of the splash presenter.
 *
 * @author <a href="mailto:vmmzn20@gmail.com">Victor Maldonado</a>
 * @since 1.0
 */
public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View view;

    @Inject
    public SplashPresenter() {
    }

    @Override
    public void resume() {
        //Do nothing.
    }

    @Override
    public void pause() {
        //Do nothing.
    }

    @Override
    public void destroy() {
        //Do nothing.
    }

    @Override
    public void setView(BaseView view) {
        this.view = (SplashContract.View) view;
    }

    @Override
    public void setupTransition() {
        view.gotoListActivity();
    }
}
