package com.gersion.superlock.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gersion.superlock.R;
import com.gersion.superlock.bean.Keyer;
import com.gersion.superlock.event.DetailEvent;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.toastlibrary.TastyToast;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.fragment
 * @待完成
 * @创建时间 2016/9/16
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class DetailsShowFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_guide_vp_1, null);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getEventData(DetailEvent event){
        Keyer msg = event.getMsg();
        ToastUtils.showTasty(getActivity(),"传来了"+msg.name, TastyToast.WARNING);
    }
}
