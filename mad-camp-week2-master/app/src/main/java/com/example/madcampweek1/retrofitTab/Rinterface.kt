package com.example.madcampweek1.retrofitTab

import okhttp3.MultipartBody
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

    @FormUrlEncoded
    @POST("/phone/download/all")
    fun phoneDownloadAll(
        @Field("name") name:String     // input
    ) : Call<Array<PhoneInf>>         // output, Login이라는 output

}

interface GalleryService {

    @FormUrlEncoded
    @POST("/image/upload")
    fun imageUpload(
        @Field("name") name:String,     // input
        @Field("file") file:MultipartBody.Part
    ) : Call<ImageInf>         // output, Login이라는 output

    @FormUrlEncoded
    @POST("/image/download")
    fun imageDownload(
        @Field("name") name:String     // input
    ) : Call<ImageInf>         // output, Login이라는 output

}

interface ScheduleService {

    @FormUrlEncoded
    @POST("/schedule/upload")
    fun scheduleUpload(
        @Field("name") name:String,     // input
        @Field("file") file:String
    ) : Call<ScheduleInf>         // output, Login이라는 output

    @FormUrlEncoded
    @POST("/schedule/download/all")
    fun scheduleDownloadAll(
        @Field("name") name:String  // input
    ) : Call<Array<ScheduleInf>>         // output, Login이라는 output

}
