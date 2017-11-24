package com.superlibrary.ken.service;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.superlibrary.ken.base.BaseApplication;
import com.superlibrary.ken.common.Constants;
import com.superlibrary.ken.utils.ToolUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名称：sudiyuan
 * 类描述：
 * 创建人：leitao
 * 创建时间：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class RetrofitManager {

    public final static int CONNECT_TIMEOUT =2;
    public final static int READ_TIMEOUT=100;
    public final static int WRITE_TIMEOUT=60;
    private static GetApiInterface mGetApiInterface;
    private static Context context= BaseApplication.mInstance;
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
//            if (ToolUtil.isNetworkAvailable(context)) {
//                request = request.newBuilder()
//                        .header("courierCode", CourierInfoManager.getInstance().getCourierCode())
//                        .header("token", CourierInfoManager.getInstance().getToken())
//                        .build();
//                Log.i("OKHttp","header");
//            }
            Response originalResponse = chain.proceed(request);
            if (ToolUtil.isNetworkAvailable(context)) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };


    public static GetApiInterface getInstance() {
        if (mGetApiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mGetApiInterface=retrofit.create(GetApiInterface.class);
        }
        return mGetApiInterface;
    }

    public static OkHttpClient getOkHttpClient(){
        //设置缓存路径
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        //设置缓存 10M
        Cache cache=null;
        try {
            cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        }catch (Exception e ){
            Log.e("OKHttp", "Could not create http cache", e);
            e.printStackTrace();
        }
        Log.e("OKHttp", "getOkHttpClient==========");
        //创建OkHttpClient，并添加拦截器和缓存代码
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Log.i("OKHttp","intercept");
                        Request original = chain.request();
                            Log.i("OKHttp","isNetworkAvailable intercept");
                          Request.Builder  requestBuilder = original.newBuilder()
                                    .header("token", "fuck you")
                                    .method(original.method(), original.body());
                            Log.i("OKHttp","no network");
                            Request request=requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .cache(cache).build();
        return client;
    }

    public interface GetApiInterface{
//        @GET("/dictionary.php?")
//        Call<ComBean> getData(@Query("w") String word, @Query("type") String type, @Query("auth") String auth);
////        @GET("http://dict-co.iciba.com/api/dictionary.php?w=hello&auth=7E9A41652BA3971C6BC4B5E05C00B630&type=json")
////        Call<ComBean> getWord(@Url());
//        @POST(Constants.getlxlist+Constants.constant)
//        Call<ResponseBody> test(@Body RequestBody json);
//
//
//        @GET(Constants.getlxlist+Constants.constant)
//        Observable<ComBean> test2(@Query("xsid") String xsid);
//
//        @POST(Constants.getlxlist+Constants.constant)
//        Observable<ResponseBody> test3(@Body RequestBody json);
//
//
//        @POST(Constants.getlxlist+Constants.constant)
//        Observable<ComBean> test4(@Body RequestBody json);
    }

}
