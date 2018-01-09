package com.gersion.superlock.filescanner;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.gersion.superlock.R;
import com.gersion.superlock.filescanner.callback.FileResultCallback;
import com.gersion.superlock.filescanner.model.Document;
import com.gersion.superlock.filescanner.model.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.MediaColumns.DATA;

/**
 * Created by droidNinja on 01/08/16.
 */
public class DocScannerTask extends AsyncTask<Void, Void, List<Document>> {
    final String[] DOC_PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Files.FileColumns.TITLE
    };
    private static final String TYPE = "slk";
    private final FileResultCallback<Document> resultCallback;

    private final Context context;

    public DocScannerTask(Context context, FileResultCallback<Document> fileResultCallback) {
        this.context = context;
        this.resultCallback = fileResultCallback;
    }

    @Override
    protected List<Document> doInBackground(Void... voids) {
        ArrayList<Document> documents = new ArrayList<>();
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

        if (cursor != null) {
            documents = getDocumentFromCursor(cursor);
            cursor.close();
        }


        return documents;
    }

    @Override
    protected void onPostExecute(List<Document> documents) {
        super.onPostExecute(documents);
        if (resultCallback != null) {
            resultCallback.onResultCallback(documents);
        }
    }

    private ArrayList<Document> getDocumentFromCursor(Cursor data) {
        ArrayList<Document> documents = new ArrayList<>();
        ArrayList<FileType> fileTypes = new ArrayList<>();
        String[] slk = {"slk"};
        fileTypes.add(new FileType(TYPE, slk, R.drawable.about));

        while (data.moveToNext()) {

            int imageId = data.getInt(data.getColumnIndexOrThrow(_ID));
            String path = data.getString(data.getColumnIndexOrThrow(DATA));
            String title = data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE));

            if (path != null) {
                FileType fileType = getFileType(fileTypes, path);
                if (fileType != null && !(new File(path).isDirectory())) {

                    Document document = new Document(imageId, title, path);
                    document.setFileType(fileType);

                    String mimeType = data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE));
                    if (mimeType != null && !TextUtils.isEmpty(mimeType))
                        document.setMimeType(mimeType);
                    else {
                        document.setMimeType("");
                    }

                    document.setSize(data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)));

                    if (!documents.contains(document))
                        documents.add(document);
                }
            }
        }

        return documents;
    }

    private FileType getFileType(ArrayList<FileType> types, String path) {
        for (int index = 0; index < types.size(); index++) {
            for (String string : types.get(index).extensions) {
                if (path.endsWith(string))
                    return types.get(index);
            }
        }
        return null;
    }
}
