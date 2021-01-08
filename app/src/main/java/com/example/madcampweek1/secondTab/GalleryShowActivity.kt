package com.example.madcampweek1.secondTab

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcampweek1.R
import kotlinx.android.synthetic.main.activity_gallery_show.*
import java.util.*
import kotlin.collections.ArrayList

class GalleryShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_show)

        val bundle: Bundle = intent.extras!!
        val uri = Uri.parse(bundle.getString("Uri"))
        expanded_image.setImageURI(uri)

        setContentView()
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
