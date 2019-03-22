package com.zking.widget.refreshview.inter;

import android.view.View;

import com.zking.widget.refreshview.adapter.RecyclerArrayAdapter;

/**
 * 接口
 */
public interface EventDelegateAble {
    void addData(int length);

    void clear();

    void stopLoadMore();

    void pauseLoadMore();

    void resumeLoadMore();

    void setMore(View view, RecyclerArrayAdapter.OnMoreListener listener);

    void setNoMore(View view, RecyclerArrayAdapter.OnNoMoreListener listener);

    void setErrorMore(View view, RecyclerArrayAdapter.OnErrorListener listener);

    void setMore(int res, RecyclerArrayAdapter.OnMoreListener listener);

    void setNoMore(int res, RecyclerArrayAdapter.OnNoMoreListener listener);

    void setErrorMore(int res, RecyclerArrayAdapter.OnErrorListener listener);
}
