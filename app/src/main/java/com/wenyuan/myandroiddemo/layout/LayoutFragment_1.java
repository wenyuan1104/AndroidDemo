package com.wenyuan.myandroiddemo.layout;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LayoutFragment_1 extends BaseFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    public LayoutFragment_1() {
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_layout_fragment_1;
    }

    @Override
    protected void initView(View view) {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_header_ui);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_show);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_green_light, android.R.color.holo_blue_light);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initObject() {

    }

}
