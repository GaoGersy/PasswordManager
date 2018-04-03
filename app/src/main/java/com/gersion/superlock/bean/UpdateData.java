package com.gersion.superlock.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity(indexes = {
        @Index(value = "address, updateTime DESC", unique = true)
})
public class UpdateData {
    @Id(autoincrement = true)
    private Long id;
    private String number;
    @NotNull
    private String address;
    @NotNull
    private String name;
    @NotNull
    private String pwd;
    private long updateTime;
    private String notes;
    private long index;
    private Integer icon;
    private String extraOptions;

    @Generated(hash = 888301604)
    public UpdateData(Long id, String number, @NotNull String address,
                      @NotNull String name, @NotNull String pwd, long updateTime,
                      String notes, long index, Integer icon, String extraOptions) {
        this.id = id;
        this.number = number;
        this.address = address;
        this.name = name;
        this.pwd = pwd;
        this.updateTime = updateTime;
        this.notes = notes;
        this.index = index;
        this.icon = icon;
        this.extraOptions = extraOptions;
    }

    @Generated(hash = 1316730321)
    public UpdateData() {
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

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
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

    public static UpdateData toUpdateData(PasswordData passwordData){
        UpdateData updateData = new UpdateData();
        updateData.setAddress(passwordData.getAddress());
        updateData.setExtraOptions(passwordData.getExtraOptions());
        updateData.setIcon(passwordData.getIcon());
        updateData.setIndex(passwordData.getIndex());
        updateData.setName(passwordData.getName());
        updateData.setNumber(passwordData.getNumber());
        updateData.setNotes(passwordData.getNotes());
        updateData.setPwd(passwordData.getPwd());
        return updateData;
    }
}
