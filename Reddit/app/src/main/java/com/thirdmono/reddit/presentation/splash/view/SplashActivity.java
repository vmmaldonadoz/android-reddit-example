package com.thirdmono.reddit.presentation.splash.view;

import android.content.Intent;
import android.os.Bundle;

import com.thirdmono.reddit.R;
import com.thirdmono.reddit.RedditApplication;
import com.thirdmono.reddit.presentation.BaseActivity;
import com.thirdmono.reddit.presentation.list.MainActivity;
import com.thirdmono.reddit.presentation.splash.SplashContract;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    @Inject
    SplashContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setupDependencyInjection();
        setContentView(R.layout.activity_splash);
        presenter.setView(this);
        presenter.setupTransition();
    }

    @Override
    public void gotoListActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private void setupDependencyInjection() {
        ((RedditApplication) getApplication()).getAppComponent().inject(this);
    }
}
