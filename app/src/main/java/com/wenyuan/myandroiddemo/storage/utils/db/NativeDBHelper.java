package com.wenyuan.myandroiddemo.storage.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.socks.library.KLog;

/**
 * Created by wenyuan on 2016/12/25 15:15.
 * Description:用于管理数据库、创建表结构及初始化数据
 */
public class NativeDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MyAndroidDemo.db";//数据库名字

    /**
     * 创建数据库
     *
     * @param context
     * @param name    数据库名字
     * @param factory 设为null 使用默认值
     * @param version 数据库版本号
     */
    public NativeDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
        KLog.d("NativeDBHelper");
    }

    /**
     * 创建 表结构
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        KLog.d("onCreate");
        db.execSQL(User.CREATE_TABLE);
    }

    /**
     * 更新数据库
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        KLog.d("onUpgrade newVersion: " + newVersion);
        //删除原有表结构
        db.execSQL(User.DROP_TABLE);
        //创建表结构
        onCreate(db);
    }
}
