package com.example.bookuilib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面加载器
 */
public abstract class PageLoader {
    /*TAG*/
    private static final String TAG = "PageLoader";

    // 默认的显示参数配置
    /*默认边距的宽度*/
    public static final int DEFAULT_MARGIN_WIDTH = 15;
    /*默认边距的高度*/
    private static final int DEFAULT_MARGIN_HEIGHT = 20;
    /*默认Tip的大小*/
    private static final int DEFAULT_TIP_SIZE = 12;
    /*最大滚动偏移*/
    private static final float MAX_SCROLL_OFFSET = 100;
    /*Tip的开始*/
    private static final int TIP_ALPHA = 180;
    /*一个Sp是多少px*/
    private final int oneSpPx;
    /*当前Page视图*/
    private  PageView mPageView;
    /*接受传入的书籍信息*/
    private  BookShelfBean mBook;
    /*接受返回的Callback*/
    private  Callback mCallback;
    /*上下文*/
    private  Context mContext;
//    private  CompositeDisposable compositeDisposable;
    /*当前章节*/
    private  int mCurChapterPos;
    private int mCurPagePos;



    private List<ChapterContainer> chapterContainers = new ArrayList<>();


    //应用的宽高
    int mDisplayWidth;
    private int mDisplayHeight;

    //间距
    private int mMarginTop;
    private int mMarginBottom;
    private int mMarginLeft;
    private int mMarginRight;
    int contentMarginHeight;
    private int tipMarginTop;
    private int tipMarginBottom;

    private float tipBottomTop;
    private float tipBottomBot;
    private float tipDistance;
    private float tipMarginLeft;
    private float displayRightEnd;
    private float tipVisibleWidth;

    //书籍绘制区域的宽高
    int mVisibleWidth;
    int mVisibleHeight;

    private boolean hideStatusBar;

    // 阅读器的配置选项
    ReadBookControl readBookControl = ReadBookControl.getInstance();
    // 绘制电池的画笔
    private TextPaint mBatteryPaint;
    // 绘制提示的画笔(章节名称和时间)
    private TextPaint mTipPaint;
    private float pageOffset = 0;
    // 绘制标题的画笔
    TextPaint mTitlePaint;
    // 绘制小说内容的画笔
    TextPaint mTextPaint;
    // 绘制结束的画笔
    private TextPaint mTextEndPaint;
    //标题的大小
    private int mTitleSize;

    //字体的大小
    private int mTextSize;
    private int mTextEndSize;
    //行间距
    int mTextInterval;
    //标题的行间距
    int mTitleInterval;
    //段落距离(基于行间距的额外距离)
    int mTextPara;
    int mTitlePara;

    PageLoader(PageView pageView , BookShelfBean book , Callback callback){
        mPageView = pageView;
        mBook = book;
        mCallback = callback;
        for (int i = 0; i <3; i++) {
            chapterContainers.add(new ChapterContainer());
        }
        mContext = pageView.getContext();
        mCurChapterPos = book.getDurChapter();
        mCurPagePos = book.getDurChapterPage();
//        compositeDisposable = new CompositeDisposable();
        oneSpPx = ScreenUtils.spToPx(1);
        // 初始化数据
        initData();
        // 初始化画笔
        initPaint();

    }
    protected  void initData(){
//        // 获取配置参数
//        hideStatusBar = readBookControl.getHideStatusBar();
//        showTimeBattery = hideStatusBar && readBookControl.getShowTimeBattery();
//        mPageMode = PageAnimation.Mode.getPageMode(readBookControl.getPageMode());
//        // 初始化参数
//        indent = StringUtils.repeat(StringUtils.halfToFull(" "), readBookControl.getIndent());
//        // 配置文字有关的参数
//        setUpTextParams();

    };


