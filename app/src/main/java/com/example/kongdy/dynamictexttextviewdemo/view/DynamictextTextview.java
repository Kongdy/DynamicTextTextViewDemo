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
        textStyle.setSpan(new AbsoluteSizeSpan((int)getTextSize()),0,text.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        textStyle.setSpan(new AbsoluteSizeSpan(size),oldText.length()-1,newText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        addCustomeSizeText(text,size);
        textStyle.setSpan(new ForegroundColorSpan(color),oldText.length()-1,newText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
    public void setDynamictextParams(DynamictextParams dynamictextParams) {
        if(dynamictextParams != null && !TextUtils.isEmpty(dynamictextParams.text)) {
            String newText = getText()+dynamictextParams.text;
            CharSequence oldText = getText();
            if(dynamictextParams.size == -1){
                textStyle.setSpan(new AbsoluteSizeSpan((int)getTextSize()),oldText.length()-1,newText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(dynamictextParams.foregroundColor != -1) {
                textStyle.setSpan(new ForegroundColorSpan(dynamictextParams.foregroundColor),oldText.length()-1,newText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(dynamictextParams.backgrounColor != -1){
                textStyle.setSpan(new ForegroundColorSpan(dynamictextParams.backgrounColor),oldText.length()-1,newText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(dynamictextParams.typeface != null) {
                textStyle.setSpan(dynamictextParams.typeface,oldText.length()-1,newText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(dynamictextParams.isUnderLine) {
                textStyle.setSpan(new UnderlineSpan(),oldText.length()-1,newText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(dynamictextParams.urlSpan != null) {
                textStyle.setSpan(dynamictextParams.urlSpan,oldText.length()-1,newText.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            refreshText();
        }
    }

    public void refreshText() {
        this.setText(textStyle, BufferType.SPANNABLE);
        invalidate();
    }

    /**
     * 控件参数类
     */
    public class DynamictextParams{
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
