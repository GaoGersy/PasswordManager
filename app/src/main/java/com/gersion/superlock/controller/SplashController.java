  
package com.gersion.superlock.controller;

import android.content.Context;
import android.view.View;

import com.gersion.superlock.R;

/** 
 * ClassName: NewsCenterBean <br/> 
 * Function: TODO ADD FUNCTION. <br/>  
 * date: 2016年8月9日 下午7:36:01 <br/> 
 * 
 * @作者 Gers 
 * @版本  
 * @包名 com.example.smartbeijing.bean
 * @待完成 TODO
 * @创建时间 2016年8月9日
 * @描述 TODO
 * 
 * @更新人 $Author$
 * @更新时间 $Date$
 * @更新版本 $Rev$
 */
public class SplashController {

    private Context mContext;
    private View mView;

    public SplashController(Context context) {
        mContext = context;
        initView();
        
        initData();
        
        initEvent();
        
    }
    //初始化控件
    private void initView() {
        mView = View.inflate(mContext, R.layout.view_guide_vp_1, null);
    }
    //初始化数据
    private void initData() {
        
    }
    //初始化事件监听
    private void initEvent() {
        
        
    }
    
    public View getRootView(){
        return mView;
    }
    
    
}
  