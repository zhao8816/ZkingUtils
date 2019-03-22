package com.zking.widget.refreshview.swipeMenu;


public interface OnSwipeMenuListener {

    /**
     * 点击侧滑删除
     *
     * @param position 索引位置
     */
    void toDelete(int position);

    /**
     * 点击置顶
     *
     * @param position 索引位置
     */
    void toTop(int position);

    /**
     * 按钮点击事件
     *
     * @param obj 对象
     */
    void btnClick(Object obj);

    /**
     * 按钮点击事件
     *
     * @param obj   对象
     * @param index 标识
     */
    void btnClick(Object obj, int index);

}
