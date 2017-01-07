package com.thirdmono.reddit.domain.utils;

/**
 * Application constants.
 *
 * @author <a href="mailto:vmmzn20@gmail.com">Victor Maldonado</a>
 * @since 1.0
 */
public class Constants {

    public final static String API_URL = "https://www.reddit.com/reddits.json";
    public final static String NEXT_PAGE_KEY = "NEXT_PAGE_KEY";
    public static final String APP_SELECTED_KEY = "APP_SELECTED_KEY";
    private final static int QUERY_LIMIT = 10;
    public final static String QUERY = "?after={" + NEXT_PAGE_KEY + "}&limit=" + QUERY_LIMIT;
}
