//package com.btten.bttenlibrary.util.glide;
//
//import android.content.Context;
//
//import com.bumptech.glide.Registry;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
//import com.bumptech.glide.load.model.GlideUrl;
//import com.bumptech.glide.module.LibraryGlideModule;
//
//import java.io.InputStream;
//
///**
// * function:使Glide使用OkHttp加载
// * author: frj
// * modify date: 2017/6/8
// */
//@GlideModule
//public final class OkHttpLibraryGlideModule extends LibraryGlideModule
//{
//    @Override
//    public void registerComponents(Context context, Registry registry)
//    {
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
//    }
//}
