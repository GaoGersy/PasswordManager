package com.gersion.superlock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gersion.superlock.R;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.view
 * @待完成
 * @创建时间 2016/8/27
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class CheckedEditTextView extends LinearLayout implements View.OnFocusChangeListener,TextWatcher {

    public static final int INPUT_STATUS_FOCUS = 1;
    public static final int INPUT_STATUS_UNFOCUS = 0;
    public static final int INPUT_STATUS_WARN = 4;
    public static final int INPUT_STATUS_ERROR = 2;
    public static final int INPUT_STATUS_OK = 3;
    TextView mLable;
    EditText mEtInput;
    ImageView mStatus;
    private boolean mHasStatuBar;
    private int current_status = INPUT_STATUS_UNFOCUS;
    private double minLength = 6;
    private boolean mHasFocus;
    private TextType mType = TextType.USERNAME;


    public CheckedEditTextView(Context context) {
        this(context, null);
    }

    public CheckedEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_checked_edit_text, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CheckedEditTextView);
        mHasStatuBar = ta.getBoolean(R.styleable.CheckedEditTextView_hasStatuBar, true);
        String lable = ta.getString(R.styleable.CheckedEditTextView_lable);

        ta.recycle();


        initView();
        initEvent(lable);
    }

    private void initEvent(String lable) {
        mLable.setText(lable);
        if (mHasStatuBar) {
            mStatus.setVisibility(VISIBLE);
        } else {
            mStatus.setVisibility(View.GONE);
        }

        mEtInput.setOnFocusChangeListener(this);
        mEtInput.addTextChangedListener(this);
    }

    private void initView() {
        mLable = (TextView) findViewById(R.id.lable);
        mStatus = (ImageView) findViewById(R.id.status);
        mEtInput = (EditText) findViewById(R.id.et_input);
    }


    //设置左边标签的颜色
    public void setLableColor(int color) {
        mLable.setTextColor(color);
    }

    //设置标签字体的大小
    public void setLableSize(int size) {
        mLable.setTextSize(size);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        mHasFocus = hasFocus;
        //获得焦点就将状态置为INPUT_STATUS_FOCUS
        if (hasFocus) {
            current_status = INPUT_STATUS_FOCUS;
        } else {
            current_status = INPUT_STATUS_UNFOCUS;
        }
        //如果 获得过焦点，现在没有焦点并且里面没有输入内容就显示错误状态
        if (current_status - INPUT_STATUS_FOCUS == -1  ) {
            if (mEtInput.length() == 0){
                current_status = INPUT_STATUS_ERROR;
            }else if (mEtInput.length()< minLength &&mType == TextType.PASSWORD){
                current_status = INPUT_STATUS_WARN;
            }else{
                current_status = INPUT_STATUS_OK;
            }

        }

        refreshStatus();
    }

    private void refreshStatus() {
        if (!mHasFocus){
            mEtInput.setBackgroundResource(R.drawable.text_bg);
        }
        switch (current_status) {
            case INPUT_STATUS_FOCUS:
                mEtInput.setBackgroundResource(R.drawable.focus_text_bg);
                break;
            case INPUT_STATUS_OK:
                mStatus.setBackgroundResource(R.mipmap.status_ok);

                break;
            case INPUT_STATUS_WARN:
                mStatus.setBackgroundResource(R.mipmap.status_warming);
                break;
            case INPUT_STATUS_ERROR:
                mEtInput.setBackgroundResource(R.drawable.text_error_bg);
                mStatus.setBackgroundResource(R.mipmap.status_error);
                break;
        }
    }

    public void setMinLength(int length){
        minLength = length;
    }

    public String getText(){
        return mEtInput.getText().toString().trim();
    }

    public void setTextType(TextType type){
        mType = type;
        if (mType==TextType.PASSWORD){
            mEtInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }else{
            mEtInput.setInputType(InputType.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int count = s.length();
        if (count>0){
            mStatus.setBackgroundResource(0);
//            if (mType == TextType.PASSWORD&&count>6){
//                mStatus.setBackgroundResource(R.mipmap.status_ok);
//            }
        }else{
            mStatus.setBackgroundResource(R.mipmap.status_error);
        }
    }
    //输入文本框的类型
    public enum TextType{
        PASSWORD,USERNAME,OTHER
    }

    public int getStatus(){
        return current_status;
    }

    public void setText(String text){
        mEtInput.setText(text);
    }

}
