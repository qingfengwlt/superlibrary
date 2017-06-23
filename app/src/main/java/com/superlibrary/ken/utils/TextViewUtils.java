package com.superlibrary.ken.utils;

import android.graphics.Paint;
import android.text.Layout;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by PC_WLT on 2017/3/31.
 */

public class TextViewUtils {

    /**
     * 判断TextView的内容宽度是否超出其可用宽度 单行
     * @param tv
     * @return
     */
    public static boolean isOverFlowedLine(EditText tv) {
        int availableWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight();
        Paint textViewPaint = tv.getPaint();
        float textWidth = textViewPaint.measureText(tv.getText().toString());
        if (textWidth > availableWidth) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isOverFlowed(TextView tv){
        Layout l=tv.getLayout();
        if (l!=null){
            int lines=l.getLineCount();
            if (lines>0){
                if (l.getEllipsisCount(lines-1)>0){
                    return true;
                }
            }
        }
        return false;
    }
}
