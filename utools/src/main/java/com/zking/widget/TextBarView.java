package com.zking.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zking.utools.R;
import com.zking.utools.StringUtils;

/**
 * Created by Z.king on 2019/3/21.
 */
public class TextBarView extends LinearLayout {

    private TextView tvTitle;
    private AppCompatTextView tvContent;

    public TextBarView(Context context) {
        super(context);
        init(context, null);
    }

    public TextBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_text_bar, this, true);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextBarView);
            String title = ta.getString(R.styleable.TextBarView_title);
            String desc = ta.getString(R.styleable.TextBarView_description);
            String hint = ta.getString(R.styleable.TextBarView_hint);
            int titColor = ta.getColor(R.styleable.TextBarView_titleColor, 0x333333);
            int descColor = ta.getColor(R.styleable.TextBarView_descColor, 0x999999);
            float titSize = ta.getDimensionPixelSize(R.styleable.TextBarView_titleSize, StringUtils.dp2px(15));
            float decSize = ta.getDimensionPixelSize(R.styleable.TextBarView_decSize, StringUtils.dp2px(15));
            setBarTitle(title);
            setBarDesc(desc);
            setBarHint(hint);
            setBarTitColor(titColor);
            setBarDescColor(descColor);
            setTitSize(titSize);
            setDecSize(decSize);
        }

    }

    public void setBarTitle(String str) {
        tvTitle.setText(str == null ? "" : str);
    }

    public void setBarDesc(String str) {
        tvContent.setText(str == null ? "" : str);
    }

    public void setBarHint(String str) {
        tvContent.setHint(str == null ? "" : str);
    }

    public void setBarTitColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setBarDescColor(int color) {
        tvContent.setTextColor(color);
    }

    private void setTitSize(float titSize) {
        tvTitle.getPaint().setTextSize(titSize);
    }

    private void setDecSize(float titSize) {
        tvContent.getPaint().setTextSize(titSize);
    }
}
