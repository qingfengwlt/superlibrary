package ken.superlibrary.com.appdemo.activity;

import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.superlibrary.ken.base.BaseAppCompatActivity;
import com.superlibrary.ken.utils.L;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import ken.superlibrary.com.appdemo.R;


//对文本单词着色
public class WordColorDrawingActivity extends BaseAppCompatActivity {


    @BindView(R.id.tv_word_color_Drawing)
    TextView mTv;

    private int lastIndex;
    @Override
    protected int resId() {
        return R.layout.activity_word_color_drawing;
    }

    @Override
    protected void initData() {

        Map<String,Integer> wordMap=new HashMap<>();

        String lastWord="";
        String text=mTv.getText().toString().trim();

        SpannableString spannableString=new SpannableString(text);
        text=text.replaceAll("\\p{Punct}", " ");
        String[] strings=text.split(" ");
        for (int i=0;i<strings.length;i++){
            String word=strings[i].trim();
            if (wordMap.containsKey(word)){//已经存在的单词

               int count=wordMap.get(word);
//                if (count==1){
//                    int index=text.indexOf(word,0);
//                    int index1=text.indexOf(word,index+word.length());
//
//                }else if (count==2){
//                    int index=text.indexOf(word);
//                    int index1=text.indexOf(word,index+word.length());
//                }else if (count==3){
//                    int index=text.indexOf(word);
//                    int index1=text.indexOf(word,index+word.length());
//                    int index2=text.indexOf(word,index1+word.length());
//                }else if (count==4){
//                    int index=text.indexOf(word);
//                    int index1=text.indexOf(word,index+word.length());
//                    int index2=text.indexOf(word,index1+word.length());
//                    int index3=text.indexOf(word,index2+word.length());
//                }else if(count==5){
//                    int index=text.indexOf(word);
//                    int index1=text.indexOf(word,index+word.length());
//                    int index2=text.indexOf(word,index1+word.length());
//                    int index3=text.indexOf(word,index2+word.length());
//                    int index4=text.indexOf(word,index3+word.length());
//                }
//                for (int j=0;j< count;j++){
//                    int indexj=text.in
//                }
                    int index=text.indexOf(word,lastIndex+word.length());
                if (word.compareTo(lastWord)==0){

                }else {

                }
                    wordMap.put(word,wordMap.get(word)+1);
                    Log.d(TAG,word+" :"+index+"--");
                    Log.d(TAG,word+" count:"+wordMap.get(word)+"--");
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mTv.getContext(), R.color.colorAccent)), index, index+word.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//                lastIndex=index1;
                    lastIndex=index;

                    lastWord=word;

                    L.e("lastIndex="+lastIndex+"--");
                    L.e("lastWord="+lastWord+"--");

            }else {//不存在的单词
                int index=text.indexOf(word);
                Log.d(TAG,word+":"+index);
                wordMap.put(word,1);
                Log.d(TAG,word+" count:"+wordMap.get(word));
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mTv.getContext(), R.color.colorPrimaryDark)), index, index+word.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                lastIndex=index;
                L.e("lastIndex="+lastIndex);
            }

        }
        mTv.setText(spannableString);

//        if (text.contains("you")){
//            SpannableString spannableString=new SpannableString(text);
//            int index=text.indexOf("you");
//            int index1=text.indexOf("you",1);
//            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mTv.getContext(), R.color.colorPrimaryDark)), index, index+3,
//                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mTv.getContext(), R.color.colorAccent)), index1, index1+3,
//                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            mTv.setText(spannableString);
//        }


    }
}
