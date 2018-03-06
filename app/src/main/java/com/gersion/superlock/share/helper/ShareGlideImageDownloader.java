/*
 * Copyright (C) 2015 Bilibili <jungly.ik@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gersion.superlock.share.helper;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.bilibili.socialize.share.download.AbsImageDownloader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.gersion.superlock.app.SuperLockApplication;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ShareGlideImageDownloader extends AbsImageDownloader {

    private Disposable mDisposable;

    @Override
    protected void downloadDirectly(final String imageUrl, final String filePath, final OnImageDownloadListener listener) {
        Observable.just(imageUrl)
                .map(new Function<String, File>() {
                    @Override
                    public File apply(@NonNull String s) throws Exception {
                        FileOutputStream fos = null;
                        try {
                            Bitmap bitmap = Glide.with(SuperLockApplication.getContext())
                                    .load(imageUrl)
                                    .asBitmap()
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();

                            File newFile = new File(filePath);
                            fos = new FileOutputStream(newFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            return newFile;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        } finally {
                            try {
                                if (fos != null) {
                                    fos.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(File file) {
                        if (listener != null) {
                            if (file!=null&&file.exists()) {
                                listener.onSuccess(filePath);
                            } else {
                                TastyToast.makeText(SuperLockApplication.getContext(),"获取分享图片失败...", Toast.LENGTH_LONG,TastyToast.ERROR).show();
                                listener.onFailed(imageUrl);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            TastyToast.makeText(SuperLockApplication.getContext(),"获取分享图片失败...", Toast.LENGTH_LONG,TastyToast.ERROR).show();
                            listener.onFailed(imageUrl);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }
                });


    }
}