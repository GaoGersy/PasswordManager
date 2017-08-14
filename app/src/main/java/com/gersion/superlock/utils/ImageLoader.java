package com.gersion.superlock.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gersion.superlock.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by soulrelay on 2016/10/11 13:42.
 * Class Note:
 * use this class to load image,single instance
 */
public class ImageLoader {

    private static ImageLoader mInstance;
    public ImageLoader() {
    }

    //单例模式，节省资源
    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public void loadCircleIcon(int resId,ImageView imageView) {
        Glide
                .with(imageView.getContext())
                .load(resId)
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .placeholder(R.drawable.pure_bg) //设置占位图
                .error(R.drawable.pure_bg) //设置错误图片
                .into(imageView);
    }

    public void loadBlurBg(int resId,ImageView imageView) {
        Glide
                .with(imageView.getContext())
                .load(resId)
                .placeholder(R.drawable.pure_bg) //设置占位图
                .error(R.drawable.pure_bg) //设置错误图片
                .bitmapTransform(new BlurTransformation(imageView.getContext(), 14, 5))
                .into(imageView);
    }

    public void loadResBlurImage(int resId,ImageView imageView) {
        Glide
                .with(imageView.getContext())
                .load(resId)
                .placeholder(R.mipmap.yellow) //设置占位图
                .error(R.mipmap.yellow) //设置错误图片
                .bitmapTransform(new BlurTransformation(imageView.getContext(), 14, 5))
                .into(imageView);
    }

    /*
    * ~~ 时间：2017/6/10 23:19 ~~
    * 加载本地图片,高斯模糊
    **/
    public void loadLocalBlurImage(String path, int placeholder, ImageView imageView) {
        Glide
                .with(imageView.getContext())
                .load(path)
                .placeholder(placeholder) //设置占位图
                .error(placeholder) //设置错误图片
                .bitmapTransform(new BlurTransformation(imageView.getContext(), 14, 3))
                .into(imageView);
    }

    public void loadLocalBlurImage(String path, ImageView imageView) {
        loadLocalBlurImage(path,R.mipmap.yellow,imageView);
    }

    /*
    * ~~ 时间：2017/6/10 23:19 ~~
    * 加载本地图片
    **/
    public void loadLocalImage(String path, int placeholder, ImageView imageView) {
        Glide
                .with(imageView.getContext())
                .load(path)
                .placeholder(placeholder) //设置占位图
                .error(placeholder) //设置错误图片
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .into(imageView);
    }

    public void loadLocalImage(String path, ImageView imageView) {
        loadCircleImage(path,R.mipmap.yellow,imageView);
    }

    /**
     * 统一使用App context
     * 可能带来的问题：http://stackoverflow.com/questions/31964737/glide-image-loading-with-application-context
     *
     * @param url
     * @param placeholder
     * @param imageView
     */
    public void loadImage(String url, int placeholder, ImageView imageView) {
        Glide
                .with(imageView.getContext())
                .load(url)
                .placeholder(placeholder) //设置占位图
                .error(placeholder) //设置错误图片
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .into(imageView);
    }

    public void loadImage(String url, ImageView imageView) {
        loadImage(url,R.mipmap.yellow,imageView);
    }

    public void loadGifImage(String url, int placeholder, ImageView imageView) {
    }

    public void loadCircleImage(String url, ImageView imageView) {
        loadCircleImage(url,R.mipmap.yellow,imageView);
    }

    public void loadCircleImage(String url, int placeholder, ImageView imageView) {
        Glide
                .with(imageView.getContext())
                .load(url).bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .placeholder(placeholder) //设置占位图
                .error(placeholder) //设置错误图片
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .into(imageView);
    }

    public void loadCircleBorderImage(String url, int placeholder, ImageView imageView, float borderWidth, int borderColor) {
    }

    public void loadCircleBorderImage(String url, int placeholder, ImageView imageView, float borderWidth, int borderColor, int heightPX,int widthPX) {
    }

    public void loadImageWithAppCxt(String url, ImageView imageView) {
    }


    /**
     * 清除图片磁盘缓存
     */
    public void clearImageDiskCache(final Context context) {
    }

    /**
     * 清除图片内存缓存
     */
    public void clearImageMemoryCache(Context context) {
    }

    /**
     * 根据不同的内存状态，来响应不同的内存释放策略
     *
     * @param context
     * @param level
     */
    public void trimMemory(Context context, int level) {
    }

    /**
     * 清除图片所有缓存
     */
    public void clearImageAllCache(Context context) {
        clearImageDiskCache(context.getApplicationContext());
        clearImageMemoryCache(context.getApplicationContext());
    }

//    /**
//     * 获取缓存大小
//     *
//     * @return CacheSize
//     */
//    public String getCacheSize(Context context) {
//        return Glide.getPhotoCacheDir(context).getUsableSpace();
//    }
//
//    public void saveImage(Context context, String url, String savePath, String saveFileName, ImageSaveListener listener) {
//        mStrategy.saveImage(context, url, savePath, saveFileName, listener);
//    }
}
