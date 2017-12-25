package com.tangxb.basic.something.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class MDrawableUtils {
    public static Drawable getDrawable(Resources resources, int resId) {
        Drawable drawable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = resources.getDrawable(resId, null);
        } else {
            drawable = resources.getDrawable(resId);
        }
        return drawable;
    }
}
