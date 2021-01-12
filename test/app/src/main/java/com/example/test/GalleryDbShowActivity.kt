package com.example.madcampweek1.secondTab

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import com.example.madcampweek1.R
import com.example.madcampweek1.retrofitTab.GalleryService
import com.example.madcampweek1.retrofitTab.ImageInf
import kotlinx.android.synthetic.main.activity_gallery_db_show.*
import kotlinx.android.synthetic.main.activity_gallery_show.expanded_image
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GalleryDbShowActivity : AppCompatActivity() {
    var imageInf: ImageInf? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_db_show)

        val bundle: Bundle = intent.extras!!
        val file = bundle.getString("File")
        val filename = bundle.getString("Name")
        var dString = android.util.Base64.decode(file, android.util.Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(dString, 0, dString.size)
        expanded_image.setImageBitmap(decodedImage)

        //setContentView()

        btn_download.setOnClickListener{
            var retrofit = Retrofit.Builder()
                .baseUrl("http://192.249.18.217:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            var galleryService: GalleryService = retrofit.create(GalleryService::class.java)
        }
    }
}