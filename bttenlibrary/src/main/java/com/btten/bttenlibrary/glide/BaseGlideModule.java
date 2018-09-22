package com.btten.bttenlibrary.glide;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * function:
 * author: frj
 * modify date: 2018/9/7
 */
@GlideModule
public class BaseGlideModule extends AppGlideModule {
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
