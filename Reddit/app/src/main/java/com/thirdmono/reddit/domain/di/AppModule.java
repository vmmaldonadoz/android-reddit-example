package com.thirdmono.reddit.domain.di;

import android.content.Context;

import com.thirdmono.reddit.BuildConfig;
import com.thirdmono.reddit.RedditApplication;
import com.thirdmono.reddit.data.api.RedditsService;
import com.thirdmono.reddit.domain.utils.Constants;
import com.thirdmono.reddit.domain.utils.Utils;
import com.thirdmono.reddit.presentation.list.ListContract;
import com.thirdmono.reddit.presentation.list.presenter.ListPresenter;
import com.thirdmono.reddit.presentation.splash.SplashContract;
import com.thirdmono.reddit.presentation.splash.presenter.SplashPresenter;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private static final String CACHE_CONTROL = "Cache-Control";

    private final RedditApplication application;


    public AppModule(RedditApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }

    @Provides
    @Named("offlineCacheInterceptor")
    public Interceptor provideOfflineCacheInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!Utils.hasNetwork(context)) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(2, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    @Provides
    @Named("cacheInterceptor")
    public Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(2, TimeUnit.HOURS)
                        .build();

                return response.newBuilder()
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    @Provides
    public Cache provideCache(Context context) {
        return new Cache(new File(context.getCacheDir(), "http-cache"), 10 * 1024 * 1024);
    }

    @Provides
    public OkHttpClient provideCapableHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                                 Cache cache,
                                                 @Named("cacheInterceptor") Interceptor cacheInterceptor,
                                                 @Named("offlineCacheInterceptor") Interceptor offlineCacheInterceptor) {

        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .addNetworkInterceptor(cacheInterceptor)
                .addInterceptor(offlineCacheInterceptor)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
    }


    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    public RedditsService provideBookService(Retrofit retrofit) {
        return retrofit.create(RedditsService.class);
    }

    @Provides
    public SplashContract.Presenter provideSplashPresenter() {
        return new SplashPresenter();
    }

    @Provides
    public ListContract.Presenter provideAppListPresenter(RedditsService redditsService) {
        return new ListPresenter(redditsService);
    }
//
//    @Provides
//    public DetailsContract.Presenter provideDetailsPresenter() {
//        return new DetailsPresenter();
//    }
}