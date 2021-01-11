package com.example.viewpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = FragmentAdapter(this)
        val fragments = listOf<Fragment>(OneFragment(), TwoFragment(), ThreeFragment())
        adapter.fragments.addAll(fragments)

        viewpager.adapter = adapter
        TabLayoutMediator(tabLayout, viewpager)
    }
}

class FragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    val fragments = mutableListOf<Fragment>()
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}