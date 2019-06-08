package com.hufs.cdt;

import android.content.Context;

import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

// new since Glide v4
@GlideModule
public final class MyAppGlideModule extends AppGlideModule {
//error catch를 위해 가지고 온 것입니다.
    public void registerComponents(Context context, Registry registry) {
        // Register FirebaseImageLoader to handle StorageReference
        registry.append(StorageReference.class, InputStream.class,
                new FirebaseImageLoader.Factory());
    }
}