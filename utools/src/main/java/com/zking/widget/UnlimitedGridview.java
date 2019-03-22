package com.zking.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Custom GridView to solve the problem of ScrollView nesting ListView display only one row
 * <p>
 * Created by Z.king on 2018/2/7.
 */
public class UnlimitedGridview extends GridView {
    public UnlimitedGridview(Context context) {
        this(context, null);
    }

    public UnlimitedGridview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnlimitedGridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);


    }
}
