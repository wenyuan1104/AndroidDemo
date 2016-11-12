package com.wenyuan.myademo.detail.permission;

import android.content.ClipData;
import android.os.Build;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.wenyuan.myademo.BaseActivity;
import com.wenyuan.myademo.R;
import com.wenyuan.myademo.utils.ToastUtils;

public class PermissionActivity extends BaseActivity {

    private TextView mTextShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_permission);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mTextShow = (TextView) findViewById(R.id.text_show);
    }

    @Override
    protected void initData() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_permission, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_per_test:
                break;
            case R.id.action_copy:
                CopyTxtTOClipboard();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 复制textview里面的文字内容到 剪切板
     * API 11之前： android.text.ClipboardManager
     * API 11之后： android.content.ClipboardManager
     * <p>
     * A. 创建普通字符型ClipData：ClipData mClipData = ClipData.newPlainText("Label", "Content");
     * B. 创建URL型ClipData：ClipData.newRawUri("Label", Uri.parse("http://www.fishme.cn/"));
     * C. 创建Intent型ClipData：ClipData.newIntent("Label", intent);
     */
    private void CopyTxtTOClipboard() {
        String txt = mTextShow.getText().toString();
        if (TextUtils.isEmpty(txt))
            return;
        if (Build.VERSION.SDK_INT < 11) {//API 11之前： android.text.ClipboardManager
            ClipboardManager cm = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            cm.setText(txt);//复制
            if (!TextUtils.isEmpty(cm.getText()))//粘贴
                ToastUtils.show(mContext, "复制文本到剪切板");
        } else { //API 11之后： android.content.ClipboardManager
            android.content.ClipboardManager cm = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            cm.setPrimaryClip(ClipData.newPlainText(null, txt));//无格式text  //复制
            if (cm.hasPrimaryClip()) {
                ToastUtils.show(mContext, "复制文本到剪切板,个数：" + cm.getPrimaryClip().getItemCount());
                //cm.getPrimaryClip().getItemAt();
            }
        }

    }
}
