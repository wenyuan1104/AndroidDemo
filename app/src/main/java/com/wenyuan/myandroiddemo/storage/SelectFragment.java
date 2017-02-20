package com.wenyuan.myandroiddemo.storage;


import android.support.v4.app.Fragment;
import android.view.View;

import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectFragment extends BaseFragment implements View.OnClickListener {


    public SelectFragment() {
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_select;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.but_file).setOnClickListener(this);
        view.findViewById(R.id.but_database).setOnClickListener(this);
        view.findViewById(R.id.but_prefs_and_provider).setOnClickListener(this);
        view.findViewById(R.id.but_prefs).setOnClickListener(this);
    }

    @Override
    protected void initObject() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_file:
                mOnButtonListener.ButtonClickForFile();
                break;
            case R.id.but_database:
                mOnButtonListener.ButtonClickForDatabase();
                break;
            case R.id.but_prefs_and_provider:
                mOnButtonListener.ButtonClickForToP();
                break;
            case R.id.but_prefs:
                mOnButtonListener.ButtonClickForToPF();
                break;
            default:
                break;
        }
    }


    public interface OnButtonListener{
        void ButtonClickForFile();
        void ButtonClickForDatabase();
        void ButtonClickForToP();
        void ButtonClickForToPF();
    }

    private OnButtonListener mOnButtonListener;

    public void setOnButtonListener(OnButtonListener onButtonListener) {
        mOnButtonListener = onButtonListener;
    }
}