    protected  void initPaint(){
        Typeface typeface;
        try {
            if (!TextUtils.isEmpty(readBookControl.getFontPath())) {
                typeface = Typeface.createFromFile(readBookControl.getFontPath());
            } else {
                typeface = Typeface.SANS_SERIF;
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "字体文件未找,到恢复默认字体", Toast.LENGTH_SHORT).show();
            readBookControl.setReadBookFont(null);
            typeface = Typeface.SANS_SERIF;
        }
        // 绘制提示的画笔
        mTipPaint = new TextPaint();
        mTipPaint.setColor(readBookControl.getTextColor());
        mTipPaint.setTextAlign(Paint.Align.LEFT); // 绘制的起始点
        mTipPaint.setTextSize(ScreenUtils.spToPx(DEFAULT_TIP_SIZE)); // Tip默认的字体大小
        mTipPaint.setTypeface(Typeface.create(typeface, Typeface.NORMAL));
        mTipPaint.setAntiAlias(true);
        mTipPaint.setSubpixelText(true);

        // 绘制标题的画笔
        mTitlePaint = new TextPaint();
        mTitlePaint.setColor(readBookControl.getTextColor());
        mTitlePaint.setTextSize(mTitleSize);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTitlePaint.setLetterSpacing(readBookControl.getTextLetterSpacing());
        }
        mTitlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTitlePaint.setTypeface(Typeface.create(typeface, Typeface.BOLD));
        mTitlePaint.setTextAlign(Paint.Align.CENTER);
        mTitlePaint.setAntiAlias(true);

