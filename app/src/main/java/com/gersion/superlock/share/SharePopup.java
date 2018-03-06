package com.gersion.superlock.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.bilibili.socialize.share.core.BiliShare;
import com.bilibili.socialize.share.core.BiliShareConfiguration;
import com.bilibili.socialize.share.core.SocializeListeners;
import com.bilibili.socialize.share.core.SocializeMedia;
import com.bilibili.socialize.share.core.error.BiliShareStatusCode;
import com.bilibili.socialize.share.core.shareparam.ShareImage;
import com.bilibili.socialize.share.core.shareparam.ShareParamWebPage;
import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.base.BaseLifeActivity;
import com.gersion.superlock.share.helper.ShareGlideImageDownloader;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.ToastUtils;
import com.sdsmdg.tastytoast.TastyToast;

public class SharePopup extends BaseLifeActivity implements OnClickListener {
    private static final String DATA_URL = "data_url";
    private ImageView share_weixin;
    private ImageView share_friends;
    private ImageView share_qq;

    private Bundle bundle;
    private FragmentActivity context;
    private String title = "密码管家";
    private String content = "找到一个好用的密码管理软件，迫不及待想推荐给你哟...";
    private String imageUrl = "http://pp.myapp.com/ma_icon/0/icon_42382793_1503457910/96";
    private String url = "http://sj.qq.com/myapp/detail.htm?apkName=com.gersion.superpasswordlock";

    private String mDataUrl;
    private ShareParamWebPage param;
    private BiliShare mBiliShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog_share);
        initShare();
//        mActivity = this;
//        context = this;
//        tencent = Tencent.createInstance(MyConstants.QQ_APP_ID, getApplicationContext());

//        bundle = this.getIntent().getExtras();
//        if (bundle != null) {
//            if (bundle.containsKey(DATA_URL)) {
//                mDataUrl = bundle.getString(DATA_URL);
//                setUrl(mDataUrl);
//            }
//        }
        setUrl(null);

        // 占满屏
        LayoutParams lay = getWindow().getAttributes();
        lay.height = LayoutParams.MATCH_PARENT;
        lay.width = LayoutParams.MATCH_PARENT;

        share_weixin = (ImageView) this.findViewById(R.id.share_weixin);
        share_friends = (ImageView) this.findViewById(R.id.share_friends);
        share_qq = (ImageView) this.findViewById(R.id.share_qq);
        // 添加按钮监听
        share_weixin.setOnClickListener(this);
        share_friends.setOnClickListener(this);
        share_qq.setOnClickListener(this);
    }

    public static void startPopShare(BaseActivity activity, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(DATA_URL, url);
        Intent intent = new Intent(activity,SharePopup.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private void setUrl(String json) {
//        title = GsonHelper.getString(json, "title");
//        content = GsonHelper.getString(json, "content");
//        if (TextUtils.isEmpty(content)){
//            content= "  ";
//        }
//        url = GsonHelper.getString(json, "url");
//        imageUrl = GsonHelper.getString(json, "imgUrl");
        setParmas();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onBackPressed();
        return true;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.share_weixin) {
            startShare(SocializeMedia.WEIXIN);
        } else if (id == R.id.share_friends) {
            startShare(SocializeMedia.WEIXIN_MONMENT);
        } else if (id == R.id.share_qq) {
            startShare(SocializeMedia.QQ);
        }
    }

    private void initShare() {
        BiliShareConfiguration configuration = new BiliShareConfiguration.Builder(this)
                .imageDownloader(new ShareGlideImageDownloader())
                .qq(MyConstants.QQ_APP_ID)
                .weixin(MyConstants.WEIXIN_APP_ID)
                .build();
        mBiliShare = BiliShare.global();
        mBiliShare.config(configuration);
    }

    private void setParmas() {
        param = new ShareParamWebPage(title, content, url);
        param.setThumb(new ShareImage(imageUrl));
    }

    public void startShare(SocializeMedia media) {
        if (param == null) {
            return;
        }
        mBiliShare.share(this, media, param, shareListener);
    }

    protected SocializeListeners.ShareListener shareListener = new SocializeListeners.ShareListenerAdapter() {
        @Override
        public void onStart(SocializeMedia type) {
        }

        @Override
        protected void onComplete(SocializeMedia type, int code, Throwable error) {
            if (code == BiliShareStatusCode.ST_CODE_SUCCESSED) {
                ToastUtils.showTasty(SharePopup.this, "分享成功", TastyToast.SUCCESS);
            } else if (code == BiliShareStatusCode.ST_CODE_ERROR) {
                ToastUtils.showTasty(SharePopup.this, "分享失败", TastyToast.ERROR);
            }
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.push_bottom_out);
    }
}

