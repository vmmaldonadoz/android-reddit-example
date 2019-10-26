package com.thirdmono.reddit.presentation.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.thirdmono.reddit.R;
import com.thirdmono.reddit.data.entity.SubReddit;
import com.thirdmono.reddit.data.entity.Thing;
import com.thirdmono.reddit.databinding.ActivityDetailsBinding;
import com.thirdmono.reddit.domain.utils.Constants;
import com.thirdmono.reddit.domain.utils.Utils;
import com.thirdmono.reddit.presentation.BaseActivity;

import java.util.Locale;

import timber.log.Timber;

public class DetailsActivity extends BaseActivity {

    String title;
    Thing thing;
    SubReddit subReddit;
    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();

        showDetail(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.REDDIT_SELECTED_KEY, Utils.toString(thing));
    }

    private void showDetail(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            thing = (getIntent() != null && getIntent().getStringExtra(Constants.REDDIT_SELECTED_KEY) != null) ?
                    Utils.valueOf(getIntent().getStringExtra(Constants.REDDIT_SELECTED_KEY)) :
                    null;
        } else {
            thing = Utils.valueOf(savedInstanceState.getString(Constants.REDDIT_SELECTED_KEY, ""));
            subReddit = thing.getData();
        }
        if (thing != null) {
            subReddit = thing.getData();
            setupDetailHeader();
            setupDetailBody();
        } else {
            Timber.e("showDetail(): Error retrieving the data for the detailed view.");
        }
    }

    private void setupDetailHeader() {
        title = subReddit.getDisplayName();
        binding.subredditName.setText(String.format("r/%s", subReddit.getDisplayName()));
        binding.subredditSubscribers.setText(String.format(Locale.getDefault(), "%d Subscribers", subReddit.getSubscribers()));

        if (subReddit.getBannerImg() != null && !subReddit.getBannerImg().isEmpty()) {
            Picasso.with(this)
                    .load(subReddit.getBannerImg())
                    .fit()
                    .centerCrop()
                    .into(binding.subredditBanner);
        }

        if (subReddit.getKeyColor() != null && !subReddit.getKeyColor().isEmpty()) {
            binding.toolbar.setBackgroundColor(Color.parseColor(subReddit.getKeyColor()));
            binding.subredditBanner.setBackgroundColor(Color.parseColor(subReddit.getKeyColor()));
        }

        if (subReddit.getIconImg() != null && !subReddit.getIconImg().trim().isEmpty()) {
            Picasso.with(this)
                    .load(subReddit.getIconImg())
                    .fit()
                    .centerCrop()
                    .into(binding.subredditIcon);
        }
    }

    private void setupDetailBody() {
        binding.content.subredditPublicDescription.setText(subReddit.getPublicDescription());
        binding.content.subredditFullDescription.setMarkDownText(subReddit.getDescription());
        binding.content.subredditFullDescription.setOpenUrlInBrowser(true);
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.ic_action_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.toolbarLayout.setTitle(" ");
        binding.toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.toolbarLayout.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    binding.toolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

    }
}
