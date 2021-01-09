package com.example.madcampweek1.retrofitTab

import com.example.madcampweek1.utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IRetrofit {
    // http://www.unsplash.com/search/photos/?query="여기"   여기 가 searchTerm에 들어감
    @GET(API.SEARCH_PHOTOS)
    fun searchPhotos(@Query("query") searchTerm: String) : Call<JsonElement>

    @POST(API.POST_TOKEN)
    fun postToken(@Query("query") token: String) : Call<JsonElement>
}