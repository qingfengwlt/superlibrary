package com.superlibrary.ken.Interface;

import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.media.TimedText;

/**
 * Created by PC_WLT on 2017/4/6.
 */

public interface OnMediaPlayerListener {
    void onStart(String url);
    boolean onError(MediaPlayer mp, int what, int extra);
    boolean onInfo(MediaPlayer mp, int what, int extra);
    void onPrepared(MediaPlayer mp);
    void onSeekComplete(MediaPlayer mp);
    void onBufferingUpdate(MediaPlayer mp, int percent);
    void onTimedText(MediaPlayer mp, TimedText text);
    void onTimedMetaDataAvailable(MediaPlayer mp, TimedMetaData data);
    void onVideoSizeChanged(MediaPlayer mp, int width, int height);
    void onCompletion(MediaPlayer mp);

}
