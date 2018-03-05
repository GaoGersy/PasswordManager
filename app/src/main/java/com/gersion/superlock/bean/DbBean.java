package com.gersion.superlock.bean;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by a3266 on 2017/5/20.
 */

public class DbBean extends RealmObject implements Serializable {
    @PrimaryKey
    private long id;
    private String number;
    private String address;
    private String name;
    private String pwd;
    private long createTime;
    private RealmList<UpdateBean> updateHistorys;
    private boolean isSelected;
    private int position;
    private boolean isVisible;
    private String notes;
    private long index;
    private String icon;

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
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

    public RealmList<UpdateBean> getUpdateHistorys() {
        return updateHistorys;
    }

    public void setUpdateHistorys(RealmList<UpdateBean> updateHistorys) {
        this.updateHistorys = updateHistorys;
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

    public void copyParams(DbBean bean) {
            this.number = bean.getNumber();
            this.address = bean.getAddress();
            this.name = bean.getName();
            this.pwd = bean.getPwd();
            this.createTime = bean.getCreateTime();
            this.notes = bean.getNotes();
            this.index = bean.getIndex();
            this.icon = bean.getIcon();
//            this.updateHistorys = bean.getUpdateHistorys();
    }

//    @Override
//    public int compareTo(DbBean bean) {//按照从小到大排列
//        if (bean.index > index) {
//            return -1;
//        } else if (bean.index < index) {
//            return 1;
//        } else {
//            return 0;
//        }
//    }

}
