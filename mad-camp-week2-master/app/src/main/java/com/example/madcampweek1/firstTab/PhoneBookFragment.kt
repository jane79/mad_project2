package com.example.madcampweek1.firstTab

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcampweek1.MainActivity
import com.example.madcampweek1.R
import kotlinx.android.synthetic.main.fragment_phone_book.*
import kotlinx.android.synthetic.main.fragment_phone_book.view.*

class PhoneBookFragment : Fragment() {

    lateinit var mAdapter: PhoneAdapter
    var phonelist = mutableListOf<Phone>()
    var searchText = ""
    var sortText = "asc"

    val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_open) }
    val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_close) }
    val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.from_bottom) }
    val toBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.to_bottom) }
    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_phone_book, container, false)
        setContentView(view)
        setSearchListener(view)
        setRadioListener(view)

        view.option_add.setOnClickListener {
            onAddButtonClicked()
            setContentView(view)
        }

        view.option_db.setOnClickListener{
            val intent = Intent(context, PhoneDatabaseActivity::class.java)
            context?.startActivity(intent)
        }

        view.option_plus.setOnClickListener{
            val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
                type = ContactsContract.RawContacts.CONTENT_TYPE
            }
            startActivity(intent)
        }

        return view
    }

    private fun setContentView(view: View){
        //phonelist.addAll(getPhoneNumbers(sortText, searchText))
        mAdapter = PhoneAdapter(getPhoneNumbers(sortText, searchText))
        view.recycler.adapter = mAdapter
        view.recycler.layoutManager = LinearLayoutManager(context)
    }

    private fun setSearchListener(view: View) {
        view.editSearch.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                changeList()
            }
        })
    }

    private fun setRadioListener(view: View) {
        view.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.radioAsc -> sortText = "asc"
                R.id.radioDsc -> sortText = "desc"
            }
            changeList()
        }
    }

    fun changeList() {
        val newList = getPhoneNumbers(sortText, searchText)
        this.phonelist.clear()
        this.phonelist.addAll(newList)
        this.mAdapter.notifyDataSetChanged()
    }

    private fun getPhoneNumbers(sort:String, searchName:String?) : List<Phone> {

        val list = mutableListOf<Phone>()
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projections = arrayOf(ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            , ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            , ContactsContract.CommonDataKinds.Phone.NUMBER)
        var wheneClause:String? = null
        var whereValues:Array<String>? = null
        if(searchName?.isNotEmpty() ?: false) {
            wheneClause = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like ?"
            whereValues = arrayOf("%$searchName%")
        }
        val optionSort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " $sort"
        context?.run{
            val cursor = contentResolver.query(phoneUri, projections, wheneClause, whereValues, optionSort)
            while(cursor?.moveToNext()?:false) {
                val id = cursor?.getString(0)
                val name = cursor?.getString(1)
                val number = cursor?.getString(2)
                val phone = Phone(id, name, number)
                list.add(phone)
            }
        }
        return list
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked) {
            option_db.visibility = View.VISIBLE
            option_plus.visibility = View.VISIBLE
        }else {
            option_db.visibility = View.VISIBLE
            option_plus.visibility = View.VISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked) {
            option_db.startAnimation(fromBottom)
            option_plus.startAnimation(fromBottom)
            option_add.startAnimation(rotateOpen)
        }else {
            option_db.startAnimation(toBottom)
            option_plus.startAnimation(toBottom)
            option_add.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean){
        if(!clicked){
            option_db.isClickable = true
            option_plus.isClickable = true
        }else {
            option_db.isClickable = false
            option_plus.isClickable = false
        }
    }
}