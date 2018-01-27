package com.gersion.superlock.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.bean.ItemBean;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.fragment.HomeFragment;
import com.gersion.superlock.view.ShadowContainer;

/**
 * Created by aa326 on 2018/1/13.
 */

public class DetailItemAdapter extends MultiTypeAdapter<ItemBean, Object> {
    private static int[][] sPalettes = new int[][]{
            {0xFFF98989, 0xFFE03535},
            {0xFFC1E480, 0xFF67CC34},
            {0xFFEDF179, 0xFFFFB314},
            {0xFF80DDE4, 0xFF286EDC},
            {0xFFE480C6, 0xFFDC285E},
    };
    private HomeFragment mHomeFragment;
    private OnItemClickListener mItemClickListener;


    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ItemBean bean) {
        ShadowContainer shadowContainer;
        ShadowContainer.ShadowDrawable shadowDrawable;
        switch (bean.getLayoutId()) {
            case R.layout.item_detail_del:
                TextView tvDel = (TextView) baseViewHolder.getView(R.id.tv_del);
                tvDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DbManager.getInstance().deleteById(bean.getId());
                    }
                });
//                shadowContainer = (ShadowContainer) baseViewHolder.getView(R.id.container);
//                shadowDrawable = shadowContainer.getShadowDrawable();
//                shadowDrawable.setColors(sPalettes[0]);
                break;
            case R.layout.item_detail_home:
                baseViewHolder.setText(R.id.tv_name, bean.getName());
                baseViewHolder.setText(R.id.tv_icon, bean.getAddress());
                FrameLayout flContainer = (FrameLayout) baseViewHolder.getView(R.id.fl_container);
                flContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener != null) {
                            mItemClickListener.onClik(v);
                        }
                    }
                });
//                shadowContainer = (ShadowContainer) baseViewHolder.getView(R.id.container);
//                shadowDrawable = shadowContainer.getShadowDrawable();
//                shadowDrawable.setColors(sPalettes[1]);
                break;
            case R.layout.item_detail_password:
                baseViewHolder.setText(R.id.tv_name, bean.getName());
                final EditText etPassword = (EditText) baseViewHolder.getView(R.id.et_password);
                etPassword.setText(bean.getPwd());
                ImageView ivEdit = (ImageView) baseViewHolder.getView(R.id.iv_edit);
                ivEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etPassword.setEnabled(true);
                        etPassword.requestFocus();
                    }
                });
//                shadowContainer = (ShadowContainer) baseViewHolder.getView(R.id.container);
//                shadowDrawable = shadowContainer.getShadowDrawable();
//                shadowDrawable.setColors(sPalettes[2]);
                break;
            case R.layout.item_detail_notice:
                String notes = bean.getNotes();
                if (TextUtils.isEmpty(notes)) {
                    notes = "没有任何备注信息";
                }
                baseViewHolder.setText(R.id.tv_notice, notes);
//                shadowContainer = (ShadowContainer) baseViewHolder.getView(R.id.container);
//                shadowDrawable = shadowContainer.getShadowDrawable();
//                shadowDrawable.setColors(sPalettes[3]);
                break;
            default:
                break;
        }
    }

    public interface OnItemClickListener {
        void onClik(View view);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {

        mItemClickListener = itemClickListener;
    }

    public void setFragment(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }
}
