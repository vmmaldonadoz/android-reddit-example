package com.thirdmono.reddit.presentation.details

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import com.squareup.picasso.Picasso
import com.thirdmono.reddit.R
import com.thirdmono.reddit.data.entity.SubReddit
import com.thirdmono.reddit.data.entity.Thing
import com.thirdmono.reddit.databinding.ActivityDetailsBinding
import com.thirdmono.reddit.domain.utils.Constants
import com.thirdmono.reddit.domain.utils.Utils
import com.thirdmono.reddit.ifNotNullOrBlank
import com.thirdmono.reddit.presentation.BaseActivity
import timber.log.Timber
import java.util.*

class DetailsActivity : BaseActivity() {

    lateinit var title: String
    private var thing: Thing? = null
    private var subReddit: SubReddit? = null

    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        showDetail(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.REDDIT_SELECTED_KEY, Utils.toString(thing))
    }

    private fun showDetail(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            if (intent != null && intent.getStringExtra(Constants.REDDIT_SELECTED_KEY) != null) {
                thing = Utils.valueOf(intent.getStringExtra(Constants.REDDIT_SELECTED_KEY))
            }
        } else {
            thing = Utils.valueOf(savedInstanceState.getString(Constants.REDDIT_SELECTED_KEY, ""))
            subReddit = thing!!.data
        }
        if (thing != null) {
            subReddit = thing!!.data
            setupDetailHeader()
            setupDetailBody()
        } else {
            Timber.e("showDetail(): Error retrieving the data for the detailed view.")
        }
    }

    private fun setupDetailHeader() {
        title = subReddit?.displayName.orEmpty()
        binding.subredditName.text = String.format("r/%s", title)
        binding.subredditSubscribers.text = String.format(Locale.getDefault(), "%d Subscribers", subReddit?.subscribers)

        subReddit?.bannerImg.ifNotNullOrBlank { bannerImage ->
            Picasso.with(this)
                    .load(bannerImage)
                    .fit()
                    .centerCrop()
                    .into(binding.subredditBanner)
        }

        subReddit?.keyColor.ifNotNullOrBlank { color ->
            binding.toolbar.setBackgroundColor(Color.parseColor(color))
            binding.subredditBanner.setBackgroundColor(Color.parseColor(color))
        }

        subReddit?.iconImg.ifNotNullOrBlank { icon ->
            Picasso.with(this)
                    .load(icon)
                    .fit()
                    .centerCrop()
                    .into(binding.subredditIcon)
        }
    }

    private fun setupDetailBody() {
        binding.subredditPublicDescription.text = subReddit?.publicDescription.orEmpty()
        binding.subredditFullDescription.setMarkDownText(subReddit?.description.orEmpty())
        binding.subredditFullDescription.isOpenUrlInBrowser = true
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_action_back)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.toolbarLayout.title = " "
        binding.toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))

        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.toolbarLayout.title = title
                    isShow = true
                } else if (isShow) {
                    binding.toolbarLayout.title = " "
                    isShow = false
                }
            }
        })

    }
}
