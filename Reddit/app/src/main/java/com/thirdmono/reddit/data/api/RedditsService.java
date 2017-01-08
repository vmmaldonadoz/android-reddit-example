package com.thirdmono.reddit.data.api;


import com.thirdmono.reddit.data.entity.Reddit;
import com.thirdmono.reddit.domain.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.thirdmono.reddit.domain.utils.Constants.QUERY_LIMIT;
import static com.thirdmono.reddit.domain.utils.Constants.QUERY_PAGINATE_AFTER;

/**
 * API interface for retrieving the most recent Subreddits in Reddit.
 *
 * @author <a href="mailto:vmmzn20@gmail.com">Victor Maldonado</a>
 * @since 1.0
 */
public interface RedditsService {

    @GET(Constants.QUERY_REDDITS)
    Call<Reddit> getPaginatedReddits(
            @Query(QUERY_PAGINATE_AFTER) String nextPage,
            @Query(QUERY_LIMIT) int limit);

}
