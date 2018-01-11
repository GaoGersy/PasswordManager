package com.gersion.superlock.bean;

public class Keyer {
    public String number;
    public String address;
    public String name;
    public String pwd;
    public long createTime;
    public String updateTime;
    public boolean isSelected;
    public int position;
    public boolean isVisible;
    public String notes;
    public int index;
    public String icon;

    public Keyer(){}

    public Keyer(DbBean dbBean) {
        address = dbBean.getAddress();
        createTime = dbBean.getCreateTime();
        icon = dbBean.getIcon();
        index = dbBean.getIndex();
        name = dbBean.getName();
        number = dbBean.getNumber();
        notes = dbBean.getNotes();
        position = dbBean.getPosition();
        pwd = dbBean.getPwd();
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public DbBean keyer2DbBean() {
        DbBean dbBean = new DbBean();
        dbBean.setAddress(address);
        dbBean.setCreateTime(createTime);
        dbBean.setIcon(icon);
        dbBean.setIndex(index);
        dbBean.setName(name);
        dbBean.setNumber(number);
        dbBean.setNotes(notes);
        dbBean.setPosition(position);
        dbBean.setPwd(pwd);
        return dbBean;
    }

}
