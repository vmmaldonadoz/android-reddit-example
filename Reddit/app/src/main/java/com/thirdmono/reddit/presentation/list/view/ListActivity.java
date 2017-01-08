package com.thirdmono.reddit.presentation.list.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.thirdmono.reddit.R;
import com.thirdmono.reddit.RedditApplication;
import com.thirdmono.reddit.data.entity.Thing;
import com.thirdmono.reddit.presentation.BaseActivity;
import com.thirdmono.reddit.presentation.list.ListContract;
import com.thirdmono.reddit.presentation.list.view.adapter.ItemSubredditAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListActivity extends BaseActivity implements ListContract.View, ItemSubredditAdapter.OnItemClickListener {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.offline_message)
    TextView offlineMessage;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.recycler_with_reddits)
    RecyclerView recyclerViewWithApps;

    @Inject
    ListContract.Presenter presenter;
    private ItemSubredditAdapter redditsAdapter;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        setupDependencyInjection();

        setupToolbar();
        setupRecyclerViewWithReddits();
        setupSwipeRefreshLayout();

        presenter.setView(this);
        presenter.setupConnectionBroadcastReceiver();
        presenter.getRedditsAfter(null);
    }

    private void setupDependencyInjection() {
        ((RedditApplication) getApplication()).getAppComponent().inject(this);
    }

    private void setupRecyclerViewWithReddits() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewWithApps.setLayoutManager(linearLayoutManager);

        redditsAdapter = new ItemSubredditAdapter(new ArrayList<Thing>(), this, this);
        recyclerViewWithApps.setAdapter(redditsAdapter);
        recyclerViewWithApps.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupSwipeRefreshLayout() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getRedditsAfter(null);
            }
        });
        swipeContainer.setColorSchemeResources(
                R.color.primary,
                R.color.accent);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.primary_text));
        toolbar.setTitle(getTitle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        snackbar = null;
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void updateListOfReddits(List<Thing> listOfReddits) {
        redditsAdapter.clear();
        redditsAdapter.setItems(listOfReddits);
        hideConnectionMessage();
        hideSwipeRefreshing();
    }

    private void hideSwipeRefreshing() {
        swipeContainer.setRefreshing(false);
    }

    private void hideConnectionMessage() {
        if (snackbar != null && snackbar.isShownOrQueued()) {
            snackbar.dismiss();
        }
    }

    @Override
    public void hideNoConnectionMessage() {
        offlineMessage.setVisibility(View.GONE);
    }

    @Override
    public void showNoConnectionMessage() {
        offlineMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void registerConnectionBroadcastReceiver(BroadcastReceiver broadcastReceiver) {
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void unRegisterConnectionBroadcastReceiver(BroadcastReceiver broadcastReceiver) {
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void showErrorDuringRequestMessage() {
        hideSwipeRefreshing();
        showRetrySnackbar(R.string.error_loading);
    }

    @Override
    public void showEmptyResponseMessage() {
        hideSwipeRefreshing();
        showRetrySnackbar(R.string.empty_response);
    }

    @Override
    public Context getActivity() {
        return this;
    }

    private void showRetrySnackbar(@StringRes int message) {
        snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.getRedditsAfter(null);
                    }
                });
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.primary));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void onClick(View view, Thing thing) {
        presenter.onItemClicked(view, thing);
    }
}
