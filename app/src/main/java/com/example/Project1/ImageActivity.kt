package com.example.project1
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.focus_image.*
import org.jetbrains.anko.toast


public class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.focus_image)
        val intent = getIntent()
        val imageUri = intent.getStringExtra("uri")
        val imageList = intent.getStringArrayListExtra("imagelist")
        val imageView : ImageView = findViewById(R.id.imageView)
        if (imageUri != null) {
            setImage(imageView, imageUri, imageList)
        }
        else{
            toast("ERROR")
            finish()
        }
        //btnBack.setOnClickListener{
        //    finish()
        //}
    }
    private fun setImage(imageView : ImageView, imageUri : String, imageList : ArrayList<String>?){
        val mAdapter = imageList?.let { ImageShowAdapter(it) }
        show_recycler.adapter = mAdapter
        show_recycler.layoutManager = LinearLayoutManager(this)

        if (mAdapter != null) {
            mAdapter.itemClick = object: ImageShowAdapter.ItemClick {
                override fun onClick(view: View, item: String) {
                    imageView.setImageURI(Uri.parse(item))
                }
            }
        }

        Log.d("test", "URI: $imageUri")
        try {
            imageView.setImageURI(Uri.parse(imageUri))
        }catch(e: Exception){
            toast("ERROR")
            finish()
        }
    }
}