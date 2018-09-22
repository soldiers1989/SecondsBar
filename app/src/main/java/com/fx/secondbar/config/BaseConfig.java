package com.fx.secondbar.config;

import com.btten.bttenlibrary.base.config.ABaseConfig;
import com.fx.secondbar.util.Constants;

/**
 * function:
 * author: frj
 * modify date: 2018/9/7
 */
public class BaseConfig extends ABaseConfig {
    @Override
    protected String getReleaseUrl() {
        return Constants.RELEASE_URL;
    }

    @Override
    protected String getDebugUrl() {
        return Constants.DEBUG_URL;
    }

    @Override
    protected String getRootDir() {
        return Constants.ROOT_DIR;
    }
}
