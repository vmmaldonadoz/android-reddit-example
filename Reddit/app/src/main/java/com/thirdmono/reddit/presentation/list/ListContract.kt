package com.thirdmono.reddit.presentation.list

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.thirdmono.reddit.data.entity.Thing
import com.thirdmono.reddit.presentation.BasePresenter
import com.thirdmono.reddit.presentation.BaseView


interface ListContract {

    interface Presenter : BasePresenter {

        fun onItemClicked(context: Context, thing: Thing)

        fun getReddits()

        fun setupConnectionBroadcastReceiver()
    }

    interface View : BaseView {

        fun updateListOfReddits(listOfReddits: List<Thing>)

        fun hideNoConnectionMessage()

        fun showNoConnectionMessage()

        fun registerConnectionBroadcastReceiver(broadcastReceiver: BroadcastReceiver)

        fun unRegisterConnectionBroadcastReceiver(broadcastReceiver: BroadcastReceiver)

        fun showErrorDuringRequestMessage()

        fun showEmptyResponseMessage()

        fun startActivity(intent: Intent)
    }
}
