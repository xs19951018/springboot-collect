package com.my.elasticsearch.util;

public class Page {

    /** 第N页 */
    private int from;

    /** 每页条数 */
    private int size;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        size = size > 0 ? size : 10;
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
