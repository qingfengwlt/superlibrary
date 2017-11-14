package ken.superlibrary.com.appdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;
import com.superlibrary.ken.base.BaseAppCompatActivity;
import com.superlibrary.ken.utils.L;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import ken.superlibrary.com.appdemo.R;
import ken.superlibrary.com.appdemo.result.Result;
import ken.superlibrary.com.appdemo.result.xml.XmlResultParser;
import ken.superlibrary.com.appdemo.utils.RecorderUtil;
import ken.superlibrary.com.appdemo.utils.ToastUtils;

/**
 * 语音测评 打分页面
 */

public class SpeechEvaluatorActivity extends BaseAppCompatActivity implements EvaluatorListener {

    //TODO 语音评测对象
    private SpeechEvaluator mSpeechEvaluator;
    //Todo 学生音频录音地址
    private String xslyPath;
    //Todo 当前时间
    private long currentTime;

    @BindView(R.id.edt_speech_evaluator)
    EditText mEdtInputWord;

    @BindView(R.id.btn_speech_evaluator)
    Button mBtnRecord;

    @BindView(R.id.tv_speech_evaluator_content)
    TextView mTvSpeechWord;

    @BindView(R.id.btn_speech_evaluator_stop)
    Button mBtnStopRecord;

    String text = "It's important to believe in yourself.Believe that you can do it under any circumstances,because if you believe you can,then you really will.The belief keeps you searching for answers,which means that pretty soon you will get them.";

    @Override
    protected int resId() {
        return R.layout.activity_speech_evaluator;
    }

    @Override
    protected void initData() {
//        mBtnRecord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String word=mEdtInputWord.getText().toString().trim();
//                if (!TextUtils.isEmpty(word)){
//                    recording(word);
//                }
//            }
//        });
        RecorderUtil.recordingPermissions(this);
        mEdtInputWord.setText(text);
    }

