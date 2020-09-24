package com.example.bookuilib;

import java.util.ArrayList;
import java.util.List;

public class TxtChapter {


    /*当前章节界面*/
    private final int mPostion;

    private List<TxtPage> txtPageList = new ArrayList<>();
    /*页面长度*/
    private List<Integer> txtPageLengthList = new ArrayList<>();
    /*段落长度*/
    private List<Integer> paragraphLengthList = new ArrayList<>();
    private Status status = Status.LOADING;
    private String msg = null;

    public TxtChapter(int position) {
        mPostion = position;
    }

    public void addPage(TxtPage txtPage) {
        txtPageList.add(txtPage);
    }

    public TxtPage getPage(int page) {
        if (!txtPageList.isEmpty()) {
            return txtPageList.get(Math.max(0, Math.min(page, txtPageList.size() - 1)));
        }
        return null;

    }

    public int getPageLenth(int position) {

        if (position >= 0 && position < txtPageLengthList.size()) {
            return txtPageLengthList.get(position);
        }
        return -1;

    }

    public void addTxtPageLength(int length) {
        txtPageLengthList.add(length);
    }

    public void addParagraphLength(int length) {
        paragraphLengthList.add(length);
    }

    public int getParagraphIndex(int length) {

        for (Integer integer : paragraphLengthList) {
            if ((integer == 0 || paragraphLengthList.get(integer - 1) < length) &&
                    length <= paragraphLengthList.get(integer)) {
                return integer;
            }
        }
        return -1;
    }

    enum Status {
        LOADING, FINISH, ERROR, EMPTY, CATEGORY_EMPTY, CHANGE_SOURCE
    }

}
