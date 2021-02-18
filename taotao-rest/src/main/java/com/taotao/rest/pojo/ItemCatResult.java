package com.taotao.rest.pojo;

import java.util.List;

public class ItemCatResult {
    private List data;

    public ItemCatResult(List data) {
        this.data = data;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public ItemCatResult(){}
}
