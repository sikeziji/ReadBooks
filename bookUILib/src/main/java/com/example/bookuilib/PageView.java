package com.example.bookuilib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PageView extends View {


    /** 文字选择画笔 */
    private Paint mTextSelectPaint;
    /**文字选择画笔颜色*/
    private int TextSelectColor = Color.parseColor("#77fadb08");
    /*当前view的宽*/
    private int mViewWidth;
    /*当前View的高*/
    private int mViewHeight;
    /*是否准备*/
    private boolean isPrepare;


    /*内容加载器*/
    private PageLoader mPageLoader;


    public PageView(Context context) {
        this(context,null);
    }

    public PageView(Context context, @Nullable AttributeSet attrs) {

        this(context,attrs,0);
    }

    public PageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化画笔
        mTextSelectPaint = new Paint();
        mTextSelectPaint.setAntiAlias(true);
        //默认字体大小
        mTextSelectPaint.setTextScaleX(19);
        //设置字体颜色
        mTextSelectPaint.setColor(TextSelectColor);


        //TODO : 长按事件，选择字体复制啊等等功能

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        isPrepare = true;

        if (mPageLoader != null) {
            //TODO 完成PageLoader 绘制阅读界面的类
//            mPageLoader.prepareDisplay(width, height);
        }

    }
}
