package com.gersion.superlock.wxapi;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gersion.superlock.app.SuperLockApplication;
import com.gersion.superlock.base.BaseLifeActivity;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.ToastUtils;
import com.sdsmdg.tastytoast.TastyToast;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
public class WXEntryActivity extends BaseLifeActivity implements IWXAPIEventHandler {

    private Context context = WXEntryActivity.this;
    private IWXAPI mIWXAPI;


    private void handleIntent(Intent paramIntent) {
        SuperLockApplication.api.handleIntent(paramIntent, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
//        initWXApi();
    }

    private void initWXApi() {
        mIWXAPI = WXAPIFactory.createWXAPI(this, MyConstants.WEIXIN_APP_ID, true);
        if (mIWXAPI.isWXAppInstalled()) {
            mIWXAPI.registerApp(MyConstants.WEIXIN_APP_ID);
        }
        try {
            mIWXAPI.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onReq(BaseReq arg0) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            if (BaseResp.ErrCode.ERR_OK == resp.errCode) {
                ToastUtils.showTasty(this, "分享成功", TastyToast.SUCCESS);
            } else if (BaseResp.ErrCode.ERR_USER_CANCEL == resp.errCode) {
                ToastUtils.showTasty(this, "分享取消", TastyToast.ERROR);
            } else {
                ToastUtils.showTasty(this, "分享失败", TastyToast.ERROR);
            }
            finish();
        }
//        if (ConstantsAPI.COMMAND_SENDAUTH == resp.getType()) {
//            WeixinAPI weixinAPI = WeixinAPI.getInstance();
//            weixinAPI.weixinCallBack(resp);
//            finish();
//        }
    }
}

