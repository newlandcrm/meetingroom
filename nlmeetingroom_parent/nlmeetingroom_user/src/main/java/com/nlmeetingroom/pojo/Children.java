package com.nlmeetingroom.pojo;

import java.util.List;

public class Children {
    private String id;
    private int assetNum;
    private String name;
    private List<Children> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAssetNum() {
        return assetNum;
    }

    public void setAssetNum(int assetNum) {
        this.assetNum = assetNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }
}
