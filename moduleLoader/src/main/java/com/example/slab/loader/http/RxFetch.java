package com.example.slab.loader.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Function;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;


public class RxFetch {

    private static final Gson g = new GsonBuilder().create();

    private static<T> Flowable<T> request(final Request request, final Type clazz) {
        return Flowable.fromCallable(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return OkHttpClientMgr.getInstance().newCall(request).execute();
            }
        }).map(new Function<Response, String>() {
            @Override
            public String apply(Response response) throws Exception {
                String result = response.body().string();
                if (response.code() < 300) {
                    return result;
                } else {
                    throw ExceptionHandler.handleHttpCode(response.code(), result);
                }

            }
        }).map(new Function<String, T>() {
            @Override
            public T apply(String s) throws Exception {
                return g.fromJson(s, clazz);
            }
        });
    }

    public static Flowable<ProgressMsg> download(final Request request,final String dstPath,final boolean supportResume) {
        return Flowable.create(new FlowableOnSubscribe<ProgressMsg>() {
            @Override
            public void subscribe(FlowableEmitter<ProgressMsg> e) throws Exception {
                if (e.isCancelled()) {
                    e.onComplete();
                    return;
                }
                final File targetFile = new File(dstPath);
                final String tmp = dstPath + ".tmp";
                final File tempFile = new File(tmp);
                final ProgressMsg msg = new ProgressMsg();
                Request req = request;
                long current = 0;
                if (tempFile.exists() && supportResume) {
                    current = tempFile.length();
                    req = request.newBuilder()
                            .header("RANGE", "bytes=" + current + "-")
                            .build();
                } else if (tempFile.exists()) {
                    tempFile.delete();
                }

                FileOutputStream fileOutputStream = null;
                InputStream inputStream = null;
                try {
                    Response response = OkHttpClientMgr.getInstance().newCall(req).execute();
                    final int code = response.code();
                    if (code > 300) {
                        throw ExceptionHandler.handleHttpCode(code, "文件下载状态异常:" + code);
                    }
                    fileOutputStream = new FileOutputStream(tempFile, true);
                    inputStream = response.body().byteStream();
                    msg.total = response.body().contentLength();
                    msg.current = current;
                    e.onNext(msg);
                    byte[] swap = new byte[4096];
                    int len;
                    while ((len = inputStream.read(swap)) != -1) {
                        fileOutputStream.write(swap, 0, len);
                        current += len;
                        if (e.isCancelled()) {
                            break;
                        }
                        msg.current = current;
                        e.onNext(msg);
                    }
                    fileOutputStream.flush();
                } finally {
                    if (fileOutputStream != null) {
                        Util.closeQuietly(fileOutputStream);
                    }
                    if (inputStream != null) {
                        Util.closeQuietly(inputStream);
                    }
                }
                if (!e.isCancelled()) {
                    if( targetFile.exists()) {
                        targetFile.delete();
                    }
                    msg.complete = tempFile.renameTo(targetFile);;
                    e.onNext(msg);
                }
                e.onComplete();
            }
        }, BackpressureStrategy.LATEST);
    }


    public static<T> Flowable<T> get(String url, Map<String, String> params, final Type clazz) {
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                if(param.getKey() == null ) {
                    throw new IllegalArgumentException("key == null");
                }
                if (param.getValue() == null) {
                    throw new IllegalArgumentException("value == null");
                }
                builder.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        Request req = new Request.Builder()
                .url(builder.build())
                .build();
        return request(req, clazz);
    }

    public static<T> Flowable<T> post(String url, Map<String, String> params, final Type clazz) {
        FormBody.Builder form = new FormBody.Builder();

        if (params != null) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                if(param.getKey() == null ) {
                    throw new IllegalArgumentException("key == null");
                }
                if (param.getValue() == null) {
                    throw new IllegalArgumentException("value == null");
                }
                form.add(param.getKey(), param.getValue());
            }
        }
        Request req = new Request.Builder()
                .url(url)
                .post(form.build())
                .build();
        return request(req, clazz);
    }


    public static class ProgressMsg {
        public long current;
        public long total;
        public boolean complete;

        public float getPercent() {
            if (total <= 0 || (current >= total)) {
                return 100f;
            }
            else {
                return current /(float)total;
            }
        }
    }

    private final static class ExceptionHandler {
        static HttpException handleHttpCode(int code, String result) {
            String errorMsg = "";
            switch (code) {
                case 400:
                    errorMsg = errorMsg + "错误请求！";
                    break;
                case 401:
                    errorMsg = errorMsg + "请求未授权！";
                    break;
                case 403:
                    errorMsg = errorMsg + "拒绝访问！";
                    break;
                case 404:
                    errorMsg = errorMsg + "请求不存在！";
                    break;
                case 405:
                    errorMsg = errorMsg + "请求被禁用！";
                    break;
                case 406:
                    errorMsg = errorMsg + "请求不能被接受！";
                    break;
                case 408:
                    errorMsg = errorMsg + "请求超时!";
                    break;
                case 413:
                    errorMsg = errorMsg + "服务器无法处理该请求!";
                    break;
                case 416:
                    errorMsg += "所请求的范围无法满足";
                    break;
                case 417:
                    errorMsg = errorMsg + "文件下载失败!";
                    break;
                case 500:
                    errorMsg = errorMsg + "服务器无法完成该请求!";
                    break;
                case 503:
                    errorMsg = errorMsg + "服务器正在维护中...";
                    break;
                default:
                    errorMsg = errorMsg + "网络不给力...";
                    break;
            }
            return new HttpException(code, errorMsg, result);
        }

        static String  exceptonToMessage(Throwable e) {
            if (e == null) {
                return "未知原因";
            }
            if (e instanceof NullPointerException) {
                return "数据错误";
            }
            if (e instanceof JsonSyntaxException) {
                return "数据错误";
            }
            if (e instanceof JsonParseException) {
                return "数据错误";
            }
            if (e instanceof SocketTimeoutException) {
                return "网络请求超时";
            }

            if (e instanceof SocketException) {
                return "服务器连接失败";
            }

            if (e instanceof UnknownHostException) {
                return "请连接网络...";
            }
            if (e instanceof HttpException) {
                return ((HttpException) e).getErrMsg();
            }
            return "未知原因";
        }
    }


}
