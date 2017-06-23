package com.superlibrary.ken.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by PC_WLT on 2017/3/27.
 */

public abstract class ComAdapter<T> extends BaseAdapter{


    protected Context context;
    protected List<T> list;
    private int layoutId;

    public ComAdapter(Context context, List<T> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 封装getView方法
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //得到一个ViewHolder
        ComViewHolder viewHolder = ComViewHolder.get(context, convertView, parent, layoutId, position);

        //设置控件内容
        setViewContent(viewHolder, (T) getItem(position));

        //返回复用的View
        return viewHolder.getConvertView();
    }

    /**
     * 提供抽象方法，来设置控件内容
     *
     * @param viewHolder 一个ViewHolder
     * @param t          一个数据集
     */
    public abstract void setViewContent(ComViewHolder viewHolder, T t);
}
