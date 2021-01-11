package com.example.madcampweek1.secondTab

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcampweek1.R
import com.example.madcampweek1.retrofitTab.GalleryService
import com.example.madcampweek1.retrofitTab.ImageInf
import com.example.madcampweek1.retrofitTab.PhoneInf
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_gallery_show.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class GalleryShowActivity : AppCompatActivity() {
    var imageInf:ImageInf? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_show)

        val bundle: Bundle = intent.extras!!
        val uri = Uri.parse(bundle.getString("Uri"))
        val filename = bundle.getString("Name")
        expanded_image.setImageURI(uri)

        setContentView()

        val path = getRealPathFromURI(uri)
        val file = File(path)

        var requestBody : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file",filename,requestBody)

        //The gson builder
        var gson : Gson =  GsonBuilder()
            .setLenient()
            .create()

        var retrofit = Retrofit.Builder()
            .baseUrl("http://192.249.18.225:5000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        var galleryService: GalleryService = retrofit.create(GalleryService::class.java)

        btn_upload.setOnClickListener{
            if (filename != null) {
                galleryService.imageUpload(filename,body).enqueue(object: Callback<ImageInf> {
                    override fun onFailure(call: Call<ImageInf>, t: Throwable) {
                        Log.d("fail", t.toString())
                        var dialog = AlertDialog.Builder(this@GalleryShowActivity)
                        dialog.setMessage("호출 실패했습니다.")
                        dialog.show()
                    }

                    override fun onResponse(call: Call<ImageInf>, response: Response<ImageInf>) {
                        Log.d("success", body.toString())
                        imageInf = response.body()
                        var dialog = AlertDialog.Builder(this@GalleryShowActivity)
                        dialog.setMessage("업로드 성공!")
                        dialog.show()
                    }
                })
            }
        }

//        expanded_image.setOnClickListener{
//            galleryService.imageUpload(text1,text2).enqueue(object: Callback<phoneInf> {
//                override fun onFailure(call: Call<phoneInf>, t: Throwable) {
//                    Log.d("fail", t.toString())
//                    var dialog = AlertDialog.Builder(this@GalleryShowActivity)
//                    dialog.setMessage("호출 실패했습니다.")
//                    dialog.show()
//                }
//
//                override fun onResponse(call: Call<phoneInf>, response: Response<phoneInf>) {
//                    phoneInf = response.body()
//                    var dialog = AlertDialog.Builder(this@GalleryShowActivity)
//                    dialog.setMessage("업로드 성공!")
//                    dialog.show()
//                }
//            })
//
//        }
    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val filePath: String?
        val cursor: Cursor? =
            this.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            filePath = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            filePath = cursor.getString(idx)
            cursor.close()
        }
        return filePath
    }


    private fun setContentView() {
        val mAdapter = GalleryShowAdapter(getFileList(this, MediaStoreFileType.IMAGE))
        //val mAdapter = GalleryShowAdapter(generateDummyImage(40))
        show_recycler.adapter = mAdapter
        show_recycler.layoutManager = LinearLayoutManager(this)

        mAdapter.itemClick = object: GalleryShowAdapter.ItemClick {
            override fun onClick(view: View, item: GalleryItem) {
                expanded_image.setImageURI(item.imageResource)
            }
        }
    }
    private fun getFileList(context: Context, type: MediaStoreFileType): ArrayList<GalleryItem> {
        val imageList = ArrayList<GalleryItem>()
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_TAKEN,
            MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Files.FileColumns.BUCKET_ID
        )
        val sortOrder = "${MediaStore.Files.FileColumns.DATE_TAKEN} DESC"
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val dateTakenColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_TAKEN)
            val displayNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val bucketIDColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_ID)
            val bucketNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val dateTaken = Date(cursor.getLong(dateTakenColumn))
                val displayName = cursor.getString(displayNameColumn)
                val contentUri = Uri.withAppendedPath(
                    type.externalContentUri,
                    id.toString()
                )
                val bucketID = cursor.getLong(bucketIDColumn)
                val bucketName = cursor.getString(bucketNameColumn)
                imageList.add(GalleryItem(contentUri, "image"))
            }
        }
        return imageList
    }

    enum class MediaStoreFileType(
        val externalContentUri: Uri,
        val mimeType: String,
        val pathByDCIM: String
    ) {
        IMAGE(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*", "/image"),
    }
}
