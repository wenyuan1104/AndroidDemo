package com.wenyuan.myandroiddemo.adapter.fortradition;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by wenyuan on 2016/12/10 20:14.
 * Description:<h1>listview gridview 封装基本适配器</h1>
 */

public abstract class MyBaseAdapter<M> extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mBaseInflater;//视图填充器

    /**
     * @param context 上下文
     * @param data    数据
     */
    public MyBaseAdapter(Context context, List<M> data) {
        this.mContext = context;
        mBaseInflater = LayoutInflater.from(context);
    }

    /**
     * 刷新数据
     *
     * @param t 数据
     */
    public abstract void refreshData(List<M> data);

    /**
     * 封装ViewHolder
     *
     * @param view    convertView
     * @param resouId item子视图id
     * @param <T>     item子视图
     * @return
     */
    protected <T extends View> T getView(View view, int resouId) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (null == viewHolder) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(resouId);
        if (null == childView) {
            childView = view.findViewById(resouId);
            viewHolder.put(resouId, childView);
        }
        return (T) childView;
    }
}
