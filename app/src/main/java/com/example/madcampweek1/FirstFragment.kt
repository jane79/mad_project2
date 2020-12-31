package com.example.madcampweek1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_first.view.*

class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val adapter = ExampleAdapter(generateDummyList(500))
        view.recyclerView.adapter = adapter
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }
//    val exampleList = generateDummyList(500)
//    recycler_view.adapter = ExampleAdapter(exampleList)
//    recycler_view.layoutManager = LinearLayoutManager(this)
//    recycler_view.setHasFixedSize(true)
    private fun generateDummyList(size: Int): List<ContactItem> {
        val list = ArrayList<ContactItem>()
        for (i in 0 until size) {
            val drawable = when (i % 3) {
                0 -> R.drawable.ic_android_head
                1 -> R.drawable.ic_flower
                else -> R.drawable.ic_face
            }
            val item = ContactItem(drawable, "Item $i", "010-$i$i$i$i-$i$i$i$i")
            list += item
        }
        return list
    }
}