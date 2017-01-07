package com.thirdmono.reddit.presentation.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.thirdmono.reddit.data.entity.Thing;
import com.thirdmono.reddit.presentation.BasePresenter;
import com.thirdmono.reddit.presentation.BaseView;

import java.util.List;


/**
 * Contract between view and presenter for the App list in the MVP pattern.
 *
 * @author <a href="mailto:vmmzn20@gmail.com">Victor Maldonado</a>
 * @since 1.0
 */
public interface ListContract {

    interface Presenter extends BasePresenter {

        void onItemClicked(android.view.View view, Thing thing);

        void getRedditsAfter(String nextPage);

        void setupConnectionBroadcastReceiver();
    }

    interface View extends BaseView {

        void updateListOfReddits(List<Thing> listOfReddits);

        void hideNoConnectionMessage();

        void showNoConnectionMessage();

        void registerConnectionBroadcastReceiver(BroadcastReceiver broadcastReceiver);

        void unRegisterConnectionBroadcastReceiver(BroadcastReceiver broadcastReceiver);

        void showErrorDuringRequestMessage();

        void showEmptyResponseMessage();

        Context getActivity();

        void startActivity(Intent intent);
    }
}
