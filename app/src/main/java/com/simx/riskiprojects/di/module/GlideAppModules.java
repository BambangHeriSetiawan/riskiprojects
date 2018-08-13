package com.simx.riskiprojects.di.module;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import dagger.Module;

/**
 * User: simx Date: 09/08/18 11:42
 */
@Module
@GlideModule
public class GlideAppModules extends AppGlideModule {
	@Override
	public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
		super.applyOptions(context, builder);
	}
}
