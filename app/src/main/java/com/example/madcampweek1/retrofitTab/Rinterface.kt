package com.example.madcampweek1.retrofitTab

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface PhoneService{

    @FormUrlEncoded
    @POST("/phone/upload")
    fun phoneUpload(
        @Field("name") name:String,     // input
        @Field("number") number:String
    ) : Call<PhoneInf>         // output, Login이라는 output

    @FormUrlEncoded
    @POST("/phone/delete")
    fun phoneDelete(
        @Field("name") name:String,     // input
        @Field("number") number:String
    ) : Call<PhoneInf>         // output, Login이라는 output


    @POST("/phone/download/all")
    fun phoneDownloadAll() : Call<Array<PhoneInf>>         // output, Login이라는 output

}

interface GalleryService {

    @FormUrlEncoded
    @POST("/image/upload")
    fun imageUpload(
        @Field("name") name:String,     // input
        @Field("file") file:String
    ) : Call<ImageInf>         // output, Login이라는 output

    @POST("/image/download/all")
    fun galleryDownloadAll() : Call<Array<ImageInf>>
}

/////////facebook
interface LoginService{
    @FormUrlEncoded
    @POST("login/facebook")
    fun postUser(
        @Field("fbUserData") fbUserData: JSONObject
    ):Call<LoginInf>
}
/////////facebook