        // 绘制页面内容的画笔
        mTextPaint = new TextPaint();
        mTextPaint.setColor(readBookControl.getTextColor());
        mTextPaint.setTextSize(mTextSize);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTextPaint.setLetterSpacing(readBookControl.getTextLetterSpacing());
        }
        int bold = readBookControl.getTextBold() ? Typeface.BOLD : Typeface.NORMAL;
        mTextPaint.setTypeface(Typeface.create(typeface, bold));
        mTextPaint.setAntiAlias(true);

        // 绘制结束的画笔
        mTextEndPaint = new TextPaint();
        mTextEndPaint.setColor(readBookControl.getTextColor());
        mTextEndPaint.setTextSize(mTextEndSize);
        mTextEndPaint.setTypeface(Typeface.create(typeface, Typeface.NORMAL));
        mTextEndPaint.setAntiAlias(true);
        mTextEndPaint.setSubpixelText(true);
        mTextEndPaint.setTextAlign(Paint.Align.CENTER);

        // 绘制电池的画笔
        mBatteryPaint = new TextPaint();
        mBatteryPaint.setAntiAlias(true);
        mBatteryPaint.setDither(true);
        mBatteryPaint.setTextSize(ScreenUtils.spToPx(DEFAULT_TIP_SIZE - 3));
        mBatteryPaint.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "number.ttf"));

        setupTextInterval();
        // 初始化页面样式
        initPageStyle();
    }

    private int textInterval;
    private int textPara;
    private int titleInterval;
    private int titlePara;
    /**
     * 设置文字间隔
     */
    private void setupTextInterval() {
        textInterval = mTextInterval + (int) mTextPaint.getTextSize();
        textPara = mTextPara + (int) mTextPaint.getTextSize();
        titleInterval = mTitleInterval + (int) mTitlePaint.getTextSize();
        titlePara = mTitlePara + (int) mTextPaint.getTextSize();
    }

    /**
     * 设置页面样式
     */
    private void initPageStyle() {
        mTipPaint.setColor(readBookControl.getTextColor());
        mTitlePaint.setColor(readBookControl.getTextColor());
        mTextPaint.setColor(readBookControl.getTextColor());
        mBatteryPaint.setColor(readBookControl.getTextColor());
        mTextEndPaint.setColor(readBookControl.getTextColor());
        mTipPaint.setAlpha(TIP_ALPHA);
        mBatteryPaint.setAlpha(TIP_ALPHA);
        mTextEndPaint.setAlpha(TIP_ALPHA);
    }

    private synchronized void drawContent(Bitmap bitmap, TxtChapter txtChapter, TxtPage txtPage) {
        if (bitmap == null) return;
        Canvas canvas = new Canvas(bitmap);
//        if (mPageMode == PageAnimation.Mode.SCROLL) {
//            bitmap.eraseColor(Color.TRANSPARENT);
//        }

        Paint.FontMetrics fontMetricsForTitle = mTitlePaint.getFontMetrics();
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();

        if (txtChapter.getStatus() != TxtChapter.Status.FINISH) {
            //绘制字体
            String tip = getStatusText(txtChapter);
            drawErrorMsg(canvas, tip, 0);
        } else {
            float top = contentMarginHeight - fontMetrics.ascent;
            //TODO ： 添加更改翻页模式功能
//            if (mPageMode != PageAnimation.Mode.SCROLL) {
//                top += readBookControl.getHideStatusBar() ? mMarginTop : mPageView.getStatusBarHeight() + mMarginTop;
//            }
            int ppp = 0;//pzl,文字位置
            //对标题进行绘制
            String str;
            int strLength = 0;
//            boolean isLight;
            for (int i = 0; i < txtPage.getTitleLines(); ++i) {
                str = txtPage.getLine(i);
                strLength = strLength + str.length();
//                isLight = ReadAloudService.running && readAloudParagraph == 0;
//                mTitlePaint.setColor(isLight ? ThemeStore.accentColor(mContext) : readBookControl.getTextColor());

                //进行绘制
                canvas.drawText(str, mDisplayWidth / 2f, top, mTitlePaint);

                //pzl
                float leftposition = mDisplayWidth / 2;
                float rightposition = 0;
                float bottomposition = top + mTitlePaint.getFontMetrics().descent;
                float TextHeight = Math.abs(fontMetricsForTitle.ascent) + Math.abs(fontMetricsForTitle.descent);

                //TODO : 添加选择字符功能 TxtChar
//                if (txtPage.getTxtLists() != null) {
//                    for (TxtChar c : txtPage.getTxtLists().get(i).getCharsData()) {
//                        rightposition = leftposition + c.getCharWidth();
//                        Point tlp = new Point();
//                        c.setTopLeftPosition(tlp);
//                        tlp.x = (int) leftposition;
//                        tlp.y = (int) (bottomposition - TextHeight);
//
//                        Point blp = new Point();
//                        c.setBottomLeftPosition(blp);
//                        blp.x = (int) leftposition;
//                        blp.y = (int) bottomposition;
//
//                        Point trp = new Point();
//                        c.setTopRightPosition(trp);
//                        trp.x = (int) rightposition;
//                        trp.y = (int) (bottomposition - TextHeight);
//
//                        Point brp = new Point();
//                        c.setBottomRightPosition(brp);
//                        brp.x = (int) rightposition;
//                        brp.y = (int) bottomposition;
//                        ppp++;
//                        c.setIndex(ppp);
//
//                        leftposition = rightposition;
//                    }
//                }

                //设置尾部间距
                if (i == txtPage.getTitleLines() - 1) {
                    top += titlePara;
                } else {
                    //行间距
                    top += titleInterval;
                }
            }

            if (txtPage.getLines().isEmpty()) {
                return;
            }
            //对内容进行绘制
            for (int i = txtPage.getTitleLines(); i < txtPage.getSize(); ++i) {
                str = txtPage.getLine(i);
                strLength = strLength + str.length();
                //TODO： 设置画笔颜色 根据主题（白天，夜间）
//                int paragraphLength = txtPage.getPosition() == 0 ? strLength : txtChapter.getPageLength(txtPage.getPosition() - 1) + strLength;
//                isLight = ReadAloudService.running && readAloudParagraph == txtChapter.getParagraphIndex(paragraphLength);
//                mTextPaint.setColor(isLight ? ThemeStore.accentColor(mContext) : readBookControl.getTextColor());
                Layout tempLayout = new StaticLayout(str, mTextPaint, mVisibleWidth, Layout.Alignment.ALIGN_NORMAL, 0, 0, false);
                float width = StaticLayout.getDesiredWidth(str, tempLayout.getLineStart(0), tempLayout.getLineEnd(0), mTextPaint);
                //TODO： 绘制比例文字
//                if (needScale(str)) {
//                    drawScaledText(canvas, str, width, mTextPaint, top, i, txtPage.getTxtLists());
//                } else {
                    canvas.drawText(str, mMarginLeft, top, mTextPaint);
//                }

                //记录文字位置 --开始 pzl
                float leftposition = mMarginLeft;
                if (isFirstLineOfParagraph(str)) {
                    String blanks = StringUtils.halfToFull("  ");
                    //canvas.drawText(blanks, x, top, mTextPaint);
                    float bw = StaticLayout.getDesiredWidth(blanks, mTextPaint);
                    leftposition += bw;
                }
                float rightposition = 0;
                float bottomposition = top + mTextPaint.getFontMetrics().descent;
                float textHeight = Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.descent);

                if (txtPage.getTxtLists() != null) {
                    for (TxtChar c : txtPage.getTxtLists().get(i).getCharsData()) {
                        rightposition = leftposition + c.getCharWidth();
                        Point tlp = new Point();
                        c.setTopLeftPosition(tlp);
                        tlp.x = (int) leftposition;
                        tlp.y = (int) (bottomposition - textHeight);

                        Point blp = new Point();
                        c.setBottomLeftPosition(blp);
                        blp.x = (int) leftposition;
                        blp.y = (int) bottomposition;

                        Point trp = new Point();
                        c.setTopRightPosition(trp);
                        trp.x = (int) rightposition;
                        trp.y = (int) (bottomposition - textHeight);

                        Point brp = new Point();
                        c.setBottomRightPosition(brp);
                        brp.x = (int) rightposition;
                        brp.y = (int) bottomposition;

                        leftposition = rightposition;

                        ppp++;
                        c.setIndex(ppp);
                    }
                }
                //记录文字位置 --结束 pzl

                //设置尾部间距
                if (str.endsWith("\n")) {
                    top += textPara;
                } else {
                    top += textInterval;
                }
            }
        }
    }

    //判断是不是d'hou
    private boolean isFirstLineOfParagraph(String line) {
        return line.length() > 3 && line.charAt(0) == (char) 12288 && line.charAt(1) == (char) 12288;
    }



    private void drawErrorMsg(Canvas canvas, String msg, float offset) {
        Layout tempLayout = new StaticLayout(msg, mTextPaint, mVisibleWidth, Layout.Alignment.ALIGN_NORMAL, 0, 0, false);
        List<String> linesData = new ArrayList<>();
        for (int i = 0; i < tempLayout.getLineCount(); i++) {
            linesData.add(msg.substring(tempLayout.getLineStart(i), tempLayout.getLineEnd(i)));
        }
        float pivotY = (mDisplayHeight - textInterval * linesData.size()) / 3f - offset;
        for (String str : linesData) {
            float textWidth = mTextPaint.measureText(str);
            float pivotX = (mDisplayWidth - textWidth) / 2;
            canvas.drawText(str, pivotX, pivotY, mTextPaint);
            pivotY += textInterval;
        }
    }


    /**
     * 获取状态文本
     */
    private String getStatusText(TxtChapter chapter) {
        String tip = "";
        switch (chapter.getStatus()) {
            case LOADING:
                tip = mContext.getString(R.string.loading);
                break;
            case ERROR:
//                tip = mContext.getString(R.string.load_error_msg, curChapter().txtChapter.getMsg());
                break;
            case EMPTY:
//                tip = mContext.getString(R.string.content_empty);
                break;
            case CATEGORY_EMPTY:
//                tip = mContext.getString(R.string.chapter_list_empty);
                break;
            case CHANGE_SOURCE:
//                tip = mContext.getString(R.string.on_change_source);
        }
        return tip;
    }

    void prepareDisplay(int w, int h) {
        // 获取PageView的宽高
        mDisplayWidth = w;
        mDisplayHeight = h;

        // 设置边距
        mMarginTop = hideStatusBar ?
                ScreenUtils.dpToPx(readBookControl.getTipPaddingTop() + readBookControl.getPaddingTop() + DEFAULT_MARGIN_HEIGHT)
                : ScreenUtils.dpToPx(readBookControl.getPaddingTop());
        mMarginBottom = ScreenUtils.dpToPx(readBookControl.getTipPaddingBottom() + readBookControl.getPaddingBottom() + DEFAULT_MARGIN_HEIGHT);
        mMarginLeft = ScreenUtils.dpToPx(readBookControl.getPaddingLeft());
        mMarginRight = ScreenUtils.dpToPx(readBookControl.getPaddingRight());
        contentMarginHeight = oneSpPx;
        tipMarginTop = ScreenUtils.dpToPx(readBookControl.getTipPaddingTop() + DEFAULT_MARGIN_HEIGHT);
        tipMarginBottom = ScreenUtils.dpToPx(readBookControl.getTipPaddingBottom() + DEFAULT_MARGIN_HEIGHT);

        Paint.FontMetrics fontMetrics = mTipPaint.getFontMetrics();
        float tipMarginTopHeight = (tipMarginTop + fontMetrics.top - fontMetrics.bottom) / 2;
        float tipMarginBottomHeight = (tipMarginBottom + fontMetrics.top - fontMetrics.bottom) / 2;
        tipBottomTop = tipMarginTopHeight - fontMetrics.top;
        tipBottomBot = mDisplayHeight - fontMetrics.bottom - tipMarginBottomHeight;
        tipDistance = ScreenUtils.dpToPx(DEFAULT_MARGIN_WIDTH);
        tipMarginLeft = ScreenUtils.dpToPx(readBookControl.getTipPaddingLeft());
        float tipMarginRight = ScreenUtils.dpToPx(readBookControl.getTipPaddingRight());
        displayRightEnd = mDisplayWidth - tipMarginRight;
        tipVisibleWidth = mDisplayWidth - tipMarginLeft - tipMarginRight;

        // 获取内容显示位置的大小
        mVisibleWidth = mDisplayWidth - mMarginLeft - mMarginRight;
              mVisibleHeight = readBookControl.getHideStatusBar()
                ? mDisplayHeight - mMarginTop - mMarginBottom
                : mDisplayHeight - mMarginTop - mMarginBottom - mPageView.getStatusBarHeight();

        // 设置翻页模式
//        mPageView.setPageMode(mPageMode, mMarginTop, mMarginBottom);
//        skipToChapter(mCurChapterPos, mCurPagePos);

    };



    static class ChapterContainer {
        TxtChapter txtChapter;
    }




    //region 自定义回调接口
    public interface Callback {
        List<BookChapterBean> getChapterList();

        /**
         * 作用：章节切换的时候进行回调
         *
         * @param pos:切换章节的序号
         */
        void onChapterChange(int pos);

        /**
         * 作用：章节目录加载完成时候回调
         *
         * @param chapters：返回章节目录
         */
        void onCategoryFinish(List<BookChapterBean> chapters);

        /**
         * 作用：章节页码数量改变之后的回调。==> 字体大小的调整，或者是否关闭虚拟按钮功能都会改变页面的数量。
         *
         * @param count:页面的数量
         */
        void onPageCountChange(int count);

        /**
         * 作用：当页面改变的时候回调
         *
         * @param chapterIndex   章节序号
         * @param pageIndex      页数
         * @param resetReadAloud 是否重置朗读
         */
        void onPageChange(int chapterIndex, int pageIndex, boolean resetReadAloud);

        void vipPop();
    }
    //endregion

}
