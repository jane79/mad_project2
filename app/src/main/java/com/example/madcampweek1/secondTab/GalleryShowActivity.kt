package com.example.madcampweek1.secondTab

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.madcampweek1.R
import kotlinx.android.synthetic.main.activity_gallery_show.*

class GalleryShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_show)

        val gallery_show: ImageView = gallery_show

        val bundle: Bundle = intent.extras!!

        val drawable = bundle.getInt("Drawable")

        gallery_show.setImageResource(drawable)
    }
}