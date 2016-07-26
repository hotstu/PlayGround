package com.example.slab.appglide.dagger;

import android.content.Context;

import com.example.slab.appglide.App;
import com.example.slab.appglide.BuildConfig;
import com.example.slab.appglide.glide.DispatchingProgressListener;
import com.example.slab.appglide.glide.OkHttpProgressResponseBody;
import com.example.slab.appglide.glide.ResponseProgressListener;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by hotstuNg on 2016/7/25.
 */
@Module
public class AppModule {
    private App mApp;

    public AppModule(App mApp) {
        this.mApp = mApp;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApp;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOKHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        return builder.build();
    }

    @Provides
    @Singleton
    @ProgressClient
    public OkHttpClient provideOKHttpClientWithProgress(final ResponseProgressListener listener) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            builder.addInterceptor(httpLoggingInterceptor);
        }

        builder.addInterceptor(new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .body(new OkHttpProgressResponseBody(request.url(), response.body(), listener))
                        .build();
            }
        });
        return builder.build();
    }


    @Provides
    @Singleton
    public ResponseProgressListener provideResponseProgressListener() {
        return new DispatchingProgressListener();
    }
}
