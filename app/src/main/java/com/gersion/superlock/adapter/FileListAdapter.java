package com.gersion.superlock.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.filescanner.model.FileInfo;
import com.gersion.superlock.utils.TimeUtils;

/**
 * Created by aa326 on 2018/1/13.
 */

public class FileListAdapter extends MultiTypeAdapter<FileInfo,Object> {
    private CheckBox currentCheckBox;
    private FileInfo mFileInfo;
    @Override
    protected void convert(BaseViewHolder baseViewHolder, final FileInfo fileInfo) {
        baseViewHolder.setText(R.id.tv_name,fileInfo.getFileName());
        long dateAdded = Long.parseLong(fileInfo.getDateAdded());
        String simpleTime = TimeUtils.getTime(dateAdded);
//        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String simpleTime = format.format(dateAdded);
        baseViewHolder.setText(R.id.tv_time, simpleTime);
        LinearLayout view = (LinearLayout) baseViewHolder.getView(R.id.ll_container);
        final CheckBox checkbox = (CheckBox) baseViewHolder.getView(R.id.checkbox);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(checkbox, fileInfo);
            }
        });
    }

    private void changeStatus(CheckBox checkbox, FileInfo fileInfo) {
        if (currentCheckBox!=null){
            currentCheckBox.setChecked(false);
        }
        checkbox.setChecked(true);
        currentCheckBox=checkbox;
        mFileInfo = fileInfo;
    }

    public FileInfo getFileInfo() {
        return mFileInfo;
    }
}
