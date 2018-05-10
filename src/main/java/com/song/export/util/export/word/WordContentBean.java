package com.song.export.util.export.word;

/**
 * word内容的bean对象,测试用
 */
public class WordContentBean {
    private String title;
    private String pushTime;
    private String content;
    private String url;

    public WordContentBean() {
    }

    public WordContentBean(String title, String pushTime, String content, String url) {
        this.title = title;
        this.pushTime = pushTime;
        this.content = content;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
