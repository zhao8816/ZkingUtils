package com.zking.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Customize listview to solve the problem of ScrollView nesting listview to display only one row
 * <p>
 * Created by Z.king on 2018/2/2.
 */
public class UnlimitedListview extends ListView {
    public UnlimitedListview(Context context) {
        this(context, null);
    }

    public UnlimitedListview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnlimitedListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);


    }
}
