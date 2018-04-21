package com.gersion.superlock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseLifeActivity;
import com.gersion.superlock.utils.KeyboardUtils;

/**
 * Created by gersy on 2017/6/24.
 */

public class SearchView extends LinearLayout {

    private View mView;
    private BaseLifeActivity mContext;
    private EditText mEtContent;
    private ImageView mIvClear;
    private ImageView mIvBack;
    private View mLlContainer;
    private OnQueryChangeListener mOnQueryChangeListener;
    private OnClickListener mOnBackClickListener;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mView = LayoutInflater.from(context).inflate(R.layout.view_search, this);
        mContext = (BaseLifeActivity) context;
        initView();
        initData(attrs);
        initListner();
    }

    private void initView() {
        mIvBack = findView(R.id.iv_back);
        mIvClear = findView(R.id.iv_clear);
        mEtContent = findView(R.id.et_content);
        mLlContainer = findView(R.id.ll_container);
        mIvClear.setVisibility(GONE);
        KeyboardUtils.toggleSoftInput();
    }

    private void initData(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.Croller);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SearchView_search_hint) {
                setHintText(a.getString(attr));
            } else if (attr == R.styleable.SearchView_search_background) {
                setBackgroundColor(a.getColor(attr, getResources().getColor(R.color.font_item_color)));
            } else if (attr == R.styleable.SearchView_search_hint_color) {
                setHintTextColor(a.getColor(attr, getResources().getColor(R.color.gray_e0)));
            } else if (attr == R.styleable.SearchView_search_text_size) {
                setTextSize(a.getInteger(attr, 15));
            }
        }
        a.recycle();
    }

    private void initListner() {
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                if (mOnQueryChangeListener != null) {
                    mOnQueryChangeListener.onTextChange(query);
                }
                mIvClear.setVisibility(query.length() == 0 ? GONE : VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    String content = mEtContent.getText().toString().toString();
                    if (content.length() > 0) {
                        if (imm.isActive()) {
                            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                        }
                        if (mOnQueryChangeListener != null) {
                            mOnQueryChangeListener.onTextSubmit(content);
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        mIvClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtContent.setText("");
            }
        });
    }

    public void setHintText(String hintText) {
        mEtContent.setHint(hintText);
    }

    public void setHintTextColor(int color) {
        mEtContent.setHintTextColor(color);
    }

    public void setTextSize(int size) {
        mEtContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setBackgroundColor(int color) {
        mLlContainer.setBackgroundColor(color);
    }

    public void setBackground(Drawable drawable) {
        mLlContainer.setBackground(drawable);
    }

    public void setOnQueryChangeListener(OnQueryChangeListener onQueryChangeListener) {
        mOnQueryChangeListener = onQueryChangeListener;
    }

    public void setOnBackClickListener(OnClickListener onBackClickListener) {
        mOnBackClickListener = onBackClickListener;
        mIvBack.setOnClickListener(mOnBackClickListener);
    }

    public interface OnQueryChangeListener {
        void onTextSubmit(String query);

        void onTextChange(String query);
    }

    public interface OnBackClickListener {
        void onBackClick();
    }

    protected <T extends View> T findView(int id) {
        return (T) mView.findViewById(id);
    }
}
