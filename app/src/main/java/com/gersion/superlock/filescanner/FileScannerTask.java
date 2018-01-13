package com.gersion.superlock.filescanner;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.gersion.superlock.filescanner.callback.FileResultCallback;
import com.gersion.superlock.filescanner.model.FileInfo;
import com.gersion.superlock.filescanner.model.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_ADDED;

/**
 * Created by droidNinja on 01/08/16.
 */
public class FileScannerTask extends AsyncTask<Void, Void, List<FileInfo>> {
    final String[] DOC_PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Files.FileColumns.TITLE
    };

    private final FileResultCallback<FileInfo> resultCallback;

    private final Context context;
    private List<FileType> mFileTypes;

    public FileScannerTask(Context context, List<FileType> fileTypes, FileResultCallback<FileInfo> fileResultCallback) {
        this.context = context;
        mFileTypes = fileTypes;
        this.resultCallback = fileResultCallback;
        resultCallback.onStart();
    }

    @Override
    protected List<FileInfo> doInBackground(Void... voids) {
        ArrayList<FileInfo> fileInfos = new ArrayList<>();
        final String[] projection = DOC_PROJECTION;
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "!="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE + " AND "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "!="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        final Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                null,
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC");

//        final Cursor cursor = context.getContentResolver().query(
//                Uri.parse("content://media/external/file"), projection,
//                MediaStore.Files.FileColumns.DATA + " like ?", new String[]{"%."+mFileType}, MediaStore.Files.FileColumns.DATE_ADDED + " DESC");

        if (cursor != null) {
            fileInfos = getDocumentFromCursor(cursor);
            cursor.close();
        }
        return fileInfos;
    }

    @Override
    protected void onPostExecute(List<FileInfo> fileInfos) {
        super.onPostExecute(fileInfos);
        if (resultCallback != null) {
            resultCallback.onComplete(fileInfos);
        }
    }

    private ArrayList<FileInfo> getDocumentFromCursor(Cursor data) {
        ArrayList<FileInfo> fileInfos = new ArrayList<>();
//        ArrayList<FileType> fileTypes = (ArrayList<FileType>) Arrays.asList(mFileTypes);
//        String[] slk = {MyConstants.FILE_TYPE};
//        fileTypes.add(new FileType(MyConstants.FILE_TYPE, slk, R.drawable.about));

        while (data.moveToNext()) {
            int imageId = data.getInt(data.getColumnIndexOrThrow(_ID));
            String path = data.getString(data.getColumnIndexOrThrow(DATA));
            String title = data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE));
            String date = data.getString(data.getColumnIndexOrThrow(DATE_ADDED));
            if (path != null) {
                FileType fileType = getFileType(mFileTypes, path);
                if (fileType != null && !(new File(path).isDirectory())) {
                    FileInfo fileInfo = new FileInfo(imageId, title, path);
                    fileInfo.setFileType(fileType);
                    fileInfo.setDateAdded(date);

                    String mimeType = data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE));
                    if (mimeType != null && !TextUtils.isEmpty(mimeType)) {
                        fileInfo.setMimeType(mimeType);
                    } else {
                        fileInfo.setMimeType("");
                    }

                    fileInfo.setSize(data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)));

                    if (!fileInfos.contains(fileInfo)) {
                        fileInfos.add(fileInfo);
                    }
                }
            }
        }

        return fileInfos;
    }

    private FileType getFileType(List<FileType> types, String path) {
        int size = types.size();
        for (int index = 0; index < size; index++) {
            FileType fileType = types.get(index);
            for (String string : fileType.extensions) {
                if (path.endsWith(string)) {
                    return fileType;
                }
            }
        }
        return null;
    }
}
