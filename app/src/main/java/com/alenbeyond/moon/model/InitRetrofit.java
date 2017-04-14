package com.alenbeyond.moon.model;

import android.text.TextUtils;

import com.alenbeyond.moon.constants.Constants;
import com.alenbeyond.moon.constants.Url;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by allen on 2017/4/12.
 */

public class InitRetrofit {

    private final Retrofit client;

    /**
     *  RxJava
     *  HttpClient
     *  Gson
     * @param sessionId
     */
    public InitRetrofit(String sessionId) {

        client = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//返回增加对RxJava的支持
                .client(getOkHttpClient(sessionId))//设置依赖的HttpClient
                .addConverterFactory(GsonConverterFactory.create())//返回的对象序列化使用Gson
                .build();
    }

    private OkHttpClient getOkHttpClient(final String sessionId) {
        KLog.d("sessionId:" + sessionId);
        OkHttpClient.Builder newBuilder = new OkHttpClient().newBuilder();
        if (!TextUtils.isEmpty(sessionId)) {
            Interceptor requestInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return chain.proceed(chain.request().newBuilder().addHeader(Constants.COOKIE, sessionId).build());
                }
            };
            newBuilder.addInterceptor(requestInterceptor);
        }
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        newBuilder.addInterceptor(loggingInterceptor);
        //设置缓存路径跟大小
        newBuilder.cache(new Cache(new File(Constants.NET_CATCH_DIR), Constants.NET_CATCH_SIZE_52428800));
        newBuilder.connectTimeout(Constants.NET_TIMEOUT_120, TimeUnit.SECONDS);
        newBuilder.readTimeout(Constants.NET_TIMEOUT_120, TimeUnit.SECONDS);
        newBuilder.writeTimeout(Constants.NET_TIMEOUT_600, TimeUnit.SECONDS);
        return newBuilder.build();
    }
}
