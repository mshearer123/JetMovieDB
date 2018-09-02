package com.shearer.jetmoviedb.shared.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.nio.charset.Charset

class ConverterFactory : Converter.Factory() {

    override fun requestBodyConverter(type: Type?,
                                      parameterAnnotations: Array<out Annotation>?,
                                      methodAnnotations: Array<out Annotation>?,
                                      retrofit: Retrofit?): Converter<*, RequestBody>? {
        val adapter = GSON.getAdapter(TypeToken.get(type))
        return RequestBodyConverter(adapter, GSON)
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?,
                                       retrofit: Retrofit?): Converter<ResponseBody, *>? {
        val adapter = GSON.getAdapter(TypeToken.get(type))
        return ResponseBodyConverter(adapter, GSON)
    }

    private companion object {
        private val GSON: Gson = GsonBuilder().create()
    }
}

private class RequestBodyConverter<T>(private val adapter: TypeAdapter<T>, private val gson: Gson) : Converter<T, RequestBody> {

    override fun convert(value: T): RequestBody {
        val buffer = Buffer()
        val writer = buffer.outputStream().writer(CHARSET_UTF_8)
        gson.newJsonWriter(writer).use {
            adapter.write(it, value)
        }
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString())
    }

    private companion object {
        private val MEDIA_TYPE = MediaType.parse("application/json")
        private val CHARSET_UTF_8 = Charset.forName("UTF-8")
    }
}

private class ResponseBodyConverter<T>(private val adapter: TypeAdapter<T>, private val gson: Gson) : Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T {
        return value.use {
            adapter.read(gson.newJsonReader(value.charStream()))
        }
    }
}