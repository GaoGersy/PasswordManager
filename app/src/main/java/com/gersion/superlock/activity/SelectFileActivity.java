package com.gersion.superlock.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.FileListAdapter;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.filescanner.FileScannerTask;
import com.gersion.superlock.filescanner.callback.FileResultCallback;
import com.gersion.superlock.filescanner.model.FileInfo;
import com.gersion.superlock.filescanner.model.FileType;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.view.TitleView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;


public class SelectFileActivity extends BaseActivity {

    private TitleView mTitleView;
    private RecyclerView mRecyclerView;
    private FileListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_file;
    }

    @Override
    protected void initView() {
        mTitleView = (TitleView) findViewById(R.id.titleView);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mTitleView.setTitleText("选择密码文件")
                .setAddVisiable(false)
                .setRightTextVisiable(true)
                .setSearchVisiable(false);

        mAdapter = new FileListAdapter();
        mAdapter.registerMultiBean(FileInfo.class, R.layout.layout_file_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        List<FileType> fileTypes = new ArrayList<>();
        String[] strings = {MyConstants.FILE_TYPE};
        fileTypes.add(new FileType("", strings, R.drawable.about));
        FileScannerTask task = new FileScannerTask(this, fileTypes, new FileResultCallback<FileInfo>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(List<FileInfo> files) {
                for (FileInfo file : files) {
                    String dateAdded = file.getDateAdded();
                    Logger.e(dateAdded);
                }
                mAdapter.setItems(files);
            }
        });
        task.execute();

    }

    @Override
    protected void initListener() {
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTitleView.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = mAdapter.getFileInfo().getPath();
            }
        });
    }
}
