package com.tangxb.basic.something.util;

import com.orhanobut.logger.Logger;
import com.tangxb.basic.something.BuildConfig;

/**
 * Created by Taxngb on 2017-12-16.
 */

public class MLogUtils {
    private static boolean DEBUG = BuildConfig.LOG_DEBUG;

    public static void d(String tag, Object object) {
        if (DEBUG) {
            Logger.t(tag).d(object);
        }
    }

    public static void json(String tag, String msg) {
        if (DEBUG) {
            Logger.t(tag).json(msg);
        }
    }
}
