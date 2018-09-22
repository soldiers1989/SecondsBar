package com.fx.secondbar.application;

import com.btten.bttenlibrary.application.BtApplication;
import com.fx.secondbar.BuildConfig;
import com.fx.secondbar.config.BaseConfig;

/**
 * function:
 * author: frj
 * modify date: 2018/9/7
 */
public class FxApplication extends BtApplication {
    @Override
    protected void setBaseConfig() {
        mAbaseConfig = new BaseConfig();
    }

    @Override
    protected void setDebug() {
        isDebug = BuildConfig.DEBUG;
    }
}
