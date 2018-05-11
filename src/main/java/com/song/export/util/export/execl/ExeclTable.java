package com.song.export.util.export.execl;

import java.io.Serializable;
import java.util.List;

/**
 * 导出表格的数据模型table
 */
public class ExeclTable implements Serializable{
    private String sheetName;//sheet名称
    private String head;//表头
    private Boolean hasHead;//是否需要表头
    private List<String> columnName;//列名
    private List<List<String>> columnContent;//内容
    private int columnCount;//列的总数count

    public ExeclTable() {
    }

    public ExeclTable(String sheetName, String head, Boolean hasHead, List<String> columnName, List<List<String>> columnContent, int columnCount) {
        this.sheetName = sheetName;
        this.head = head;
        this.hasHead = hasHead;
        this.columnName = columnName;
        this.columnContent = columnContent;
        this.columnCount = columnCount;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Boolean getHasHead() {
        return hasHead;
    }

    public void setHasHead(Boolean hasHead) {
        this.hasHead = hasHead;
    }

    public List<String> getColumnName() {
        return columnName;
    }

    public void setColumnName(List<String> columnName) {
        this.columnName = columnName;
    }

    public List<List<String>> getColumnContent() {
        return columnContent;
    }

    public void setColumnContent(List<List<String>> columnContent) {
        this.columnContent = columnContent;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }
}
