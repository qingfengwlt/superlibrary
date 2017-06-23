package com.superlibrary.ken.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.media.TimedText;
import android.os.Build;

import com.superlibrary.ken.Interface.OnMediaPlayerListener;


/**
 * Created by PC_WLT on 2017/4/6.
 */

@TargetApi(Build.VERSION_CODES.M)
public class MMediaPlayer implements MediaPlayer.OnErrorListener ,MediaPlayer.OnInfoListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener,MediaPlayer.OnSeekCompleteListener,MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnTimedTextListener,MediaPlayer.OnTimedMetaDataAvailableListener,MediaPlayer.OnVideoSizeChangedListener{

    private Context mContext;
    private MediaPlayer mMediaPlayer=new MediaPlayer();
    OnMediaPlayerListener mOnMediaPlayerListener;
    protected boolean mMediaPlayerIsPrepared;

    public MMediaPlayer(Context context) {
        this.mContext=context;
    }
    public MMediaPlayer(Context context,OnMediaPlayerListener onMediaPlayerListener) {
        this.mContext=context;
        this.mOnMediaPlayerListener=onMediaPlayerListener;
    }

    void init(String url){
        if(mMediaPlayer!=null){
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(url);
                mMediaPlayer.setLooping(false);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setOnErrorListener(this);
                mMediaPlayer.setOnPreparedListener(this);
                mMediaPlayer.setOnCompletionListener(this);
                mMediaPlayer.setOnTimedMetaDataAvailableListener(this);
                mMediaPlayer.setOnTimedTextListener(this);
                mMediaPlayer.setOnSeekCompleteListener(this);
                mMediaPlayer.setOnBufferingUpdateListener(this);
                mMediaPlayer.setOnVideoSizeChangedListener(this);
                mMediaPlayer.prepareAsync();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
//        mMediaPlayer =MediaPlayer.create(mContext,Uri.parse(url));

    }

    public void playUrl(String url){
        if (mOnMediaPlayerListener!=null){
            mOnMediaPlayerListener.onStart(url);
        }
        if (mMediaPlayer!=null){
            stop();
        }
        init(url);
    }
    public void stop(){
        if (mMediaPlayer!=null){
            mMediaPlayer.stop();
            try {
                mMediaPlayer.prepare();
                mMediaPlayer.seekTo(0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void destroy(){
        if (mMediaPlayer!=null){
            stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }
    public void pause(){
        if (isPlaying()){
            mMediaPlayer.pause();
        }
    }

    public void resume(){
        if (mMediaPlayer!=null){
            mMediaPlayer.start();
        }
    }

    public boolean isPlaying(){
        if (mMediaPlayer!=null){
            return mMediaPlayer.isPlaying();
        }
        return false;
    }
    /**
     * 设置监听
     * @param mOnMediaPlayerListener
     */
    public void setmOnMediaPlayerListener(OnMediaPlayerListener mOnMediaPlayerListener) {
        this.mOnMediaPlayerListener = mOnMediaPlayerListener;
    }


    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (mOnMediaPlayerListener!=null){
            mOnMediaPlayerListener.onBufferingUpdate(mp,percent);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mOnMediaPlayerListener!=null){
            mOnMediaPlayerListener.onCompletion(mp);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (mOnMediaPlayerListener!=null){
          return mOnMediaPlayerListener.onError(mp,what,extra);
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        if (mOnMediaPlayerListener!=null){
            return mOnMediaPlayerListener.onInfo(mp,what,extra);
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mOnMediaPlayerListener!=null){
            mOnMediaPlayerListener.onPrepared(mp);
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        if (mOnMediaPlayerListener!=null){
            mOnMediaPlayerListener.onSeekComplete(mp);
        }
    }

    @Override
    public void onTimedMetaDataAvailable(MediaPlayer mp, TimedMetaData data) {
        if (mOnMediaPlayerListener!=null){
            mOnMediaPlayerListener.onTimedMetaDataAvailable(mp,data);
        }
    }

    @Override
    public void onTimedText(MediaPlayer mp, TimedText text) {
        if (mOnMediaPlayerListener!=null){
            mOnMediaPlayerListener.onTimedText(mp,text);
        }
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        if (mOnMediaPlayerListener!=null){
            mOnMediaPlayerListener.onVideoSizeChanged(mp,width,height);
        }
    }
}
