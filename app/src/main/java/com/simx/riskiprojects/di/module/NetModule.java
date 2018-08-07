package com.simx.riskiprojects.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * User: simx Date: 07/08/18 14:58
 */
@Module
public class NetModule {
	private static String MAP_BASE_URL ="https://maps.googleapis.com/maps/api/";
	private static String AUTH_BASE_URL ="https://lara.lesgood.com/api/";
	private static String PAYMENT_BASE_URL= "";
	@Provides
	@Singleton
	SharedPreferences providesSharedPreferences(Application application) {
		return PreferenceManager.getDefaultSharedPreferences(application);
	}

	@Provides
	@Singleton
	Cache provideHttpCache(Application application) {
		int cacheSize = 10 * 1024 * 1024;
		Cache cache = new Cache (application.getCacheDir(), cacheSize);
		return cache;
	}
	@Provides
	@Singleton
	Gson provideGson() {
		GsonBuilder gsonBuilder = new GsonBuilder ();
		gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		return gsonBuilder.create();
	}

	@Provides
	@Singleton
	OkHttpClient provideOkhttpClient(Cache cache) {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		return new OkHttpClient.Builder().cache(cache).addInterceptor(interceptor)
				.connectTimeout(5, TimeUnit.MINUTES)
				.writeTimeout(5, TimeUnit.MINUTES)
				.cache(cache).build();
	}
	@Provides
	@Named("payment")
	@Singleton
	Retrofit provideRetrofit( Gson gson, OkHttpClient okHttpClient) {
		return new Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.baseUrl(PAYMENT_BASE_URL)
				.client(okHttpClient)
				.build();
	}

	@Provides
	@Named("second")
	@Singleton
	Retrofit provideRetrofitSecond(Gson gson, OkHttpClient okHttpClient) {
		return new Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.baseUrl(MAP_BASE_URL)
				.client(okHttpClient)
				.build();
	}
  /*@Provides
    @Singleton
    ApiService provideApiservice(@Named("payment")Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }
    @Provides
    @Singleton
    MapApiService provideMapApiService(@Named("second")Retrofit retrofit){
        return retrofit.create(MapApiService.class);
    }*/

}
