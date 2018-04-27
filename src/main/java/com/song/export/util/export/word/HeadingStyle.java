package com.song.export.util.export.word;

/**
 * word 文档中标题的样式，含编号样式和文字样式
 */
public class HeadingStyle {

    /**
     * 标题的等级，≥1
     */
    private int grade;

    /**
     * 标题所在段落的整体样式，如 1、heading 2等
     */
    private String pStyle;

    /**
     * 标题编码格式的正则表达式
     * 目前仅支持 #.#，即数字之间以 . 分割
     */
    private String hStyle;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getPStyle() {
        return pStyle;
    }

    public void setPStyle(String pStyle) {
        this.pStyle = pStyle;
    }

    public String getHStyle() {
        return hStyle;
    }

    public void setHStyle(String hStyle) {
        this.hStyle = hStyle;
    }

    public HeadingStyle(int grade, String pStyle) {
        super();
        this.grade = grade;
        this.pStyle = pStyle;
        this.hStyle = "#.#";
    }

    public HeadingStyle(int grade, String pStyle, String hStyle) {
        super();
        this.grade = grade;
        this.pStyle = pStyle;
        this.hStyle = hStyle;
    }

}