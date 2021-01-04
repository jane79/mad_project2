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
}