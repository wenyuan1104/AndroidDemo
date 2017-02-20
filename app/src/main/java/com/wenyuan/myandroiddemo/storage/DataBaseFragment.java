package com.wenyuan.myandroiddemo.storage;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;

import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.storage.utils.db.NativeDBHelper;
import com.wenyuan.myandroiddemo.storage.utils.db.User;
import com.wenyuan.myandroiddemo.utils.copy.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataBaseFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Switch mSwitchSelectDatabaseOperation;
    private EditText mEditName;
    private EditText mEditPass;
    private Button mButSubmit;
    private Button mButGet;
    private ListView mListView;

    private static boolean mIsLib;//是否使用开源库flag
    private SQLiteDatabase mDatabase;
    private NativeDBHelper dbHelper;
    private Cursor mCursor;
    private SimpleCursorAdapter simpleCursorAdapter;

    public DataBaseFragment() {
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_data_base;
    }

    @Override
    protected void initView(View view) {
        mSwitchSelectDatabaseOperation = (Switch) view.findViewById(R.id.switch_select_database_operation);
        mSwitchSelectDatabaseOperation.setOnCheckedChangeListener(this);
        mEditName = (EditText) view.findViewById(R.id.edit_name);
        mEditPass = (EditText) view.findViewById(R.id.edit_pass);
        mButSubmit = (Button) view.findViewById(R.id.but_submit);
        mButGet = (Button) view.findViewById(R.id.but_get);
        mButSubmit.setOnClickListener(this);
        mButGet.setOnClickListener(this);
        mListView = (ListView) view.findViewById(R.id.list_show_data);
    }

    @Override
    protected void initObject() {
        dbHelper = new NativeDBHelper(mContext, 8);//:数据库版本
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_submit:
                submitData();
                break;
            case R.id.but_get:
                getData();
                break;
        }
    }

    /**
     * 从sqlite中取数据
     */
    private void getData() {
        if (mIsLib) {

        } else {
            getForNative();
        }
    }

    private void getForNative() {
        mDatabase = dbHelper.getReadableDatabase();
        mCursor = mDatabase.query(User.TABLE_NAME,//表名
                User._ALL,//字段
                null,//条件
                null,//条件值
                null,//组
                null,//having
                null);//排序
        if (simpleCursorAdapter != null) { //更新游标
            simpleCursorAdapter.swapCursor(mCursor);
        } else {
            simpleCursorAdapter = new SimpleCursorAdapter(
                    mContext,
                    android.R.layout.simple_list_item_2,
                    mCursor,
                    new String[]{User._ID, User._NAME},
                    new int[]{android.R.id.text1, android.R.id.text2},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            mListView.setAdapter(simpleCursorAdapter);
        }
        // todo 记得关闭游标（Remember to close the cursor） 这里不能关闭 还在使用中
        //cursor.close();
        //管理游标
        //mActivity.startManagingCursor();
    }

    /**
     * 将数据save到sqlite中
     */
    private void submitData() {
        if (mIsLib) {

        } else {
            saveForNative();
        }
    }

    private void saveForNative() {
        mDatabase = dbHelper.getWritableDatabase();
        if (TextUtils.isEmpty(mEditName.getText()) || TextUtils.isEmpty(mEditPass.getText())) {
            ToastUtils.show(mContext, "NullPointException");
            return;
        }
        User user = new User(mEditName.getText().toString(), mEditPass.getText().toString());
        long flag = mDatabase.insert(User.TABLE_NAME, null, user.getContentValues());
        if (flag != -1) ToastUtils.show(mContext, "succeed");
    }

    /**
     * switch 按钮事件
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mIsLib = isChecked;
    }

    /**
     * 关闭游标
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCursor != null)
            mCursor.close();
    }
}
