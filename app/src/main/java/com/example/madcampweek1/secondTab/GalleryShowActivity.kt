package com.example.madcampweek1.secondTab

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcampweek1.R
import kotlinx.android.synthetic.main.activity_gallery_show.*

class GalleryShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_show)

        val bundle: Bundle = intent.extras!!
        val drawable = bundle.getInt("Drawable")
        expanded_image.setImageResource(drawable)

        setContentView()
    }

    private fun setContentView() {
        val mAdapter = GalleryShowAdapter(generateDummyImage(20))
        show_recycler.adapter = mAdapter
        show_recycler.layoutManager = LinearLayoutManager(this)

        mAdapter.itemClick = object: GalleryShowAdapter.ItemClick {
            override fun onClick(view: View, item: GalleryItem) {
                expanded_image.setImageResource(item.imageResource)
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
