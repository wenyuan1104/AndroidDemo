package com.wenyuan.myandroiddemo.storage.utils.db;

import android.content.ContentValues;
import android.provider.BaseColumns;

import java.io.Serializable;

/**
 * Created by wenyuan on 2016/12/25 15:17.
 * Description:
 * <h1>Parcelable不能使用在要将数据存储在磁盘上的情况，
 * 因为Parcelable不能很好的保证数据的持续性在外界有变化的情况下。
 * 尽管Serializable效率低点，但此时还是建议使用Serializable 。</h1>
 * <p>
 * BaseColumns 接口中有 _id 字段 创建数据库多需要这个字段
 */
public class User implements BaseColumns,Serializable {

    public static final String TABLE_NAME = "user_table";//表名字

    public static final String _NAME = "name";//字段名
    public static final String _PASSWORD = "password";

    public static final String[] _ALL = {_ID, _NAME, _PASSWORD};//字段集合
    //创建数据库
    public static final String CREATE_TABLE = String.format(
            "create table if not exists %s(%s integer primary key autoincrement,%s text,%s text)",
            TABLE_NAME, _ID, _NAME, _PASSWORD);
    //删除表
    public static final String DROP_TABLE = String.format(
            "drop table if exists %s",
            TABLE_NAME);

    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * 为sqlite操作提供 contentvalues
     * 获得当前对象的 ContentValues
     *
     * @return
     */
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        //字段 _id为主键 不需要设置 会自动增长
        values.put(_NAME, getName());
        values.put(_PASSWORD, getPassword());
        return values;
    }
}
