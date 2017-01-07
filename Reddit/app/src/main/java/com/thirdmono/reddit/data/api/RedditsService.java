package com.thirdmono.reddit.data.api;


import com.thirdmono.reddit.data.entity.Listing;
import com.thirdmono.reddit.domain.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * API interface for retrieving the most recent Subreddits in Reddit.
 *
 * @author <a href="mailto:vmmzn20@gmail.com">Victor Maldonado</a>
 * @since 1.0
 */
public interface RedditsService {

    @GET(Constants.QUERY)
    Call<Listing> getPaginatedReddits(@Path(Constants.NEXT_PAGE_KEY) String nextPage);

}
