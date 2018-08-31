package com.shearer.jetmoviedb.shared

import com.shearer.jetmoviedb.shared.http.ConverterFactory
import com.shearer.jetmoviedb.shared.http.RestService
import com.shearer.jetmoviedb.shared.http.RestServiceDefault
import org.koin.dsl.module.module
import retrofit2.Converter

val sharedModule = module {
    single { ConverterFactory() as Converter.Factory }
    single { RestServiceDefault(get()) as RestService }
}