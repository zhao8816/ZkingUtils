package com.zking.widget.refreshview.observer;

import android.support.v7.widget.RecyclerView;

import com.zking.widget.refreshview.RefreshView;
import com.zking.widget.refreshview.adapter.RecyclerArrayAdapter;

public class EasyDataObserver extends RecyclerView.AdapterDataObserver {

    private RefreshView recyclerView;
    private RecyclerArrayAdapter adapter;

    public EasyDataObserver(RefreshView recyclerView) {
        this.recyclerView = recyclerView;
        if (recyclerView.getAdapter() instanceof RecyclerArrayAdapter) {
            adapter = (RecyclerArrayAdapter) recyclerView.getAdapter();
        }
    }

    private boolean isHeaderFooter(int position) {
        return adapter != null && (position < adapter.getHeaderCount()
                || position >= adapter.getHeaderCount() + adapter.getCount());
    }


    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        if (!isHeaderFooter(positionStart)) {
            update();
        }
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        if (!isHeaderFooter(positionStart)) {
            update();
        }
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        if (!isHeaderFooter(positionStart)) {
            update();
        }
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        //header&footer不会有移动操作
        update();
    }

    @Override
    public void onChanged() {
        super.onChanged();
        //header&footer不会引起changed
        update();
    }


    //自动更改Container的样式
    private void update() {
        int count;
        if (recyclerView.getAdapter() instanceof RecyclerArrayAdapter) {
            count = ((RecyclerArrayAdapter) recyclerView.getAdapter()).getCount();
        } else {
            count = recyclerView.getAdapter().getItemCount();
        }
        if (count == 0) {
            recyclerView.showEmpty();
        } else {
            recyclerView.showRecycler();
        }
    }
}