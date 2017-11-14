package ken.superlibrary.com.appdemo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.cleveroad.audiovisualization.AudioVisualization;
import com.cleveroad.audiovisualization.DbmHandler;
import com.cleveroad.audiovisualization.SpeechRecognizerDbmHandler;
import com.cleveroad.audiovisualization.VisualizerDbmHandler;

import ken.superlibrary.com.appdemo.R;


public class WaveActivity extends Activity {
    private AudioVisualization audioVisualization;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        audioVisualization= (AudioVisualization) findViewById(R.id.visualizer_view);
        SpeechRecognizerDbmHandler speechRecHandler = DbmHandler.Factory.newSpeechRecognizerHandler(this);
//        speechRecHandler.innerRecognitionListener(...);
        audioVisualization.linkTo(speechRecHandler);

        // set audio visualization handler. This will REPLACE previously set speech recognizer handler
        VisualizerDbmHandler vizualizerHandler = DbmHandler.Factory.newVisualizerHandler(this, 0);
        audioVisualization.linkTo(vizualizerHandler);
    }


    @Override
    public void onResume() {
        super.onResume();
        audioVisualization.onResume();
    }

    @Override
    public void onPause() {
        audioVisualization.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioVisualization.release();
    }
}
