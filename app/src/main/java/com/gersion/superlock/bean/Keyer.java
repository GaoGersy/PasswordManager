package com.gersion.superlock.bean;

public class Keyer {
    public String number;
    public String address;
    public String name;
    public String pwd;
    public long createTime;
    public String updateTime;
    public String notes;
    public String icon;
    private String extraOption;

    public Keyer(){}

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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getExtraOption() {
        return extraOption;
    }

    public void setExtraOption(String extraOption) {
        this.extraOption = extraOption;
    }

    public Keyer(PasswordData passwordData) {
        address = passwordData.getAddress();
        createTime = passwordData.getCreateTime();
        icon = passwordData.getIcon();
        name = passwordData.getName();
        number = passwordData.getNumber();
        notes = passwordData.getNotes();
        pwd = passwordData.getPwd();
        extraOption = passwordData.getExtraOptions();
    }

    public PasswordData keyer2DbBean() {
        PasswordData passwordData = new PasswordData();
        passwordData.setAddress(address);
        passwordData.setCreateTime(createTime);
        passwordData.setIcon(icon);
        passwordData.setName(name);
        passwordData.setNumber(number);
        passwordData.setNotes(notes);
        passwordData.setExtraOptions(extraOption);
        passwordData.setPwd(pwd);
        return passwordData;
    }

}
