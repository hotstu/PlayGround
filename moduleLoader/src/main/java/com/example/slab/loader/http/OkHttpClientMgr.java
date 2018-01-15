package com.example.slab.loader.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpClientMgr {
    private static OkHttpClient client;

    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (OkHttpClientMgr.class) {
                if (client == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(17, TimeUnit.SECONDS);
                    builder.writeTimeout(17, TimeUnit.SECONDS);
                    builder.readTimeout(77, TimeUnit.SECONDS);
                    client =  builder.build();
                }
            }
        }
        return client;
    }

}
