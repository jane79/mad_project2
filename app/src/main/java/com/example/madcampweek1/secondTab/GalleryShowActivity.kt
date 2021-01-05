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
        val mAdapter = GalleryShowAdapter(generateDummyImage(40))
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
