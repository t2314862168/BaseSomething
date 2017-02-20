package com.tangxb.basic.something;

import com.facebook.stetho.Stetho;

/**
 * Created by Tangxb on 2017/2/14.
 */

public class MDebugApplication extends MApplication {
    @Override
    public void initDebugSth() {
        initStetho();
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }
}
