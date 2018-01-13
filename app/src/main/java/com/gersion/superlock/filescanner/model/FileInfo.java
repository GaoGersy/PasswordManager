package com.gersion.superlock.filescanner.model;

import java.io.File;

/**
 * Created by droidNinja on 29/07/16.
 */
public class FileInfo extends BaseFile {
    private String mimeType;
    private String size;
    private FileType fileType;
    private String dateAdded;

    public FileInfo(int id, String title, String path) {
        super(id, title, path);
    }

    public FileInfo() {
        super(0, null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileInfo)) return false;

        FileInfo fileInfo = (FileInfo) o;

        return android.R.attr.id == fileInfo.id;
    }

    @Override
    public int hashCode() {
        return android.R.attr.id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return android.R.attr.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFileName() {
        return new File(this.path).getName();
    }

    public void setFileName(String title) {
        this.name = title;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dataAdded) {
        this.dateAdded = dataAdded;
    }

    public boolean isThisType(String[] types) {
        for (String string : types) {
            if (path.toLowerCase().endsWith(string)) {
                return true;
            }
        }
        return false;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "mimeType='" + mimeType + '\'' +
                ", size='" + size + '\'' +
                ", fileType=" + fileType +
                '}';
    }
}
