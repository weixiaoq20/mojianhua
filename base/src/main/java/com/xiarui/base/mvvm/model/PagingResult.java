package com.xiarui.base.mvvm.model;

public class PagingResult {
    public boolean isFirstPage;
    public boolean isEmpty;
    public boolean hasNextPage;

    public PagingResult(boolean isFirstPage, boolean isEmpty, boolean hasNextPage) {

        super();
        this.isFirstPage = isFirstPage;
        this.isEmpty = isEmpty;
        this.hasNextPage = hasNextPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}

