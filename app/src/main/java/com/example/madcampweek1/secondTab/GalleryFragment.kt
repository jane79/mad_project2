package com.example.madcampweek1.secondTab

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.madcampweek1.R
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import java.text.SimpleDateFormat

class GalleryFragment : Fragment() {
    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99
    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_GALLERY = 102

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        val adapter = GalleryAdapter(generateDummyImage(40))
        view.gallery_recycler_view.adapter = adapter

        view.btnTakePhoto.setOnClickListener { openCamera() }
        view.btnChoosePhoto.setOnClickListener { openGallery() }

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
    private fun saveImageFile(bitmap: Bitmap, title: String) : Uri? {
        val savedImageURL = MediaStore.Images.Media.insertImage(
            activity!!.contentResolver,
            bitmap,
            title,
            "image of $title"
        )
        return Uri.parse(savedImageURL)
    }
    private fun newFileName() : String {
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
                        Toast.makeText(activity, "사진을 저장했습니다.", Toast.LENGTH_LONG).show()
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
                if (checked) { openCamera() }
            }
            FLAG_PERM_STORAGE -> {
                var checked = true
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        checked = false
                        break
                    }
                }
                if (checked) { openGallery() }
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
                8 -> R.drawable.cat8
                9 -> R.drawable.cat9
                10 -> R.drawable.cat10
                11 -> R.drawable.cat11
                12 -> R.drawable.cat12
                13 -> R.drawable.cat13
                14 -> R.drawable.cat14
                15 -> R.drawable.cat1
                16 -> R.drawable.cat2
                17 -> R.drawable.cat3
                18 -> R.drawable.cat4
                19 -> R.drawable.cat5
                20 -> R.drawable.cat6
                21 -> R.drawable.cat7
                22 -> R.drawable.cat8
                23 -> R.drawable.cat9
                24 -> R.drawable.cat10
                25 -> R.drawable.cat11
                26 -> R.drawable.cat12
                27 -> R.drawable.cat13
                28 -> R.drawable.cat14
                29 -> R.drawable.cat1
                30 -> R.drawable.cat2
                31 -> R.drawable.cat3
                32 -> R.drawable.cat4
                33 -> R.drawable.cat5
                34 -> R.drawable.cat6
                35 -> R.drawable.cat7
                36 -> R.drawable.cat8
                37 -> R.drawable.cat9
                38 -> R.drawable.cat10
                39 -> R.drawable.cat11
                else -> R.drawable.cat12
            }
            val item = GalleryItem(drawable, "cat$i")
            list += item
        }
        return list
    }
}