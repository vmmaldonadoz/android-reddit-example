package com.thirdmono.reddit.presentation.list.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.thirdmono.reddit.R
import com.thirdmono.reddit.RedditApplication
import com.thirdmono.reddit.data.entity.Thing
import com.thirdmono.reddit.databinding.ActivityListBinding
import com.thirdmono.reddit.presentation.BaseActivity
import com.thirdmono.reddit.presentation.list.ListContract
import com.thirdmono.reddit.presentation.list.view.adapter.ItemSubredditAdapter
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*
import javax.inject.Inject

class ListActivity : BaseActivity(), ListContract.View, ItemSubredditAdapter.OnItemClickListener {

    @Inject
    lateinit var presenter: ListContract.Presenter
    private lateinit var binding: ActivityListBinding

    private lateinit var redditsAdapter: ItemSubredditAdapter
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupDependencyInjection()

        setupToolbar()
        setupRecyclerViewWithReddits()
        setupSwipeRefreshLayout()

        presenter?.setView(this)
        presenter?.setupConnectionBroadcastReceiver()
        presenter?.getReddits()
    }

    private fun setupDependencyInjection() {
        (application as RedditApplication).appComponent?.inject(this)
    }

    private fun setupRecyclerViewWithReddits() {

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = linearLayoutManager

        redditsAdapter = ItemSubredditAdapter(ArrayList(), this, this)
        binding.recyclerView.adapter = redditsAdapter
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeContainer.setOnRefreshListener { presenter?.getReddits() }
        binding.swipeContainer.setColorSchemeResources(
                R.color.primary,
                R.color.accent)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.primary_text))
        binding.toolbar.title = title
    }

    override fun onResume() {
        super.onResume()
        presenter?.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter?.pause()
    }

    override fun onDestroy() {
        presenter?.destroy()
        snackbar = null
        super.onDestroy()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun updateListOfReddits(listOfReddits: List<Thing>) {
        redditsAdapter.clear()
        redditsAdapter.items = listOfReddits
        hideConnectionMessage()
        hideSwipeRefreshing()
    }

    private fun hideSwipeRefreshing() {
        binding.swipeContainer.isRefreshing = false
    }

    private fun hideConnectionMessage() {
        if (snackbar != null && snackbar?.isShownOrQueued == true) {
            snackbar?.dismiss()
        }
    }

    override fun hideNoConnectionMessage() {
        binding.offlineMessage.visibility = View.GONE
    }

    override fun showNoConnectionMessage() {
        binding.offlineMessage.visibility = View.VISIBLE
    }

    override fun registerConnectionBroadcastReceiver(broadcastReceiver: BroadcastReceiver) {
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun unRegisterConnectionBroadcastReceiver(broadcastReceiver: BroadcastReceiver) {
        unregisterReceiver(broadcastReceiver)
    }

    override fun showErrorDuringRequestMessage() {
        hideSwipeRefreshing()
        showRetrySnackbar(R.string.error_loading)
    }

    override fun showEmptyResponseMessage() {
        hideSwipeRefreshing()
        showRetrySnackbar(R.string.empty_response)
    }

    private fun showRetrySnackbar(@StringRes message: Int) {
        snackbar = Snackbar.make(binding.coordinator, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry) { presenter?.getReddits() }
        snackbar?.setActionTextColor(Color.WHITE)
        snackbar?.duration = Snackbar.LENGTH_INDEFINITE

        snackbar?.view?.let { snackbarView ->
            snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
            val textView = snackbarView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
            textView.setTextColor(Color.WHITE)
        }

        snackbar?.show()
    }

    override fun onClick(view: View, thing: Thing) {
        presenter?.onItemClicked(this, thing)
    }
}
