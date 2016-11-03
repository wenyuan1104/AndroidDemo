package com.wenyuan.myademo.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
