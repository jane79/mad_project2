package com.example.madcampweek1.secondTab

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.madcampweek1.R
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import java.text.SimpleDateFormat

class GalleryFragment : Fragment() {
    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99
    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_GALLERY = 102

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        val adapter = GalleryAdapter(generateDummyImage(20))
        view.gallery_recycler_view.adapter = adapter

        view.btnTakePhoto.setOnClickListener {
            if(isPermitted(CAMERA_PERMISSION)){
                openCamera()
            } else {
                ActivityCompat.requestPermissions(activity!!, CAMERA_PERMISSION, FLAG_PERM_CAMERA)
            }
        }

        view.btnChoosePhoto.setOnClickListener {
            if(isPermitted(STORAGE_PERMISSION)){
                openGallery()
            } else {
                ActivityCompat.requestPermissions(activity!!, STORAGE_PERMISSION, FLAG_PERM_STORAGE)
            }
        }
        return view
    }
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, FLAG_REQ_CAMERA)
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, FLAG_REQ_GALLERY)
    }
//    fun saveImageFile(fileName: String, mimeType: String, bitmap: Bitmap) : Uri?{
//        val values = ContentValues()
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
//        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            values.put(MediaStore.Images.Media.IS_PENDING, 1)
//        }
//
//        val uri = activity!!.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//
//        try{
//            if(uri != null){
//                var descriptor = activity!!.contentResolver.openFileDescriptor(uri, "w")
//                if (descriptor != null){
//                    val fos = FileOutputStream(descriptor.fileDescriptor)
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
//                    fos.close()
//                    Toast.makeText(activity!!, "uri: $uri", Toast.LENGTH_LONG).show()
//                    return uri
//                }
//            }
//        } catch(e:Exception){
//            Log.e("Camera", e.localizedMessage)
//        }
//        Toast.makeText(activity!!, "uri: null", Toast.LENGTH_SHORT).show()
//        return null
//    }
    fun saveImageFile(bitmap: Bitmap, title:String) : Uri? {
        val savedImageURL = MediaStore.Images.Media.insertImage(activity!!.contentResolver, bitmap, title, "image of $title")
        return Uri.parse(savedImageURL)
//        val values = ContentValues()
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
//        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            values.put(MediaStore.Images.Media.IS_PENDING, 1)
//        }
//
//        val uri = activity!!.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//
//        try{
//            if(uri != null){
//                var descriptor = activity!!.contentResolver.openFileDescriptor(uri, "w")
//                if (descriptor != null){
//                    val fos = FileOutputStream(descriptor.fileDescriptor)
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
//                    fos.close()
//                    Toast.makeText(activity!!, "uri: $uri", Toast.LENGTH_LONG).show()
//                    return uri
//                }
//            }
//        } catch(e:Exception){
//            Log.e("Camera", e.localizedMessage)
//        }
//        Toast.makeText(activity!!, "uri: null", Toast.LENGTH_SHORT).show()
//        return null
    }
    fun newFileName() : String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return filename
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                FLAG_REQ_CAMERA -> {
                    if (data?.extras?.get("data") != null) {
                        val bitmap = data.extras?.get("data") as Bitmap
                        val filename = newFileName()

                        val uri = saveImageFile(bitmap, filename)
                        Toast.makeText(activity, "saved: $uri", Toast.LENGTH_LONG).show()
                        imagePreview.setImageURI(uri)
                    }
                }
                FLAG_REQ_GALLERY -> {
                    val uri = data?.data
                    imagePreview.setImageURI(uri)
                }
            }
        }
    }
    private fun isPermitted(permissions: Array<String>) : Boolean {
        for(perm in permissions){
            val result = ContextCompat.checkSelfPermission(activity!!, perm)
            if(result != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            FLAG_PERM_CAMERA -> {
                var checked = true
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        checked = false
                        break
                    }
                }
                if (checked) {
                    openCamera()
                }
            }
        }
    }


    private fun generateDummyImage(size: Int): ArrayList<GalleryItem> {
        val list = ArrayList<GalleryItem>()
        for (i in 1 until size) {
            val drawable = when (i) {
                1 -> R.drawable.cat1
                2 -> R.drawable.cat2
                3 -> R.drawable.cat3
                4 -> R.drawable.cat4
                5 -> R.drawable.cat5
                6 -> R.drawable.cat6
                7 -> R.drawable.cat7
                else -> R.drawable.cat7
            }
            val item = GalleryItem(drawable, "cat$i")
            list += item
        }
        return list
    }
}