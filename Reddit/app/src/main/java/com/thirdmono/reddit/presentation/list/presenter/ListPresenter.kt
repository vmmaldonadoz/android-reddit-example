package com.thirdmono.reddit.presentation.list.presenter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.thirdmono.reddit.data.api.RedditsService
import com.thirdmono.reddit.data.entity.Listing
import com.thirdmono.reddit.data.entity.Reddit
import com.thirdmono.reddit.data.entity.Thing
import com.thirdmono.reddit.doIfBothAreNotNull
import com.thirdmono.reddit.domain.utils.Constants
import com.thirdmono.reddit.domain.utils.Utils
import com.thirdmono.reddit.presentation.BaseView
import com.thirdmono.reddit.presentation.details.DetailsActivity
import com.thirdmono.reddit.presentation.list.ListContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ListPresenter @Inject
constructor(private val redditsService: RedditsService) : ListContract.Presenter {

    private var view: ListContract.View? = null
    private var connectionBroadcastReceiver: BroadcastReceiver? = null
    private var nextPage: String? = null

    override fun resume() {
        doIfBothAreNotNull(view, connectionBroadcastReceiver) { view, broadcastReceiver ->
            view.registerConnectionBroadcastReceiver(broadcastReceiver)
        }
    }

    override fun pause() {
        doIfBothAreNotNull(view, connectionBroadcastReceiver) { view, broadcastReceiver ->
            view.unRegisterConnectionBroadcastReceiver(broadcastReceiver)
        }
    }

    override fun destroy() {
        connectionBroadcastReceiver = null
        nextPage = null
    }

    override fun setView(view: BaseView) {
        this.view = view as ListContract.View
    }

    override fun onItemClicked(context: Context, thing: Thing) {
        // Launch Activity
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(Constants.REDDIT_SELECTED_KEY, Utils.toString(thing))
        this.view?.startActivity(intent)
    }

    override fun getReddits() {
        redditsService.getPaginatedReddits().enqueue(object : Callback<Reddit> {
            override fun onResponse(call: Call<Reddit>, response: Response<Reddit>) {
                Timber.d("Got some feed back!")
                if (response.isSuccessful) {

                    val entries = getListOfApplications(response.body())
                    if (entries.isEmpty()) {
                        view?.showEmptyResponseMessage()
                        Timber.i("Empty response from API.")
                    } else {
                        view?.updateListOfReddits(entries)
                        Timber.i("Apps data was loaded from API.")
                    }
                }
            }

            override fun onFailure(call: Call<Reddit>, t: Throwable) {
                Timber.e(t, "Failed to get feed!")
                view?.showErrorDuringRequestMessage()
            }

        })
    }

    private fun getListOfApplications(feedWrapper: Reddit?): List<Thing> {
        var listing: Listing? = null
        var things: List<Thing> = ArrayList()
        if (feedWrapper != null) {
            listing = feedWrapper.listing
        }
        if (listing != null) {
            if (listing.things != null && !listing.things.isEmpty()) {
                Timber.i("getListOfApplications(): size: %s", listing.things.size)
                things = listing.things
            } else {
                Timber.w("getListOfApplications(): Empty entries.")
            }
        } else {
            Timber.w("getListOfApplications(): Empty listing!")
        }
        return things
    }

    override fun setupConnectionBroadcastReceiver() {
        connectionBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (Utils.hasNetwork(context)) {
                    view?.hideNoConnectionMessage()
                } else {
                    view?.showNoConnectionMessage()
                }
            }
        }
    }
}
