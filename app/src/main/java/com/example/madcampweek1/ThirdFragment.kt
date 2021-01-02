package com.example.madcampweek1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_third.view.*

class ThirdFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_third, container, false)
        val btnOpenActivity: Button = view.btn_open_new_activity
        btnOpenActivity.setOnClickListener{
            val intent = Intent(activity, GalleryShowActivity :: class.java)
            activity?.startActivity(intent)
//            startActivity(intent)
        }
        return view
    }
}