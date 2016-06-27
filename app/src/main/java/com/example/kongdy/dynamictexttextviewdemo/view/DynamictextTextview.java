package com.example.kongdy.dynamictexttextviewdemo.view;

import android.content.Context;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.widget.TextView;



/**
 * 可自由设置文本的样式的textView
 * @author kongdy
 *         on 2016/6/15
 */
public class DynamictextTextview extends TextView {

    private SpannableString textStyle;
    private boolean isDotSmall;
    private int smallSize = -1;
    private float dotTextScale;
    /** 设置的再此字符之后变化的字符 */
    private CharSequence changedChar;
    private boolean isAfter = true;

    private final CharSequence dotChar = ".";

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
        CharSequence text = getText();
        textStyle = new SpannableString(text);
        textStyle.setSpan(new AbsoluteSizeSpan((int)getTextSize()),0,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        refreshText();
    }

    /**
     * 在后面增加一个自己定义大小的文本
     * @param text 文本
     * @param size 文字大小
     */
    public void addCustomeSizeText(String text,int size) {
        String newText = getText()+text;
        CharSequence oldText = getText();
        textStyle = new SpannableString(newText);
        textStyle.setSpan(new AbsoluteSizeSpan(size),oldText.length(),newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        refreshText();
    }

    /**
     * 增加一个自定义大小和颜色的文本
     * @param text 文本
     * @param size 文字大小
     * @param color 文字颜色
     */
    public void addCustomeSizeAndColorText(String text,int size,int color) {
        final String newText = getText()+text;
        final CharSequence oldText = getText();
        textStyle = new SpannableString(newText);
        addCustomeSizeText(text,size);
        textStyle.setSpan(new ForegroundColorSpan(color),oldText.length(),newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        refreshText();
    }

    /**
     * 获取当前文字配置
     * @return
     */
    public SpannableString getTextStyle(){
        return textStyle;
    }

    public void setTextStyle(SpannableString textStyle) {
        this.textStyle = textStyle;
    }


    /**
     * 设置参数
     * @param dynamictextParams
     */
    public void addDynamictextParams(DynamictextParams dynamictextParams) {
        if(dynamictextParams != null && !TextUtils.isEmpty(dynamictextParams.text)) {
            String newText = getText()+dynamictextParams.text;
            CharSequence oldText = getText();
            textStyle = new SpannableString(newText);
            if(dynamictextParams.size == -1){
                textStyle.setSpan(new AbsoluteSizeSpan((int)getTextSize()),oldText.length(),newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(dynamictextParams.foregroundColor != -1) {
                textStyle.setSpan(new ForegroundColorSpan(dynamictextParams.foregroundColor),oldText.length(),newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(dynamictextParams.backgrounColor != -1){
                textStyle.setSpan(new ForegroundColorSpan(dynamictextParams.backgrounColor),oldText.length(),newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(dynamictextParams.typeface != null) {
                textStyle.setSpan(dynamictextParams.typeface,oldText.length(),newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(dynamictextParams.isUnderLine) {
                textStyle.setSpan(new UnderlineSpan(),oldText.length(),newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(dynamictextParams.urlSpan != null) {
                textStyle.setSpan(dynamictextParams.urlSpan,oldText.length(),newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            refreshText();
        }
    }

    public void refreshText() {
        if(isDotSmall) {
            CharSequence text = getText();
            SpannableString tempStyle = new SpannableString(text);
            int dotPos = compareContainCharsequence(text, changedChar, isAfter);
            if(dotPos != -1) {
                if(smallSize != -1) {
                    tempStyle.setSpan(new AbsoluteSizeSpan(smallSize), dotPos+1, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    tempStyle.setSpan(new AbsoluteSizeSpan((int) (getTextSize()*dotTextScale)), dotPos+1, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            this.setText(tempStyle, BufferType.SPANNABLE);
        } else {
            this.setText(textStyle, BufferType.SPANNABLE);
        }
        invalidate();
    }

    public void refreshText(CharSequence text) {
        SpannableString tempStyle = new SpannableString(text);
        if(isDotSmall) {
            int dotPos = compareContainCharsequence(text, changedChar, isAfter);
            if(dotPos != -1) {
                if(smallSize != -1) {
                    tempStyle.setSpan(new AbsoluteSizeSpan(smallSize), dotPos+1, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    tempStyle.setSpan(new AbsoluteSizeSpan((int) (getTextSize()*dotTextScale)), dotPos+1, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        this.setText(tempStyle, BufferType.SPANNABLE);
        invalidate();
    }

    /**
     * 将第一个小数点之后的字符按给予的大小重新设定
     * @param size
     */
    public void setDotSmall(int size) {
        isDotSmall = true;
        smallSize = size;
        changedChar = dotChar;
        refreshText();
    }

    /**
     * 将第一个小数点之后的字符按前面字符一半的大小重新设定
     */
    public void setDotSmall() {
        changedChar = dotChar;
        isDotSmall = true;
        smallSize = -1;
        refreshText();
    }

    /**
     * 将第一个小数点之后的字符按前面字符大小的sacle比例重新设定
     * @param scale
     */
    public void setDotSmall(float scale) {
        changedChar = dotChar;
        isDotSmall = true;
        smallSize = -1;
        dotTextScale = scale;
        refreshText();
    }

    /**
     * 设置是否在此字符产生大小变化
     * @param mChar 关键字符
     * @param isAfter 是否在该字符之后才产生变化
     * @param scale 放大缩小比例
     */
    public void setSmallCustomeChar(CharSequence mChar,boolean isAfter,float scale) {
        changedChar = mChar;
        this.isAfter = isAfter;
        smallSize = -1;
        dotTextScale = scale;
        isDotSmall = true;
        refreshText();
    }

    /**
     * 设置是否在此字符产生大小变化
     * @param mChar 关键字符
     * @param isAfter 是否在该字符之后才产生变化
     * @param size 改变的大小
     */
    public void setSmallCustomeChar(CharSequence mChar,boolean isAfter,int size) {
        changedChar = mChar;
        this.isAfter = isAfter;
        smallSize = size;
        isDotSmall = true;
        refreshText();
    }

    public void closeDotSmall() {
        isDotSmall = false;
        smallSize = -1;
        dotTextScale = 0.5f;
        refreshText();
    }

    /**
     * 判断text中是否包含factorText
     * @param text
     * @param factorText
     * @param isLast
     * @return pos 返回匹配成功的位置，受isLast影响，isLast为false，返回匹配成功开始的位置，否则返回
     *                       匹配成功结束的位置，没有匹配成功返回-1
     */
    private int compareContainCharsequence(CharSequence text,CharSequence factorText,boolean isLast) {
        int pos = -1;
        int tempPos = 0;
        for (int i = 0; i < text.length(); i++) {
            int totalLength = text.length()-i;
            if(tempPos >= factorText.length()) {
                break;
            }
            if(totalLength < factorText.length()) {
                break;
            }
            for (int j = 0; j < factorText.length(); j++) {
                if(text.charAt(i) == factorText.charAt(j)) {
                    ++tempPos;
                    pos = i;
                    if(tempPos >= factorText.length()) {
                        break;
                    }
                } else {
                    tempPos = 0;
                    break;
                }
            }
        }
        if(tempPos == factorText.length()) {
            if(!isLast) {
                pos = pos-tempPos;
            }
        } else {
            pos = -1;
        }
        return pos;
    }


    /**
     * 控件参数类
     */
    public class DynamictextParams{

        public DynamictextParams(URLSpan urlSpan, String text, int size, int foregroundColor, int backgrounColor, Typeface typeface, boolean isUnderLine) {
            this.urlSpan = urlSpan;
            this.text = text;
            this.size = size;
            this.foregroundColor = foregroundColor;
            this.backgrounColor = backgrounColor;
            this.typeface = typeface;
            this.isUnderLine = isUnderLine;
        }

        public DynamictextParams(String text) {
            this.text = text;
        }

        /** 文字 */
        public String text;
        /** 文字大小 */
        public int size = -1;
        /** 文字前景色 */
        public int foregroundColor = -1;
        /** 文字背景色 */
        public int backgrounColor = -1;
        /** 文字字体 */
        public Typeface typeface;
        /** 是否设置下划线 */
        public boolean isUnderLine = false;
        /** 设置超链接*/
        public URLSpan urlSpan;
    }

}
