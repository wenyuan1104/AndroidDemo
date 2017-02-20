package com.wenyuan.myandroiddemo.thirdparty.networkframe.glide;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by wenyuan on 2016/11/28 23:28.
 * Description:
 */
public class GlideImageView extends ImageView {
    public GlideImageView(Context context) {
        super(context);
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 显示imageview图片
     */
    protected void setImage(Context context, String url) {
        GlideManager.loadImg(context, "", this);
    }
}
