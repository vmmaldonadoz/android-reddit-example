package com.thirdmono.reddit.presentation.list.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.thirdmono.reddit.data.api.RedditsService;
import com.thirdmono.reddit.data.entity.Listing;
import com.thirdmono.reddit.data.entity.Reddit;
import com.thirdmono.reddit.data.entity.Thing;
import com.thirdmono.reddit.domain.utils.Constants;
import com.thirdmono.reddit.domain.utils.Utils;
import com.thirdmono.reddit.presentation.BaseView;
import com.thirdmono.reddit.presentation.details.DetailsActivity;
import com.thirdmono.reddit.presentation.list.ListContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Presenter for the list of reddits.
 *
 * @author <a href="mailto:vmmzn20@gmail.com">Victor Maldonado</a>
 * @since 1.0
 */
public class ListPresenter implements ListContract.Presenter {

    private final RedditsService redditsService;
    private ListContract.View view;
    private BroadcastReceiver connectionBroadcastReceiver;
    private String nextPage;

    @Inject
    public ListPresenter(RedditsService redditsService) {
        this.redditsService = redditsService;
    }

    @Override
    public void resume() {
        view.registerConnectionBroadcastReceiver(connectionBroadcastReceiver);
    }

    @Override
    public void pause() {
        view.unRegisterConnectionBroadcastReceiver(connectionBroadcastReceiver);
    }

    @Override
    public void destroy() {
        connectionBroadcastReceiver = null;
        nextPage = null;
    }

    @Override
    public void setView(BaseView view) {
        this.view = (ListContract.View) view;
    }

    @Override
    public void onItemClicked(View view, Thing thing) {
        // Launch Activity
        Intent intent = new Intent(this.view.getActivity(), DetailsActivity.class);
        intent.putExtra(Constants.REDDIT_SELECTED_KEY, Utils.toString(thing));
        this.view.startActivity(intent);
    }

    @Override
    public void getRedditsAfter(String nextPage) {
        redditsService.getPaginatedReddits(nextPage, Constants.DEFAULT_LIMIT).enqueue(new Callback<Reddit>() {
            @Override
            public void onResponse(Call<Reddit> call, Response<Reddit> response) {
                Timber.d("Got some feed back!");
                if (response.isSuccessful()) {

                    List<Thing> entries = getListOfApplications(response.body());
                    if (entries.isEmpty()) {
                        view.showEmptyResponseMessage();
                        Timber.i("Empty response from API.");
                    } else {
                        view.updateListOfReddits(entries);
                        Timber.i("Apps data was loaded from API.");
                    }
                }
            }

            @Override
            public void onFailure(Call<Reddit> call, Throwable t) {
                Timber.e(t, "Failed to get feed!");
                view.showErrorDuringRequestMessage();
            }

        });
    }

    private List<Thing> getListOfApplications(Reddit feedWrapper) {
        Listing listing = null;
        List<Thing> things = new ArrayList<>();
        if (feedWrapper != null) {
            listing = feedWrapper.getListing();
        }
        if (listing != null) {
            if (listing.getThings() != null && !listing.getThings().isEmpty()) {
                Timber.i("getListOfApplications(): size: %s", listing.getThings().size());
                things = listing.getThings();
            } else {
                Timber.w("getListOfApplications(): Empty entries.");
            }
        } else {
            Timber.w("getListOfApplications(): Empty listing!");
        }
        return things;
    }

    @Override
    public void setupConnectionBroadcastReceiver() {
        connectionBroadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (Utils.hasNetwork(context)) {
                    view.hideNoConnectionMessage();
                } else {
                    view.showNoConnectionMessage();
                }
            }
        };
    }
}
