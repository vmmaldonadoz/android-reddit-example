package com.thirdmono.reddit.presentation.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mukesh.MarkdownView;
import com.squareup.picasso.Picasso;
import com.thirdmono.reddit.R;
import com.thirdmono.reddit.data.entity.SubReddit;
import com.thirdmono.reddit.data.entity.Thing;
import com.thirdmono.reddit.domain.utils.Constants;
import com.thirdmono.reddit.domain.utils.Utils;
import com.thirdmono.reddit.presentation.BaseActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

public class DetailsActivity extends BaseActivity {

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.subreddit_banner)
    ImageView banner;

    @BindView(R.id.subreddit_icon)
    CircleImageView icon;

    @BindView(R.id.subreddit_name)
    TextView name;

    @BindView(R.id.subreddit_subscribers)
    TextView subscribers;

    @BindView(R.id.subreddit_public_description)
    TextView publicDescription;

    @BindView(R.id.subreddit_full_description)
    MarkdownView fullDescription;

    String title;
    Thing thing;
    SubReddit subReddit;

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

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
        name.setText(String.format("r/%s", subReddit.getDisplayName()));
        subscribers.setText(String.format(Locale.getDefault(), "%d Subscribers", subReddit.getSubscribers()));

        if (subReddit.getBannerImg() != null && !subReddit.getBannerImg().isEmpty()) {
            Picasso.with(this)
                    .load(subReddit.getBannerImg())
                    .fit()
                    .centerCrop()
                    .into(banner);
        }

        if (subReddit.getKeyColor() != null && !subReddit.getKeyColor().isEmpty()) {
            toolbar.setBackgroundColor(Color.parseColor(subReddit.getKeyColor()));
            banner.setBackgroundColor(Color.parseColor(subReddit.getKeyColor()));
        }

        if (subReddit.getIconImg() != null && !subReddit.getIconImg().trim().isEmpty()) {
            Picasso.with(this)
                    .load(subReddit.getIconImg())
                    .fit()
                    .centerCrop()
                    .into(icon);
        }
    }

    private void setupDetailBody() {
        publicDescription.setText(subReddit.getPublicDescription());
        fullDescription.setMarkDownText(subReddit.getDescription());
        fullDescription.setOpenUrlInBrowser(true);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        collapsingToolbarLayout.setTitle(" ");
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

    }
}
