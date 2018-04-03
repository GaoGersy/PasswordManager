package com.gersion.superlock.bean;

/**
 * Created by gersy on 2018/4/1.
 */

public class IconBean {
    private String name;
    private Integer resourceId;
    private boolean selected;

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
}
