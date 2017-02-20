package com.wenyuan.myandroiddemo.thirdparty.networkframe.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.utils.UIManager;

/**
 * Created by wenyuan on 2016/11/28 23:33.
 * Description:
 */

public class GlideManager {

    private static RequestManager mRequestManager;//根据with()方法传出不同类型的参数

    /**
     * @param type with()方法传入的参数
     *             Context，Activity，FragmentActivity，Fragment，android.app.Fragment
     */
    private static void withType(Object type) {
        if (type instanceof Context)
            mRequestManager = Glide.with((Context) type);
        else if (type instanceof Activity)
            mRequestManager = Glide.with((Activity) type);
        else if (type instanceof FragmentActivity)
            mRequestManager = Glide.with((FragmentActivity) type);
        else if (type instanceof Fragment)
            mRequestManager = Glide.with((Fragment) type);
        else if (type instanceof android.app.Fragment)
            mRequestManager = Glide.with((android.app.Fragment) type);
        else {
            mRequestManager = Glide.with(UIManager.getInstance().getAppContext());
            //KLog.e("不好意思，你传入的参数有误，没有与之相匹配的类型,使用默认值！(Sorry, you is wrong, the incoming parameters without matching type!)");
        }
    }

    /**
     * @param withType with()方法传入的参数，有五种参数类型使用{@link #withType(Object)}筛选
     * @param url      图片地址
     * @param view     target imageview
     */
    public static void loadImg(Object withType, String url, ImageView view) {
        withType(withType);
        mRequestManager.load(url)
                .placeholder(R.drawable.placeholder)
                .dontAnimate()
                .into(view);

    }

    /**
     *
     */
    public static void loadGifImg() {

    }

    /**
     * @return
     */
    public static Bitmap getBitamp() {
        return null;
    }

}
