package com.wenyuan.myandroiddemo.adapter.fortradition;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenyuan.myandroiddemo.R;

import java.util.List;

/**
 * Created by wenyuan on 2016/12/10 20:31.
 * Description:
 */

public class ListViewAdapter<T> extends MyBaseAdapter<T> {

    private List<String> mListData;

    /**
     * @param context 上下文
     * @param data    数据
     */
    public ListViewAdapter(Context context, List<T> data) {
        super(context, data);
        this.mListData = (List<String>) data;
    }

    /**
     * 刷新数据
     *
     * @param data
     */
    @Override
    public void refreshData(List<T> data) {
        mListData.clear();
        mListData = (List<String>) data;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public String getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView)
            convertView = mBaseInflater.inflate(R.layout.item_listview, parent, false);
        //  convertView = mBaseInflater.inflate(R.layout.item_listview, null);//或者这个

        TextView textView = getView(convertView, R.id.textView4);
        textView.setText(mListData.get(position));
        return convertView;
    }

}
