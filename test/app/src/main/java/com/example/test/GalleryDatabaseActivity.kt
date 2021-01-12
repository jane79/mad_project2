package com.example.madcampweek1.secondTab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.madcampweek1.R
import com.example.madcampweek1.retrofitTab.GalleryService
import com.example.madcampweek1.retrofitTab.ImageInf
import kotlinx.android.synthetic.main.activity_gallery_database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GalleryDatabaseActivity : AppCompatActivity() {

    lateinit var mAdapter: GalleryDatabaseAdapter
    var dblist = mutableListOf<Image>()

    var ArrayimgInf:Array<ImageInf>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_database)
        setContentView()
    }

    private fun setContentView(){
        val list = mutableListOf<Image>()
        fun getInfo(){
            var retrofit = Retrofit.Builder()
                .baseUrl("http://192.249.18.217:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            var galleryService: GalleryService = retrofit.create(GalleryService::class.java)

            Log.d("gallerydb", "hear")
            galleryService.galleryDownloadAll().enqueue(object : Callback<Array<ImageInf>> {
                override fun onFailure(call: Call<Array<ImageInf>>, t: Throwable) {
                    Log.d("fail1", t.toString())
                }
                override fun onResponse(
                    call: Call<Array<ImageInf>>,
                    response: Response<Array<ImageInf>>
                ){
                    ArrayimgInf = response.body()
                    var i =0
                    var len = ArrayimgInf?.size
                    while(i < len!!){
                        list.add(
                            Image(
                                ArrayimgInf?.get(i)?.name,
                                ArrayimgInf?.get(i)?.file
                            )
                        )
                        Log.d("Added Image",  ArrayimgInf?.get(i)?.file.toString())
                        i++
                    }
                    Log.d("Download Image", "Finish")
                    dblist.addAll(list)
                    recycler.adapter = GalleryDatabaseAdapter(dblist)

                }
            })
        }
        getInfo()
    }

}