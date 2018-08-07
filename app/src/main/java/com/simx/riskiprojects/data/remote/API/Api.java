package com.simx.riskiprojects.data.remote.API;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.simx.riskiprojects.BuildConfig;
import com.simx.riskiprojects.data.model.ResponsePlace;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * User: simx Date: 08/08/18 1:01
 */
public interface Api {
	@Headers({"Accept: application/json", "Content-type: application/json"})
	@GET("place/nearbysearch/json")
	Observable<ResponsePlace> getPlaceNearby(
			@Query("key") String key,
			@Query("type") String type,
			@Query("radius") int radius,
			@Query("location") String location,
			@Query("keyword") String keyword
	);
	@Headers({"Accept: application/json", "Content-type: application/json"})
	@GET("place/nearbysearch/json")
	Observable<ResponsePlace> getAll(
			@Query("key") String key,
			@Query("type") String type,
			@Query("radius") int radius,
			@Query("location") String location
	);


	class Factory {
		private static Retrofit retrofit = null;
		public static Retrofit getRetrofit(){
			return retrofit;
		}

		public static Api create() {
			return getRetrofitConfig().create(Api.class);
		}
		public static Retrofit getRetrofitConfig() {
			return new Retrofit.Builder()
					.baseUrl(BuildConfig.BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.client(client())
					.build();
		}

		private static OkHttpClient client() {
			return new OkHttpClient.Builder()
					.readTimeout(60, TimeUnit.SECONDS)
					.connectTimeout(60, TimeUnit.SECONDS)
					.addInterceptor(new HttpLoggingInterceptor()
							.setLevel(HttpLoggingInterceptor.Level.BODY)
					)
					.build();
		}
	}
}
