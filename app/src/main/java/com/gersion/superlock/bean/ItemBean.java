package com.gersion.superlock.bean;

import com.gersion.library.inter.IMultiLayout;

import java.io.Serializable;

/**
 * Created by aa326 on 2018/1/13.
 */

public class ItemBean implements IMultiLayout,Serializable {
    private long id;
    private int layoutId;
    private String number;
    private String address;
    private String name;
    private String pwd;
    private long createTime;
    private boolean isSelected;
    private int position;
    private boolean isVisible;
    private String notes;
    private long index;
    private String icon;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public void setLayoutId(int i) {
        layoutId = i;
    }

    public static ItemBean DbBean2ItemBean(DbBean bean) {
        ItemBean itemBean = new ItemBean();
        itemBean.number = bean.getNumber();
        itemBean.address = bean.getAddress();
        itemBean.name = bean.getName();
        itemBean.pwd = bean.getPwd();
        itemBean.createTime = bean.getCreateTime();
        itemBean.notes = bean.getNotes();
        itemBean.index = bean.getIndex();
        itemBean.icon = bean.getIcon();
        itemBean.id = bean.getId();
//            this.updateHistorys = bean.getUpdateHistorys();
        return itemBean;
    }
}
