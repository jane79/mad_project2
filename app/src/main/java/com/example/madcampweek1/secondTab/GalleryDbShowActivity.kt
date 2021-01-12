package com.example.madcampweek1.secondTab

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import com.example.madcampweek1.R
import com.example.madcampweek1.retrofitTab.GalleryService
import com.example.madcampweek1.retrofitTab.ImageInf
import kotlinx.android.synthetic.main.activity_gallery_db_show.*
import kotlinx.android.synthetic.main.activity_gallery_show.expanded_image
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream

class GalleryDbShowActivity : AppCompatActivity() {
    var imageInf: ImageInf? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_db_show)

        val bundle: Bundle = intent.extras!!
        val file = bundle.getString("File")
        val filename = bundle.getString("Name")
        val idx = bundle.getInt("Idx")
        var dString = android.util.Base64.decode(file, android.util.Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(dString, 0, dString.size)
        expanded_image.setImageBitmap(decodedImage)

        //setContentView()

        btn_download.setOnClickListener{
            try {
                val iStream = ByteArrayInputStream(dString)
                var image = BitmapFactory.decodeStream(iStream)
                MediaStore.Images.Media.insertImage(contentResolver, image, "Title", null)
            }catch(e : Exception){
                Log.v("SaveFile",""+e)
            }
        }
    }
}