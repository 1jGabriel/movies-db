package db.movies.movies.remotey

import db.movies.movies.BuildConfig
import db.movies.movies.remote.MoviesService
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {


        private val httpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val url = original.url()
                            .newBuilder()
                            .addQueryParameter("api_key", "2b6764990a31d19390eb249cffaca862")
                            .addQueryParameter("language", "pt-BR")
                            .build()
                    val requestBuilder = original.newBuilder().url(url)
                    chain.proceed(requestBuilder.build())
                }
                .build()

        fun getRetrofit() = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BuildConfig.SERVER_URL)
                    .client(httpClient)
                    .build()

        fun getClient() = getRetrofit().create(MoviesService::class.java)
    }
}