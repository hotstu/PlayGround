package com.example.slab.appglide.glide;

import okhttp3.HttpUrl;

public interface ResponseProgressListener {
    void forget(String url);

    void expect(String url, UIProgressListener listener);

    void update(HttpUrl url, long bytesRead, long contentLength);
}