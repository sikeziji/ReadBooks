package com.example.bookuilib;

class BookShelfBean {

    private String noteUrl; //对应BookInfoBean noteUrl;
    private Integer durChapter = 0;   //当前章节 （包括番外）
    private Integer durChapterPage = 0;  // 当前章节位置   用页码
    private Long finalDate = System.currentTimeMillis();  //最后阅读时间
    private Boolean hasUpdate = false;  //是否有更新
    private Integer newChapters = 0;  //更新章节数
    private String tag;
    private Integer serialNumber = 0; //手动排序
    private Long finalRefreshData = System.currentTimeMillis();  //章节最后更新时间
    private Integer group = 0;
    private String durChapterName;
    private String lastChapterName;
    private Integer chapterListSize = 0;
    private String customCoverPath;
    private Boolean allowUpdate = true;
    private Boolean useReplaceRule = true;
    private String variable;


    public String getNoteUrl() {
        return noteUrl;
    }

    public void setNoteUrl(String noteUrl) {
        this.noteUrl = noteUrl;
    }

    public Integer getDurChapter() {
        return durChapter;
    }

    public void setDurChapter(Integer durChapter) {
        this.durChapter = durChapter;
    }

    public Integer getDurChapterPage() {
        return durChapterPage;
    }

    public void setDurChapterPage(Integer durChapterPage) {
        this.durChapterPage = durChapterPage;
    }

    public Long getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Long finalDate) {
        this.finalDate = finalDate;
    }

    public Boolean getHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(Boolean hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

    public Integer getNewChapters() {
        return newChapters;
    }

    public void setNewChapters(Integer newChapters) {
        this.newChapters = newChapters;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getFinalRefreshData() {
        return finalRefreshData;
    }

    public void setFinalRefreshData(Long finalRefreshData) {
        this.finalRefreshData = finalRefreshData;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getDurChapterName() {
        return durChapterName;
    }

    public void setDurChapterName(String durChapterName) {
        this.durChapterName = durChapterName;
    }

    public String getLastChapterName() {
        return lastChapterName;
    }

    public void setLastChapterName(String lastChapterName) {
        this.lastChapterName = lastChapterName;
    }

    public Integer getChapterListSize() {
        return chapterListSize;
    }

    public void setChapterListSize(Integer chapterListSize) {
        this.chapterListSize = chapterListSize;
    }

    public String getCustomCoverPath() {
        return customCoverPath;
    }

    public void setCustomCoverPath(String customCoverPath) {
        this.customCoverPath = customCoverPath;
    }

    public Boolean getAllowUpdate() {
        return allowUpdate;
    }

    public void setAllowUpdate(Boolean allowUpdate) {
        this.allowUpdate = allowUpdate;
    }

    public Boolean getUseReplaceRule() {
        return useReplaceRule;
    }

    public void setUseReplaceRule(Boolean useReplaceRule) {
        this.useReplaceRule = useReplaceRule;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }
}
