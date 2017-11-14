package ken.superlibrary.com.appdemo.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by PCWLT on 2017/3/17.
 * 解决吐司重复点击重复出现的不友好交互效果
 */

public class ToastUtils {

    private static String mOldMsg ;
    private static Toast toast = null ;
    private static long oneTime = 0 ;
    private static long twoTime = 0 ;

    public static void showToast(Context context,String msg){
        if(toast == null){
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.show() ;
            oneTime = System.currentTimeMillis() ;
        }else{
            twoTime = System.currentTimeMillis() ;
            if(msg.equals(mOldMsg)){
                if(twoTime - oneTime > Toast.LENGTH_SHORT){
                    toast.show() ;
                }
            }else{
                mOldMsg = msg ;
                toast.setText(msg) ;
                toast.show() ;
            }
        }
        oneTime = twoTime ;
    }

}
