package com.example.madcampweek1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exampleList = generateDummyList(500)

        recycler_view.adapter = ExampleAdapter(exampleList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }
    private fun generateDummyList(size: Int): List<ExampleItem> {
        val list = ArrayList<ExampleItem>()

        for (i in 0 until size) {
            val drawable = when (i % 3) {
                0 -> R.drawable.ic_android_head
                1 -> R.drawable.ic_flower
                else -> R.drawable.ic_face
            }
            val item = ExampleItem(drawable, "Item $i", "010-$i$i$i$i-$i$i$i$i")
            list += item
        }
        return list
    }
}