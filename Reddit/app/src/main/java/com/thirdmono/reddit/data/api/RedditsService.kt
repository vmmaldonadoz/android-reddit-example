package com.thirdmono.reddit.data.api

import com.thirdmono.reddit.data.entity.Reddit
import com.thirdmono.reddit.domain.utils.Constants
import com.thirdmono.reddit.domain.utils.Constants.QUERY_LIMIT
import com.thirdmono.reddit.domain.utils.Constants.QUERY_PAGINATE_AFTER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditsService {

    @GET(Constants.QUERY_REDDITS)
    fun getPaginatedReddits(
            @Query(QUERY_PAGINATE_AFTER) nextPage: String = "",
            @Query(QUERY_LIMIT) limit: Int = Constants.DEFAULT_LIMIT): Call<Reddit>

}
