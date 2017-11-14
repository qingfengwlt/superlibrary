package ken.superlibrary.com.appdemo.activity;

import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.superlibrary.ken.base.BaseAppCompatActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import ken.superlibrary.com.appdemo.R;

/**
 * 系统录音
 */
public class SpeechActivity extends BaseAppCompatActivity implements TextToSpeech.OnInitListener {

 @BindView(R.id.btn_speech_test)
 Button mBtnSpeech;
 @BindView(R.id.edt_speech_test)
 EditText mEdtSpeech;

 private TextToSpeech mTextToSpeech;

 @Override
 protected int resId() {
  return R.layout.activity_speech;
 }

 @Override
 protected void initData() {
  mTextToSpeech = new TextToSpeech(this, this);
  mBtnSpeech.setEnabled(false);
 }

 @OnClick(R.id.btn_speech_test)
 public void onClick(View v) {
  mTextToSpeech.speak(mEdtSpeech.getText().toString(),
          TextToSpeech.QUEUE_FLUSH, null);
 }

 @Override
 public void onInit(int status) {
  if (status == TextToSpeech.SUCCESS) {/**如果装载TTS成功*/
   int result = mTextToSpeech.setLanguage(Locale.ENGLISH);/**有Locale.CHINESE,但是不支持中文*/
   if (result == TextToSpeech.LANG_MISSING_DATA/**表示语言的数据丢失。*/
           || result == TextToSpeech.LANG_NOT_SUPPORTED) {/**语言不支持*/
    Toast.makeText(SpeechActivity.this, "我说不出口", Toast.LENGTH_SHORT).show();
   } else {
    mBtnSpeech.setEnabled(true);
    mTextToSpeech.setPitch(1.0f);
    mTextToSpeech.speak("I miss you", TextToSpeech.QUEUE_FLUSH,
            null);
   }
  }
 }

 @Override
 protected void onDestroy() {
  if (mTextToSpeech != null) {
   mTextToSpeech.stop();
   mTextToSpeech.shutdown();
  }
  super.onDestroy();

 }
}