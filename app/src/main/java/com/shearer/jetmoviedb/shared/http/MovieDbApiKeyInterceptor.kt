package com.shearer.jetmoviedb.shared.http

import com.shearer.jetmoviedb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class MovieDbApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalUrl = request.url()

        val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
                .build()

        return chain.proceed(request.newBuilder().url(url).build())
    }
}