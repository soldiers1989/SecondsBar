package com.fx.secondbar.config;

import com.btten.bttenlibrary.base.config.ABaseConfig;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.http.interceptor.CurrencyInterceptor;
import com.fx.secondbar.util.Constants;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

/**
 * function:
 * author: frj
 * modify date: 2018/9/7
 */
public class BaseConfig extends ABaseConfig
{
    @Override
    protected String getReleaseUrl()
    {
        return Constants.RELEASE_URL;
    }

    @Override
    protected String getDebugUrl()
    {
        return Constants.DEBUG_URL;
    }

    @Override
    protected String getRootDir()
    {
        return Constants.ROOT_DIR;
    }

    @Override
    public List<Interceptor> getInterceptors()
    {
        List<Interceptor> interceptors = super.getInterceptors();
        if (interceptors == null)
        {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new CurrencyInterceptor(FxApplication.getInstance()));
        return interceptors;
    }
}
