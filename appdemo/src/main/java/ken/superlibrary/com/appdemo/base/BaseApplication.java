package ken.superlibrary.com.appdemo.base;

import android.app.Application;
import android.content.Context;
import android.os.HandlerThread;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.superlibrary.ken.utils.L;


/**
 * Created by PC_WLT on 2017/4/26.
 */

public class BaseApplication extends Application {

    public static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        //讯飞语音
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=58636b76");
//        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                //其他配置
//                .build();
//        OkHttpUtils.initClient(okHttpClient);
        HandlerThread workerThread = new HandlerThread("global_worker_thread");
        workerThread.start();
        initImageLoader(this);

    }
    public static void initImageLoader(Context context) {
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = null;
            if (L.isDebug) {
                config = new ImageLoaderConfiguration.Builder(context)
						/*.threadPriority(Thread.NORM_PRIORITY - 2)
						.memoryCacheSize((int) (Runtime.getRuntime().maxMemory() / 4))
						.diskCacheSize(500 * 1024 * 1024)
						.writeDebugLogs()
						.diskCacheFileNameGenerator(new Md5FileNameGenerator())
						.tasksProcessingOrder(QueueProcessingType.LIFO).build();*/

                        //.memoryCacheExtraOptions(200, 200)
                        //.diskCacheExtraOptions(200, 200, null).threadPoolSize(3)
                        .threadPriority(Thread.NORM_PRIORITY - 1)
                        .tasksProcessingOrder(QueueProcessingType.LIFO)
                        //.denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(2 * 1024 * 1024))
						/*.memoryCacheSize(20 * 1024 * 1024)*/
                        .memoryCacheSizePercentage(13)
                        .diskCacheSize(500 * 1024 * 1024)
                        //.imageDownloader(new BaseImageDownloader(A3App.getInstance().getApplicationContext()))
                        //.imageDecoder(new BaseImageDecoder(true))
                        //.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                        //.writeDebugLogs()
                        .build();
            } else {
                config = new ImageLoaderConfiguration.Builder(context)
                        .threadPriority(Thread.NORM_PRIORITY - 2)
                        .diskCacheSize(500 * 1024 * 1024)
                        .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                        .tasksProcessingOrder(QueueProcessingType.LIFO).build();
            }
            ImageLoader.getInstance().init(config);
        }
    }
}
