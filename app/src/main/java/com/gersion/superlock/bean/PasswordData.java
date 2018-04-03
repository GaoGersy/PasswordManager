package com.gersion.superlock.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity(indexes = {
        @Index(value = "address, createTime DESC", unique = true)
})
public class PasswordData {
    @Id(autoincrement = true)
    private Long id;
    private String number;
    @NotNull
    private String address;
    @NotNull
    private String name;
    @NotNull
    private String pwd;
    @NotNull
    private long createTime;
    private String updateHistoryIds;
    private boolean isSelected;
    private int position;
    private boolean isVisible;
    private String notes;
    private long index;
    private Integer icon;
    private String extraOptions;

    @Generated(hash = 916813017)
    public PasswordData(Long id, String number, @NotNull String address,
                        @NotNull String name, @NotNull String pwd, long createTime,
                        String updateHistoryIds, boolean isSelected, int position,
                        boolean isVisible, String notes, long index, Integer icon,
                        String extraOptions) {
        this.id = id;
        this.number = number;
        this.address = address;
        this.name = name;
        this.pwd = pwd;
        this.createTime = createTime;
        this.updateHistoryIds = updateHistoryIds;
        this.isSelected = isSelected;
        this.position = position;
        this.isVisible = isVisible;
        this.notes = notes;
        this.index = index;
        this.icon = icon;
        this.extraOptions = extraOptions;
    }

    @Generated(hash = 281740898)
    public PasswordData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUpdateHistoryIds() {
        return this.updateHistoryIds;
    }

    public void setUpdateHistoryIds(String updateHistoryIds) {
        this.updateHistoryIds = updateHistoryIds;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean getIsVisible() {
        return this.isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getIndex() {
        return this.index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public Integer getIcon() {
        return this.icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getExtraOptions() {
        return this.extraOptions;
    }

    public void setExtraOptions(String extraOptions) {
        this.extraOptions = extraOptions;
    }


}
