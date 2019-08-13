package com.nlmeetingroom.pojo;

import java.util.List;

public class Children {
    private String id;
    private int asset_num;
    private String name;
    private List<Children> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAsset_num() {
        return asset_num;
    }

    public void setAsset_num(int asset_num) {
        this.asset_num = asset_num;
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
