//package com.gersion.superlock.wxapi;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.tencent.mm.opensdk.constants.ConstantsAPI;
//import com.tencent.mm.opensdk.modelbase.BaseReq;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//
//
///**
// * Created by qwz on 2017/4/19.
// */
//public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
//
//    private static final String TAG = "WXPayEntryActivity";
//    public static Activity backToClass;
//
//    private IWXAPI api;
//    private Intent intent;
//
//    OnWecahtResultListener onWechatResultListener;
//
//    public interface OnWecahtResultListener {
//        void onWechatResult(boolean result);
//    }
//
//    public void setOnWechatResultListener(OnWecahtResultListener listener) {
//        onWechatResultListener = listener;
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        api = WXAPIFactory.createWXAPI(this, WechatPayUtil.APP_ID);
//        api.handleIntent(getIntent(), this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }
//
//    @Override
//    public void onReq(BaseReq req) {
//    }
//
//    //回调中errCode值
//    // 0    成功  展示成功页面s
//    //-1    错误  可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
//    //-2    用户取消    无需处理。发生场景：用户不支付了，点击取消，返回APP。
//    @Override
//    public void onResp(BaseResp resp) {
//        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            if (resp.errCode == 0) {
//                if (backToClass != null) {
//                    if (backToClass instanceof AliPayAlertActivity) {
//                        ((AliPayAlertActivity) backToClass).onWechatResult(true);
//                    }
//                }
//                ToastUtil.show(BaseApplication.getContext(), "支付成功");
//            } else if (resp.errCode == -2) {
//                ToastUtil.show(BaseApplication.getContext(), "取消支付");
//            } else {
//                if (backToClass != null) {
//                    if (backToClass instanceof AliPayAlertActivity) {
//                        ((AliPayAlertActivity) backToClass).onWechatResult(false);
//                    }
//                }
//                ToastUtil.show(BaseApplication.getContext(), "支付异常");
//            }
//            finish();
//        }
//    }
//
//
//}
