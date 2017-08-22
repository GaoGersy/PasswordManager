package com.gersion.superlock.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.activity.MainActivity;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.dialog.LoadingDialog;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/2/23.
 */
public class RefreshWebView extends RelativeLayout {
    private static final Object APP_CACAHE_DIRNAME = "/webViewCache";
    private View mView;
    private WebView4Scroll mWebView;
    private BaseActivity mContext;
    private LinearLayout mErrorContainer;
    private TextView mTvLoadError;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static LoadingDialog mDialog;
    private boolean mIsCanRefresh = true;//是否能下拉刷新
    private ViewGroup mVideoLayout;
    private View mLoadingView;
    private ProgressBar mTopProgress;
    private boolean mIsShowProgress = true;
    private final Paint mPaint = new Paint();
    private static final int API = Build.VERSION.SDK_INT;
    private FrameLayout mFlContainer;
    private FrameLayout mFlLoadingContainer;
    private DisplayMetrics mDisplayMetrics;
    private boolean mIsWebViewDraw;

    public RefreshWebView(Context context) {
        this(context, null);
    }

    public RefreshWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true); //设置View能够获取到焦点。
        setFocusableInTouchMode(true);//设置是否有焦点来响应点触的资格
        mContext = (BaseActivity) context;
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();
        mView = LayoutInflater.from(context).inflate(R.layout.view_webview, this);
        initView();
        initListener();
    }

    private void initView() {
        mWebView = (WebView4Scroll) mView.findViewById(R.id.rv_webView);
        mFlContainer = (FrameLayout) mView.findViewById(R.id.fl_container);
        mFlLoadingContainer = (FrameLayout) mView.findViewById(R.id.fl_loading_container);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.xRefreshView);
        mTopProgress = (ProgressBar) findViewById(R.id.top_web_progress);
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#F6B527"), Color.parseColor("#DB5728"), Color.parseColor("#A01DC6"));
        int length = (int) dp2px(160);
        //设置向下拉多少出现刷新
        mSwipeRefreshLayout.setDistanceToTriggerSync(length);
        initializeSettings();
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());

    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.reload();
            }
        });

        mWebView.setOnWebViewDrawListener(new WebView4Scroll.OnWebViewDrawListener() {

            @Override
            public void onPageLoaded() {
                mIsWebViewDraw = true;
            }
        });
        mWebView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mWebView.getScrollY() == 0) {
                    if (mIsCanRefresh) {
                        mSwipeRefreshLayout.setEnabled(true);
                    } else {
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
                return false;
            }
        });

    }

    public void hideLoading() {
        if (mFlLoadingContainer.getVisibility() == VISIBLE) {
            mFlLoadingContainer.animate()
                    .alpha(0)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mFlLoadingContainer.setVisibility(GONE);
                        }
                    })
                    .start();
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    public void initializeSettings() {
        WebSettings settings = mWebView.getSettings();
        //setPageCacheCapacity2(settings);
        if (API < 18) {
            settings.setAppCacheMaxSize(Long.MAX_VALUE);
        }
        if (API < 17) {
            settings.setEnableSmoothTransition(true);
        }
        if (API > 16) {
            settings.setMediaPlaybackRequiresUserGesture(true);
        }
        if (API >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
//        settings.setBlockNetworkImage(true);
        settings.setSupportMultipleWindows(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        if (mContext instanceof MainActivity) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        settings.setUserAgentString(mWebView.getSettings().getUserAgentString());
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        if (API >= Build.VERSION_CODES.KITKAT) {
            try {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } catch (Exception e) {
            }
        }
        if (API > 16) {
            settings.setAllowFileAccessFromFileURLs(false);
            settings.setAllowUniversalAccessFromFileURLs(false);
        }
        settings.setAppCachePath(mContext.getDir("appcache", 0).getPath());
        settings.setGeolocationDatabasePath(mContext.getDir("geolocation", 0).getPath());
        if (API < Build.VERSION_CODES.KITKAT) {
            settings.setDatabasePath(mContext.getDir("databases", 0).getPath());
        }
    }

    public void setHardwareRendering() {
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        } else {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, mPaint);
        }
    }

    public synchronized void onPause() {
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    public synchronized void goBack() {
        if (mWebView != null) {
            mWebView.goBack();
        }
    }

    public String getUserAgent() {
        if (mWebView != null) {
            return mWebView.getSettings().getUserAgentString();
        } else {
            return "";
        }
    }

    public synchronized void goForward() {
        if (mWebView != null) {
            mWebView.goForward();
        }
    }

    public synchronized void onDestroy() {
        if (mWebView != null) {
            mWebView.setVisibility(GONE);
            mWebView.destroyDrawingCache();
            mWebView.stopLoading();
//            mWebView.pauseTimers();//加了这个第二次进入会不加载网页，不知何故
            mWebView.onPause();
            mWebView.clearHistory();
            mWebView.removeAllViews();
            mWebView.freeMemory();
            releaseAllWebViewCallback();
            mWebView.destroy();
            mWebView = null;
        }
    }

    public synchronized void onResume() {
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    public boolean canGoBack() {
        return mWebView != null && mWebView.canGoBack();
    }

    public boolean canGoForward() {
        return mWebView != null && mWebView.canGoForward();
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void loadUrl(String url) {
        if (mWebView != null) {
            mWebView.loadUrl(url);
        }
    }

    public void reload() {
        if (mWebView != null) {
            mWebView.reload();
        }
    }

    public void goBackOrForward(int steps) {
        mWebView.goBackOrForward(steps);
    }

    public synchronized void pauseTimers() {
        if (mWebView != null) {
            mWebView.pauseTimers();
        }
    }

    public synchronized void resumeTimers() {
        if (mWebView != null) {
            mWebView.resumeTimers();
        }
    }

    public boolean requestWebFocus() {
        boolean hasFocus = false;
        if (mWebView != null && !mWebView.hasFocus()) {
            hasFocus = mWebView.requestFocus();
        }
        return hasFocus;
    }

    public WebBackForwardList copyBackForwardList() {
        return mWebView == null ? null : mWebView.copyBackForwardList();
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        if (mWebView != null) {
            mWebView.setWebViewClient(webViewClient);
        }
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        if (mWebView != null) {
            mWebView.setWebChromeClient(webChromeClient);
        }
    }

    public void setProgressViewVisible(boolean visible) {
        mIsShowProgress = visible;
    }

    public void stopRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public boolean isPullToRefresh() {
        return true;
    }

    public void EnableRefresh(boolean isEnable) {
        mIsCanRefresh = isEnable;
        mSwipeRefreshLayout.setEnabled(isEnable);
    }


    public int getProgress() {
        return mWebView.getProgress();
    }

    public void clearCache(boolean disk) {
        if (mWebView != null) {
            mWebView.clearCache(disk);
        }
    }

    public String getUrl() {
        if (mWebView != null) {
            return mWebView.getUrl();
        } else {
            return "";
        }
    }

    public void releaseAllWebViewCallback() {
        if (Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (Exception e) {

            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (Exception e) {
            }
        }
    }

    private float dp2px(float size) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, mDisplayMetrics);
    }


}
