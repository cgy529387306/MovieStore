package com.android.mb.movie.entity;

import java.io.Serializable;

public class BaseListData implements Serializable{
    private int currentPage;

    private int pageCount;

    private int pageSize;

    private int rowCount;

    private boolean isEnd;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public boolean isEnd() {
        return pageSize*currentPage>=rowCount;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
