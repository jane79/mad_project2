package com.example.madcampweek1

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val exampleList = generateDummyList(500)
//
//        recycler_view.adapter = ExampleAdapter(exampleList)
//        recycler_view.layoutManager = LinearLayoutManager(this)
//        recycler_view.setHasFixedSize(true)
////////////////////////////////////////////////////////////////
        val adapter = FragmentAdapter(this)
        val fragments = listOf<Fragment>(FirstFragment(), SecondFragment(), ThirdFragment())
        val tabTitles = listOf<String>("Contacts", "Gallery", "Game")
        adapter.fragments.addAll(fragments)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

//    private fun generateDummyList(size: Int): List<ExampleItem> {
//        val list = ArrayList<ExampleItem>()
//
//        for (i in 0 until size) {
//            val drawable = when (i % 3) {
//                0 -> R.drawable.ic_android_head
//                1 -> R.drawable.ic_flower
//                else -> R.drawable.ic_face
//            }
//            val item = ExampleItem(drawable, "Item $i", "010-$i$i$i$i-$i$i$i$i")
//            list += item
//        }
//        return list
//    }
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