package com.gersion.superlock.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.activity.AddPasswordActivity;
import com.gersion.superlock.activity.MainActivity;
import com.gersion.superlock.activity.PasswordDetailActivity;
import com.gersion.superlock.bean.ExtraOptionBean;
import com.gersion.superlock.bean.ItemBean;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.utils.GsonHelper;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.DetaiItemView;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa326 on 2018/1/13.
 */

public class DetailItemAdapter extends MultiTypeAdapter<ItemBean, Object> {
    private OnItemClickListener mItemClickListener;
    private List<View> views_1 = new ArrayList<>();
    private List<View> views_2 = new ArrayList<>();
    private List<View> views_3 = new ArrayList<>();
    private View mConvertView;

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ItemBean bean) {
        mConvertView = baseViewHolder.getConvertView();
        switch (bean.getLayoutId()) {
            case R.layout.item_detail_home:
                TextView tvName = (TextView) baseViewHolder.getView(R.id.tv_name);
                tvName.setText(bean.getAddress());
                Integer icon = bean.getIcon();
                int iconResource = icon == null ? R.mipmap.default_icon : icon;
                baseViewHolder.setImageResource(R.id.iv_icon, iconResource);
//                FrameLayout flContainer = (FrameLayout) baseViewHolder.getView(R.id.fl_container);
//                flContainer.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (mItemClickListener != null) {
//                            mItemClickListener.onClik(v);
//                        }
//                    }
//                });
                ImageView ivInfo = (ImageView) baseViewHolder.getView(R.id.iv_info);
                ivInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity mainActivity = (MainActivity) mConvertView.getContext();
                        Bundle bundle = new Bundle();
                        bundle.putLong("id", bean.getId());
                        mainActivity.toActivity(PasswordDetailActivity.class, bundle);
                    }
                });
                break;
            case R.layout.item_detail_password:
                TextView title = (TextView) baseViewHolder.getView(R.id.tv_title);
                DetaiItemView itemName = (DetaiItemView) baseViewHolder.getView(R.id.item_name);
                DetaiItemView itemPwd = (DetaiItemView) baseViewHolder.getView(R.id.item_pwd);
                itemName.setItemName("用户名");
                itemName.setItemValue(bean.getName());
                itemPwd.setItemName("密码");
                itemPwd.setItemValue(bean.getPwd());
                TextView tvDel = (TextView) baseViewHolder.getView(R.id.tv_del);
                TextView tvEdit = (TextView) baseViewHolder.getView(R.id.tv_edit);
                addView(views_1,title);
                addView(views_1,itemName);
                addView(views_1,itemPwd);
                addView(views_1,tvDel);
                addView(views_1,tvEdit);
                tvDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MainActivity mainActivity = (MainActivity) mConvertView.getContext();
                        StyledDialog.buildIosAlert(mainActivity, "确认删除这条密码吗？", "删除后将不能恢复，请谨慎删除！", new MyDialogListener() {
                            @Override
                            public void onFirst() {
                                DbManager.getInstance().deleteById(bean.getId());
                                ToastUtils.showTasty(mainActivity, "删除成功", TastyToast.SUCCESS);
                            }

                            @Override
                            public void onSecond() {

                            }
                        }).show();

                    }
                });
                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity mainActivity = (MainActivity) mConvertView.getContext();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("itemBean", bean);
                        mainActivity.toActivity(AddPasswordActivity.class, bundle);
                    }
                });
                viewGone(views_1);
                break;
            case R.layout.item_detail_notice:
                TextView tvTitle = (TextView) baseViewHolder.getView(R.id.tv_title);
                TextView tv_notice = (TextView) baseViewHolder.getView(R.id.tv_notice);
                String notes = bean.getNotes();
                if (TextUtils.isEmpty(notes)) {
                    notes = "没有任何备注信息";
                }
                tv_notice.setText(notes);
                addView(views_2,tvTitle);
                addView(views_2,tv_notice);
                viewGone(views_2);
                break;
            case R.layout.item_detail_more:
                TextView tvNoData = (TextView) baseViewHolder.getView(R.id.tv_no_data);
                LinearLayout container = (LinearLayout) baseViewHolder.getView(R.id.container);
                Context context = mConvertView.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                String extraOptions = bean.getExtraOptions();
                if (extraOptions != null) {
                    List<ExtraOptionBean> extraOptionBeans = GsonHelper.toList(extraOptions, ExtraOptionBean.class);
                    if (extraOptionBeans != null) {
                        tvNoData.setVisibility(View.GONE);
                        int size = extraOptionBeans.size()>2?2:extraOptionBeans.size();
                        for (int i = 0; i < size; i++) {
                            ExtraOptionBean extraOptionBean = extraOptionBeans.get(i);
                            DetaiItemView detaiItemView = new DetaiItemView(context);
                            detaiItemView.setItemName(extraOptionBean.getKey());
                            detaiItemView.setItemValue(extraOptionBean.getValue());
                            container.addView(detaiItemView);
                            views_3.add(detaiItemView);
                        }
                    }
                }
                viewGone(views_3);
                break;
            default:
                break;
        }
    }

    private void addView(List<View> views_1, View tv_title) {
        if (!views_1.contains(tv_title)){
            views_1.add(tv_title);
        }
    }

    public void playAnimator(int position) {
        if (currentPosition == position) {
            return;
        }
        currentPosition = position;
        if (position == 1) {
            viewGone(views_1);
            startPlay(views_1);
        } else if (position == 2) {
            viewGone(views_2);
            startPlay(views_2);
        } else if (position == 3) {
            viewGone(views_3);
            startPlay(views_3);
        }
    }

    int currentPosition = 0;

    public void hideView(int position) {
        currentPosition = position;
        if (position == 1) {
            viewGone(views_1);
        } else if (position == 2) {
            viewGone(views_2);
        } else if (position == 3) {
            viewGone(views_3);
        }
    }

    public void needShowViews(int position) {
        if (currentPosition != position) {
            return;
        }
        if (position == 1) {
            viewVisible(views_1);
        } else if (position == 2) {
            viewVisible(views_2);
        } else if (position == 3) {
            viewVisible(views_3);
        }
    }

    private void viewVisible(List<View> views) {
        for (int i = 0; i < views.size(); i++) {
            View view = views.get(i);
            view.setVisibility(View.VISIBLE);
        }
    }

    private void viewGone(List<View> views) {
        for (int i = 0; i < views.size(); i++) {
            View view = views.get(i);
            view.setVisibility(View.GONE);
        }
    }

    private void startPlay(List<View> views) {
        for (int i = 0; i < views.size(); i++) {
            View view = views.get(i);
            doAnimator(mConvertView, view, i * 100);
        }
    }

    private void doAnimator(View mView, final View view, int dely) {
        if (mView == null) {
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", mView.getWidth(), 0);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setStartDelay(dely);
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
    }

    public interface OnItemClickListener {
        void onClik(View view);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {

        mItemClickListener = itemClickListener;
    }

}
