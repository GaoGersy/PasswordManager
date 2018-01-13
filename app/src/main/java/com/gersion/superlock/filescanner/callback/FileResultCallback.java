package com.gersion.superlock.filescanner.callback;

import java.util.List;

public interface FileResultCallback<T> {
    void onStart();

    void onComplete(List<T> files);

}