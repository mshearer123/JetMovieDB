package com.shearer.jetmoviedb.shared.http

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.shearer.jetmoviedb.BuildConfig
import com.shearer.jetmoviedb.features.movie.common.repository.MovieDbApi
import com.shearer.jetmoviedb.features.movie.common.repository.MovieDbConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


interface RestService {
    fun createMovieApi(): MovieDbApi.Dao
}

class RestServiceDefault(private val converterFactory: Converter.Factory) : RestService {
    override fun createMovieApi(): MovieDbApi.Dao {
        return createRetrofit(MovieDbConstants.baseUrl, MovieDbApiKeyInterceptor()).create(MovieDbApi.Dao::class.java)
    }

    private fun createRetrofit(baseUrl: String, interceptor: Interceptor? = null): Retrofit {

        val okHttpBuilder = OkHttpClient.Builder()
        interceptor?.let {
            okHttpBuilder.addInterceptor(it)
        }

        okHttpBuilder.addStethoInterceptor()

        return Retrofit.Builder()
                .client(okHttpBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .baseUrl(baseUrl)
                .build()
    }

    private fun OkHttpClient.Builder.addStethoInterceptor(): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            addNetworkInterceptor(StethoInterceptor())
        }
        return this
    }

}