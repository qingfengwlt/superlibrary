package ken.superlibrary.com.appdemo.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * author jacksgong
 */
public class WordView extends android.support.v7.widget.AppCompatEditText {
    private final static String TAG = "WordView";
    private SpannableString mSpannableString;
    private String mSelectedWord;
    private OnWordSelectListener mOnWordSelectListener;
    private ForegroundColorSpan mForegroundColorSpan = new ForegroundColorSpan(Color.MAGENTA);
    private boolean mIsLongPressed=false;

    public WordView(Context context) {
        super(context);
        initialize();
    }

    public WordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public void setOnWordSelectListener(OnWordSelectListener listener) {
        mOnWordSelectListener = listener;
    }

    private void initialize() {
        setGravity(Gravity.TOP);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        //不做任何处理，为了阻止长按的时候弹出上下文菜单
    }

    @Override
    public boolean getDefaultEditable() {
        return false;
    }

    private float mLastX = -1f;
    private float mLastY = -1f;

    private final float MIN_VALID_MOVE = 3f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (mWords == null) {
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = -1f;
                mLastY = -1f;
                //检测是否长按,在非长按时检测
                if(!mIsLongPressed){
//                    mIsLongPressed = isLongPressed(mLastMotionX, mLastMotionY, x, y, lastDownTime,eventTime,500);
                }
                if(mIsLongPressed){
                    //长按模式所做的事
                }else{
                    //移动模式所做的事
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mSelectedWord = null;
                clearSpan();
                if (Math.abs(event.getX() - mLastX) > MIN_VALID_MOVE || Math.abs(event.getY() - mLastY) > MIN_VALID_MOVE) {
                    mLastX = event.getX();
                    mLastY = event.getY();
                    trySelectWord(event);
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastX = -1f;
                mLastY = -1f;
//                clearSelectedWord();
                break;
        }
        return true;
    }

    public void trySelectWord(MotionEvent event) {
        Layout layout = getLayout();
        if (layout == null) {
            return;
        }
        int line  = layout.getLineForVertical(getScrollY() + (int) event.getY());
        final int index = layout.getOffsetForHorizontal(line, (int) event.getX());
        Word selectedWord = getWord(index);

        if (selectedWord != null) {
            mSpannableString.setSpan(mForegroundColorSpan,
                    selectedWord.getStart(), selectedWord.getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(mSpannableString);
            mSelectedWord = getText().subSequence(selectedWord.getStart(), selectedWord.getEnd()).toString();
            mOnWordSelectListener.onWordSelect();
            mOnWordSelectListener.onWordSelect(mSelectedWord);
        }
    }

    public void clearSelectedWord() {
        clearSpan();
        setText(mSpannableString);
        showSelectedWord(mSelectedWord);
    }

    private void showSelectedWord(String selectedWord) {
        if (selectedWord != null) {
            Toast.makeText(getContext(), selectedWord,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void clearSpan() {
        ForegroundColorSpan[] spans = mSpannableString.getSpans(0, getText().length(), ForegroundColorSpan.class);
        for (int i = 0; i < spans.length; i++) {
            mSpannableString.removeSpan(spans[i]);
        }
    }

    private Word getWord(final int index) {
        if (mWords == null) {
            return null;
        }

        for (Word w : mWords) {
            if (w.isIn(index)) {
                return w;
            }
        }

        return null;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        mSpannableString = SpannableString.valueOf(getEditableText());
        mWords = getWords(text);
    }


    private List<Word> mWords;

    public List<Word> getWords(CharSequence s) {

        if (s == null) {
            return null;
        }

        List<Word> result = new ArrayList<Word>();

        int start = -1;

        int i = 0;

        for (; i < s.length(); i++) {
            char c = s.charAt(i);


            if (c == ' ' || !Character.isLetter(c)) {
                if (start != -1) {
                    result.add(new Word(start, i));// From ( 0, 4 )
                }
                start = -1;
            } else {
                if (start == -1) {
                    start = i;
                }
            }

        }

        if (start != -1) {
            result.add(new Word(start, i));
        }

        Log.d(TAG, result.toString());

        return result;

    }

    private class Word {
        public Word(final int start, final int end) {
            this.mStart = start;
            this.mEnd = end;
        }

        private int mStart;
        private int mEnd;

        public int getStart() {
            return this.mStart;
        }

        public int getEnd() {
            return this.mEnd;
        }

        public boolean isIn(final int index) {
            if (index >= getStart() && index <= getEnd()) {
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "( " + getStart() + ", " + getEnd() + " )";
        }
    }

    /**
     * * 判断是否有长按动作发生 * @param lastX 按下时X坐标 * @param lastY 按下时Y坐标 *
     *
     * @param thisX
     *            移动时X坐标 *
     * @param thisY
     *            移动时Y坐标 *
     * @param lastDownTime
     *            按下时间 *
     * @param thisEventTime
     *            移动时间 *
     * @param longPressTime
     *            判断长按时间的阀值
     */
    static boolean isLongPressed(float lastX, float lastY, float thisX,
                                 float thisY, long lastDownTime, long thisEventTime,
                                 long longPressTime) {
        float offsetX = Math.abs(thisX - lastX);
        float offsetY = Math.abs(thisY - lastY);
        long intervalTime = thisEventTime - lastDownTime;
        if (offsetX <= 10 && offsetY <= 10 && intervalTime >= longPressTime) {
            return true;
        }
        return false;
    }

    public interface OnWordSelectListener {
        public void onWordSelect();
        public void onWordSelect(CharSequence selectedWord);
    }
}

