package com.example.madcampweek1.retrofitTab

import com.example.madcampweek1.utils.API
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // 레트로핏 클라이언트 선언
    private var retrofitClient : Retrofit? = null

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseURL: String): Retrofit? {

        // okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        val baseParameterInterceptor : Interceptor = (object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val addedUrl = originalRequest.url.newBuilder().addQueryParameter("client_id", API.CLIENT_ID).build()
                val finalRequest = originalRequest.newBuilder()
                                        .url(addedUrl)
                                        .method(originalRequest.method, originalRequest.body)
                                        .build()
                return chain.proceed(finalRequest)
            }
        })

        client.addInterceptor(baseParameterInterceptor)


        if(retrofitClient == null) {                //retrofitClient가 없으면
            retrofitClient = Retrofit.Builder()     // 만들어줌
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                //
                //.client(client.build())
                .build()
        }
        return retrofitClient
    }
}