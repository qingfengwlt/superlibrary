package ken.superlibrary.com.appdemo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by YuLiang on 2017/1/19.
 */

public class RecorderUtil {

    private static final String TAG = "RecorderUtil";

    public static String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/AudioRecord/";

    private String mFileName = null;
    private MediaRecorder mRecorder = null;
    private long startTime;
    private long timeInterval;
    private boolean isRecording;


    private String tengxunPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/AudioRecord/"+ System.currentTimeMillis()+".mp3";

    public void setSavePath(Context context, String path){
        if (TextUtils.isEmpty(path)){
            Toast.makeText(context,"地址为空", Toast.LENGTH_SHORT).show();
        }else {
            mFileName=path;
        }
    }

    public RecorderUtil(){
        File file=new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
    }


    /**
     * 开始录音
     */
    public void startRecording() {

        if (!EnvironmentShare.haveSdCard()){
            //弹出框
            return;
        }

        if (isRecording){
            mRecorder.release();
            mRecorder = null;
        }
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);

//        mFileName=System.currentTimeMillis()+mFileName;
        mRecorder.setOutputFile(mFileName);
//        mRecorder.setOutputFile(tengxunPath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        startTime = System.currentTimeMillis();
        try {
            mRecorder.prepare();
            mRecorder.start();
            isRecording = true;
        } catch (Exception e){
            Log.e(TAG, "prepare() failed");
        }

    }


    /**
     * 停止录音
     */
    public void stopRecording() {

        timeInterval = System.currentTimeMillis() - startTime;
        try{
//            if (timeInterval>1000){
//                mRecorder.stop();
//            }
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            isRecording =false;
        }catch (Exception e){
            Log.e(TAG, "release() failed");
        }

    }

    /**
     * 取消语音
     */
    public synchronized void cancelRecording() {

        if (mRecorder != null) {
            try {
                mRecorder.release();
                mRecorder = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            File file = new File(mFileName);
            file.deleteOnExit();
        }

        isRecording =false;
    }

    /**
     * 获取录音文件
     */
    public byte[] getDate() {
        if (mFileName == null) return null;
        try{
            return readFile(new File(mFileName));
        }catch (IOException e){
            Log.e(TAG, "read file error" + e);
            return null;
        }
    }

    /**
     * 获取录音文件地址
     */
    public String getFilePath(){
        return mFileName;
    }


    /**
     * 获取录音时长,单位秒
     */
    public long getTimeInterval() {
        return timeInterval/1000;
    }


    /**
     * 将文件转化为byte[]
     *
     * @param file 输入文件
     */
    private static byte[] readFile(File file) throws IOException {
        // Open file
        RandomAccessFile f = new RandomAccessFile(file, "r");
        try {
            // Get and check length
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength)
                throw new IOException("File size >= 2 GB");
            // Read file and return data
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        } finally {
            f.close();
        }
    }

    /**
     * 录音的权限
     * @param activity
     */
    public static boolean recordingPermissions(Activity activity) {
        // 话筒权限
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity,"请添加话筒权限", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions( activity,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
            return false;
        }else {
            return true;
        }
    }

}

