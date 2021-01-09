package com.example.madcampweek1.retrofitTab

import android.content.ContentValues.TAG
import android.util.Log
import com.example.madcampweek1.utils.API
import com.example.madcampweek1.retrofitTab.IRetrofit
import com.example.madcampweek1.utils.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit : IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit) {
        val term = searchTerm ?: ""     // searchTerm의 값이 없으면 ""넣고 아니면 그대로
        val call = iRetrofit?.searchPhotos(searchTerm = term) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")
                completion(RESPONSE_STATE.OKAY, t.toString())
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / t: ${response.raw()}")
                completion(RESPONSE_STATE.OKAY, response.raw().toString())
            }
        })
    }

    fun postToken(token: String?, completion: (RESPONSE_STATE, String) -> Unit) {
        val term = token ?: ""     // searchTerm의 값이 없으면 ""넣고 아니면 그대로
        val call = iRetrofit?.postToken(token = term) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")
                completion(RESPONSE_STATE.OKAY, t.toString())
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / t: ${response.raw()}")
                completion(RESPONSE_STATE.OKAY, response.raw().toString())
            }
        })
    }


}