    @OnClick({R.id.btn_speech_evaluator, R.id.btn_speech_evaluator_stop})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_speech_evaluator:
                L.d(TAG, "onclick");
                String word = mEdtInputWord.getText().toString().trim();
                if (!TextUtils.isEmpty(word)) {
                    recording(word);
                }
                break;
            case R.id.btn_speech_evaluator_stop:
                if (mSpeechEvaluator != null) {
                    if (mSpeechEvaluator.isEvaluating()) {
                        mSpeechEvaluator.stopEvaluating();
                    }
                }
                break;
        }

    }

    private void initXunFeiCePingParams(Context context, String playerTime) {
        mSpeechEvaluator = SpeechEvaluator.createEvaluator(context, null);
        mSpeechEvaluator.setParameter(SpeechConstant.LANGUAGE, "en_us");//英语
        // 设置评测题型
        mSpeechEvaluator.setParameter(SpeechConstant.ISE_CATEGORY, "read_sentence");//句子
//        mSpeechEvaluator.setParameter(SpeechConstant.ISE_CATEGORY, "read_word");//词语
        // 设置试题编码类型
        mSpeechEvaluator.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        // 设置前、后端点超时
        //// 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mSpeechEvaluator.setParameter(SpeechConstant.VAD_BOS, "10000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mSpeechEvaluator.setParameter(SpeechConstant.VAD_EOS, "10000");
        // 设置录音超时，设置成-1则无超时限制  // 语音输入超时时间，即用户最多可以连续说多长时间；
        mSpeechEvaluator.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, playerTime);
        // 设置结果等级，不同等级对应不同的详细程度
        mSpeechEvaluator.setParameter(SpeechConstant.RESULT_LEVEL, "complete");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mSpeechEvaluator.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//        mSpeechEvaluator.setParameter(SpeechConstant.AUDIO_FORMAT, "aac");

        //Todo 唯一地址(讯飞语音地址)
        currentTime = System.currentTimeMillis();
        xslyPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AudioRecord/" + currentTime + ".wav";
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AudioRecord/");
        if (!file.exists()) {
            file.mkdirs();
        }
        mSpeechEvaluator.setParameter(SpeechConstant.ISE_AUDIO_PATH, xslyPath);
    }


    public void recording(String xfword) {
        if (mSpeechEvaluator != null && mSpeechEvaluator.isEvaluating()) {
            stopRecording();
        } else {
            initXunFeiCePingParams(this, "-1");
            mSpeechEvaluator.startEvaluating(xfword, null, this);
//            handler.sendEmptyMessageDelayed(0,10000);
        }
    }

    @Override
    public void onVolumeChanged(int i, byte[] bytes) {

    }

    @Override
    public void onBeginOfSpeech() {
        mBtnRecord.setText("录音中");
    }

    @Override
    public void onEndOfSpeech() {
        mBtnRecord.setText("录音");
    }

    int score = 0;

    // 结果回调，评测过程中可能会多次调用该方法，isLast为true则为最后结果
    @Override
    public void onResult(EvaluatorResult result, boolean isLast) {
        if (isLast) {
            L.d(TAG, "onResult" + result.getResultString());
            L.d(TAG, "onResult" + result.describeContents());
            XmlResultParser resultParser = new XmlResultParser();
            Result resultScore = resultParser.parse(result.getResultString());
//        L.d(TAG,"onResult"+resultScore.toString());
            if (resultScore.is_rejected) {//检测到乱读
                score = 0;
            } else {
                score = (int) (resultScore.total_score * 20);
            }

            colorWordMethod2(resultScore);
//            colorWordMethod(resultScore);
            mBtnRecord.setText("开始录音");

        }
    }

    private int lastIndex = -1;
    private String lastWord = "";

    private void colorWordMethod2(Result resultScore) {
        Map<String, Integer> wordMap = new HashMap<>();
        String text = mEdtInputWord.getText().toString();
//        text=text.replaceAll("\\p{Punct}", " ");
        SpannableString spannableString = new SpannableString(text);
        if (resultScore.sentences.size() > 0) {
            for (int i = 0; i < resultScore.sentences.size(); i++) {
                for (int j = 0; j < resultScore.sentences.get(i).words.size(); j++) {
                    int wordScore = score = (int) (resultScore.sentences.get(i).words.get(j).total_score * 20);
                    String word = resultScore.sentences.get(i).words.get(j).content;
                    if (text.toLowerCase().contains(word.toLowerCase())) {

                        int index = 0;
                        L.e(TAG, "lastIndex=" + lastIndex);
                        L.e(TAG, "index=" + index);
                        if (wordMap.containsKey(word)) {
                            index = text.toLowerCase().indexOf(word.toLowerCase(), lastIndex);
                            wordMap.put(word.toLowerCase(), wordMap.get(word) + 1);
                            if (index > 0) {
                                if (wordScore > 60) {
                                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mEdtInputWord.getContext(), R.color.green)), index, index + word.length(),
                                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                } else if (wordScore > 80) {
                                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mEdtInputWord.getContext(), R.color.blue)), index, index + word.length(),
                                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                } else {
                                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mEdtInputWord.getContext(), R.color.red)), index, index + word.length(),
                                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                }
                            }
                            lastIndex = index+word.length();
                        } else {
                            index = text.toLowerCase().indexOf(word.toLowerCase());
                            wordMap.put(word.toLowerCase(), 1);
                            if (index >=
                                    0) {
                                if (wordScore > 60) {
                                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mEdtInputWord.getContext(), R.color.green)), index, index + word.length(),
                                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                } else if (wordScore > 80) {
                                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mEdtInputWord.getContext(), R.color.blue)), index, index + word.length(),
                                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                } else {
                                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mEdtInputWord.getContext(), R.color.red)), index, index + word.length(),
                                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                }
                            }
                            lastIndex =  index+word.length();
                        }
                        lastWord = word;
                    }
                }
            }
        }
        lastIndex = -1;
        wordMap.clear();
        mEdtInputWord.setText(spannableString);
        ToastUtils.showToast(SpeechEvaluatorActivity.this, "score=" + score);
    }

    /***
     * 根据分数，对文本单词着色
     * @param resultScore
     */
    private void colorWordMethod(Result resultScore) {
        SpannableStringBuilder builder = new SpannableStringBuilder(mEdtInputWord.getText().toString());
        String exChange = "";
        if (resultScore.sentences.size() > 0) {
            for (int i = 0; i < resultScore.sentences.size(); i++) {
                for (int j = 0; j < resultScore.sentences.get(i).words.size(); j++) {
                    int wordScore = score = (int) (resultScore.sentences.get(i).words.get(j).total_score * 20);
                    String word = resultScore.sentences.get(i).words.get(j).content;
                    if (wordScore > 60) {
                        exChange = exChange + " <font color='#0000ff'>" + word +
                                "</font>";
                    } else if (wordScore > 80) {
                        exChange = exChange + " <font color='#00ff00'>" + word +
                                "</font>";
                    } else {
                        exChange = exChange + " <font color='#ff0000'>" + word +
                                "</font>";
                    }
                }
                exChange = exChange + "\n";
            }
        }
        exChange = exChange.replace("sil", "");
        exChange = exChange.replace("fil", "");
        Spanned spanned = Html.fromHtml("test:" + exChange);

        mTvSpeechWord.setText(spanned);
        ToastUtils.showToast(SpeechEvaluatorActivity.this, "score=" + score);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            stopRecording();
        }
    };

    @Override
    public void onError(SpeechError error) {

        if (error != null) {
            switch (error.getErrorCode()) {
                case 11401:
                    Log.e("ExamineAdapter::::", "ERROR:" + "无语音或音量小");
                    break;
                case 11402:
                    Log.e("ExamineAdapter::::", "ERROR:" + "信噪比低或有效语音过短");
                case 11405:
                    Log.e("ExamineAdapter::::", "ERROR:" + "录音格式有误");
                    break;
                case 11406:
                    Log.e("ExamineAdapter::::", "ERROR:" + "其他评测数据异常， 包括错读、 漏读、\n" +
                            "恶意录入、试卷内容等错误");
                    break;
                case 11408:
                    Log.e("ExamineAdapter::::", "ERROR:" + "存在未登录词， 即引擎中没有该词语\n" +
                            "的信息");
                    break;
            }
        }
        L.e("YYYYYYY", "SpeechError error :" + error);
    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {

    }

    /**
     * 停止录音
     */
    private void stopRecording() {
        if (null != mSpeechEvaluator) {
            if (mSpeechEvaluator.isEvaluating()) {
                mSpeechEvaluator.stopEvaluating();
            }
            mSpeechEvaluator.destroy();
            mSpeechEvaluator = null;
        }
    }
}
