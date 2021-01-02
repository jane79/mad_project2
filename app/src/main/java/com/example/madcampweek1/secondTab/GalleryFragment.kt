package com.example.madcampweek1.secondTab

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.madcampweek1.R
import kotlinx.android.synthetic.main.fragment_gallery.view.*

class GalleryFragment : Fragment() {

//    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
//    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    val FLAG_PERM_CAMERA = 98
//    val FLAG_PERM_STORAGE = 99
//    val FLAG_REQ_CAMERA = 101

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        val adapter = GalleryAdapter(generateDummyImage(20))
        view.gallery_recycler_view.adapter = adapter

        return view
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
// 카메라 버튼 시도
//    fun isPermitted(permissions: Array<String>) : Boolean {
//        for(permission in permissions){
////            val result = ContextCompat.checkSelfPermission(this, permission)
//            val result = activity?.let { ContextCompat.checkSelfPermission(it, permission) }
//            if(result != PackageManager.PERMISSION_GRANTED) {
//                return false
//            }
//        }
//        return true
//    }
//
//    fun openCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, FLAG_REQ_CAMERA)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == Activity.RESULT_OK) {
//            when(requestCode){
//                FLAG_REQ_CAMERA -> {
//                    val bitmap = data?.extras?.get("data") as Bitmap
//                    imagePreview.setImageBitmap(bitmap)
//                }
//            }
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        when(requestCode){
//            FLAG_PERM_STORAGE -> {
//                var checked = true
//                for (grant in grantResults){
//                    if(grant != PackageManager.PERMISSION_GRANTED) {
//                        checked = false
//                        break
//                    }
//                }
//                if(checked) {
//                    openCamera()
//                }
//            }
//        }
//    }
}