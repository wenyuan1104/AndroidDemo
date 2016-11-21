package com.wenyuan.myandroiddemo.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * Created by www22_000 as wenyuan on 2016/10/29 22:43.
 * Email :wenyuan1104@163.com
 * Description:
 */

public class AlertDialogV7Factory {

    private Context mContext;

    public AlertDialogV7Factory(@NonNull Context context) {
        this.mContext = context;
    }

    /**
     * @param title
     * @param message
     */
    public AlertDialog showTextDialog(String title, String message, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setCancelable(cancelable)
                .setTitle(TextUtils.isEmpty(title) == true ? "" : title)
                .setMessage(TextUtils.isEmpty(message) == true ? "" : message)
                .setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /**
     * 单选列表对框框
     *
     * @param title
     * @param content
     */
    public AlertDialog showSingleListDialog(String title, boolean cancelable, int checkedItem, @NonNull String[] content, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setCancelable(cancelable)
                .setTitle(TextUtils.isEmpty(title) == true ? "" : title)
                .setSingleChoiceItems(content, checkedItem >= content.length ? -1 : checkedItem, listener)
                .setNegativeButton("取消", null)
                .setNeutralButton("更多", null)
                .setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /**
     * 为了显示 缺失和设置 权限的对话框
     *
     * @param title
     * @param content
     * @param listener
     */
    public AlertDialog showForPermissionOfDialog(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(TextUtils.isEmpty(title) == true ? "" : title)
                .setMessage(TextUtils.isEmpty(content) == true ? "" : content)
                .setPositiveButton("设置权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        //为什么加这句话 不加的话不能跳转至app详情界面
                        intent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
                        mContext.startActivity(intent);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

    /**
     * 加载对话框 的自定义对话框
     */
    public class ProgressDialogFactory extends ProgressDialog {

        public ProgressDialogFactory(Context context) {
            super(context);
        }

        public ProgressDialogFactory(Context context, int theme) {
            super(context, theme);
        }

        /**
         * 带文字的加载对话框
         *
         * @param message
         */
        public void showLoadDialog(String title, String message) {
            this.setCancelable(false);
            if (!TextUtils.isEmpty(title))
                this.setTitle(title);

            this.setMessage(message);
            this.create();
            this.show();
        }

        /**
         *
         */
        public void dismiss() {
            if (null != this)
                super.dismiss();
        }

        /**
         * @return
         */
        public boolean isShowing() {
            if (null != this)
                return super.isShowing();
            return false;
        }
    }
}
