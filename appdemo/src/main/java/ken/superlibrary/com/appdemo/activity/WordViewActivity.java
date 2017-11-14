package ken.superlibrary.com.appdemo.activity;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.superlibrary.ken.base.BaseAppCompatActivity;

import java.io.InputStream;

import butterknife.BindView;
import ken.superlibrary.com.appdemo.R;
import ken.superlibrary.com.appdemo.dictionary.model.Words;
import ken.superlibrary.com.appdemo.dictionary.util.HttpCallBackListener;
import ken.superlibrary.com.appdemo.dictionary.util.HttpUtil;
import ken.superlibrary.com.appdemo.dictionary.util.ParseXML;
import ken.superlibrary.com.appdemo.dictionary.util.WordsAction;
import ken.superlibrary.com.appdemo.dictionary.util.WordsHandler;
import ken.superlibrary.com.appdemo.widget.BasePopwindow;
import ken.superlibrary.com.appdemo.widget.WordView;


/***
 *  词典  文本捕捉 单词点击事件
 */
public class WordViewActivity extends BaseAppCompatActivity implements WordView.OnWordSelectListener {

    @BindView(R.id.wv_wordview_test)
    WordView mWordView;
    BasePopwindow mCustomPopWindow;

    private SearchView searchView;
    private TextView searchWords_key, searchWords_psE, searchWords_psA, searchWords_posAcceptation, searchWords_sent;
    private ImageButton searchWords_voiceE, searchWords_voiceA;
    private LinearLayout searchWords_posA_layout,searchWords_posE_layout, searchWords_linerLayout, searchWords_fatherLayout;
    private WordsAction wordsAction;
    private Words words = new Words();
    @Override
    protected int resId() {
        return R.layout.activity_word_view;
    }

    @Override
    protected void initData() {
        mWordView.setOnWordSelectListener(this);
        wordsAction = WordsAction.getInstance(this);
    }

    @Override
    public void onWordSelect() {
//        ToastUtils.showToast(this,mWordView.get);
    }

    @Override
    public void onWordSelect(CharSequence selectedWord) {
//        ToastUtils.showToast(this, (String) selectedWord);
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_word_search,null);
        //处理popWindow 显示内容

        handleLogic(contentView);
        loadWords((String) selectedWord);
        //创建并显示popWindow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mCustomPopWindow= new BasePopwindow(this);
            mCustomPopWindow .setContentView(contentView);
            mCustomPopWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0, 0);
        }
    }


    private void handleLogic(View contentView) {

    searchWords_linerLayout =(LinearLayout) contentView.findViewById(R.id.searchWords_linerLayout);
    searchWords_posA_layout = (LinearLayout)contentView.findViewById(R.id.searchWords_posA_layout);
    searchWords_posE_layout = (LinearLayout) contentView.findViewById(R.id.searchWords_posE_layout);
//    searchWords_fatherLayout = (LinearLayout) contentView.findViewById(R.id.searchWords_fatherLayout);
//        searchWords_fatherLayout.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            //点击输入框外实现软键盘隐藏
//            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
//        }
//    });
    searchWords_key = (TextView)contentView. findViewById(R.id.searchWords_key);
    searchWords_psE = (TextView) contentView.findViewById(R.id.searchWords_psE);
    searchWords_psA = (TextView) contentView.findViewById(R.id.searchWords_psA);
    searchWords_posAcceptation = (TextView) contentView.findViewById(R.id.searchWords_posAcceptation);
    searchWords_sent = (TextView)contentView. findViewById(R.id.searchWords_sent);
    searchWords_voiceE = (ImageButton)contentView. findViewById(R.id.searchWords_voiceE);
        searchWords_voiceE.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            wordsAction.playMP3(words.getKey(), "E", WordViewActivity.this);
        }
    });
    searchWords_voiceA = (ImageButton)contentView. findViewById(R.id.searchWords_voiceA);
        searchWords_voiceA.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            wordsAction.playMP3(words.getKey(), "A", WordViewActivity.this);
        }
    });
//    searchView = (SearchView)contentView. findViewById(R.id.searchWords_searchView);
//        searchView.setSubmitButtonEnabled(true);//设置显示搜索按钮
//        searchView.setIconifiedByDefault(false);//设置不自动缩小为图标
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//        @Override
//        public boolean onQueryTextSubmit(String query) {
//            loadWords(query);
//            return true;
//        }
//
//        @Override
//        public boolean onQueryTextChange(String newText) {
//            return false;
//        }
//    });
    }

    /**
     * 读取words的方法，优先从数据中搜索，没有在通过网络搜索
     */
    public void loadWords(String key) {
        words = wordsAction.getWordsFromSQLite(key);
        if ("" == words.getKey()) {
            String address = wordsAction.getAddressForWords(key);
            Log.d(TAG,address);
            HttpUtil.sentHttpRequest(address, new HttpCallBackListener() {
                @Override
                public void onFinish(InputStream inputStream) {
                    WordsHandler wordsHandler = new WordsHandler();
                    ParseXML.parse(wordsHandler, inputStream);
                    words = wordsHandler.getWords();
                    wordsAction.saveWords(words);
                    wordsAction.saveWordsMP3(words);
                    handler.sendEmptyMessage(111);
                }

                @Override
                public void onError() {

                }
            });
        } else {
            upDateView();
        }
    }

    private void upDateView() {
        {
            if (words.getIsChinese()) {
                searchWords_posAcceptation.setText(words.getFy());
                searchWords_posA_layout.setVisibility(View.GONE);
                searchWords_posE_layout.setVisibility(View.GONE);
            } else {
                searchWords_posAcceptation.setText(words.getPosAcceptation());
                if(words.getPsE()!="") {
                    searchWords_psE.setText(String.format(getResources().getString(R.string.psE), words.getPsE()));
                    searchWords_posE_layout.setVisibility(View.VISIBLE);
                }else {
                    searchWords_posE_layout.setVisibility(View.GONE);
                }
                if(words.getPsA()!="") {
                    searchWords_psA.setText(String.format(getResources().getString(R.string.psA), words.getPsA()));
                    searchWords_posA_layout.setVisibility(View.VISIBLE);
                }else {
                    searchWords_posA_layout.setVisibility(View.GONE);
                }
            }
            searchWords_key.setText(words.getKey());
            searchWords_sent.setText(words.getSent());
            searchWords_linerLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 网络查词完成后回调handleMessage方法
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 111:
                    //判断网络查找不到该词的情况
                    if (words.getSent().length() > 0) {
                        upDateView();
                    } else {
                        searchWords_linerLayout.setVisibility(View.GONE);
                        Toast.makeText(WordViewActivity.this, "抱歉！找不到该词！", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("测试", "tv保存2");
            }
        }
    };

}
