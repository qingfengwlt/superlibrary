package com.superlibrary.ken.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by PC_WLT on 2017/3/28.
 */

public class MoveImageView extends ImageView {

    private int x, y;

    public MoveImageView(Context context) {
        this(context, null);
    }

    public MoveImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = (int) event.getX();
        y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                AnimatorSet setDown = new AnimatorSet();
                setDown.playTogether(
                        ObjectAnimator.ofFloat(this, "scaleX", 1f, 1.5f),
                        ObjectAnimator.ofFloat(this, "scaleY", 1f, 1.5f),
                        ObjectAnimator.ofFloat(this, "alpha", 1f, 0.5f)
                );
                setDown.start();
                break;
            case MotionEvent.ACTION_MOVE:
                //个人感觉跟手时，指尖在控件的中间比较好，所以减去宽高的一半
                setX(x + getLeft() + getTranslationX() - getWidth() / 2);
                setY(y + getTop() + getTranslationY() - getHeight() / 2);
                break;
            case MotionEvent.ACTION_UP:
                AnimatorSet setUp = new AnimatorSet();
                setUp.playTogether(
                        ObjectAnimator.ofFloat(this, "scaleX", 1.5f, 1f),
                        ObjectAnimator.ofFloat(this, "scaleY", 1.5f, 1f),
                        ObjectAnimator.ofFloat(this, "alpha", 0.5f, 1f)
                );
                setUp.start();
                break;
        }

        return true;
    }
}
