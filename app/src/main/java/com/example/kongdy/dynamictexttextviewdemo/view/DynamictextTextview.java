package com.example.kongdy.dynamictexttextviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * @author kongdy
 *         on 2016/6/15
 */
public class DynamictextTextview extends TextView {

    private List<String> needDrawText;
    private TextPaint mainPaint;

    public DynamictextTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DynamictextTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DynamictextTextview(Context context) {
        super(context);
        init();
    }

    public void init() {
        needDrawText = new ArrayList<>();
        mainPaint = new TextPaint();

        mainPaint.setTextSize(getTextSize());
        if(!TextUtils.isEmpty(getText())) {
            needDrawText.add((String) getText());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

    }
}
