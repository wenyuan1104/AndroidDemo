package com.wenyuan.myandroiddemo.layout;

import android.view.View;
import android.widget.ListView;

import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.adapter.fortradition.ListViewAdapter;
import com.wenyuan.myandroiddemo.utils.copy.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class Demo3Fragment extends BaseFragment {

    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    private ListView mListView;
    private ListViewAdapter<String> mAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_demo3;
    }

    @Override
    protected void initView(View view) {
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.pull_view);
        mListView = (ListView) view.findViewById(R.id.pull_list);
        mPtrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        mPtrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show(mContext, "refresh complete demo3");
                        mPtrClassicFrameLayout.refreshComplete();
                    }
                }, 1500);
            }
        });
    }

    @Override
    protected void initObject() {

    }

    @Override
    protected void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            list.add(String.valueOf("PtrClassicFrameLayout"));
        mAdapter = new ListViewAdapter<>(mContext, list);
        mListView.setAdapter(mAdapter);
    }
}
