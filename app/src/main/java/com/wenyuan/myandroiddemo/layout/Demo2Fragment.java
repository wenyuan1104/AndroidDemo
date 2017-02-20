package com.wenyuan.myandroiddemo.layout;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.thirdparty.viewframe.pullrefresh.UltraPTRFragmentLayout;
import com.wenyuan.myandroiddemo.utils.copy.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class Demo2Fragment extends BaseFragment implements UltraPTRFragmentLayout.PullRefreshListener,
        AdapterView.OnItemClickListener {

    private UltraPTRFragmentLayout mCustomPullUI;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;


    public Demo2Fragment() {
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_demo2;
    }

    @Override
    protected void initView(View view) {
        mCustomPullUI = (UltraPTRFragmentLayout) view.findViewById(R.id.custom_pull_refresh_layout);
        mCustomPullUI.setPullRefreshListener(this);
        mListView = (ListView) view.findViewById(R.id.list_show_custom);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void initObject() {
    }

    @Override
    protected void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            list.add(String.valueOf("Custom packaging drop-down refresh"));
        mAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(mAdapter);
    }


    /**
     * 下拉刷新中
     *
     * @param frame
     */
    @Override
    public void PullRefreshOperation(PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show(mContext, "refresh complete demo2");
                mCustomPullUI.refreshComplete();
            }
        }, 1500);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtils.show(mContext, String.valueOf(position));
    }
}
