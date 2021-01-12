package com.example.madcampweek1.thirdTab

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.madcampweek1.R
import com.example.madcampweek1.firstTab.PhoneDatabaseActivity
import com.example.madcampweek1.retrofitTab.PhoneInf
import com.example.madcampweek1.retrofitTab.PhoneService
import com.example.madcampweek1.retrofitTab.ScheduleInf
import com.example.madcampweek1.retrofitTab.ScheduleService
import com.example.madcampweek1.secondTab.GalleryShowActivity
import com.github.tlaabs.timetableview.Schedule
import kotlinx.android.synthetic.main.activity_phone_book_show.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import okhttp3.internal.cache2.Relay.Companion.edit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.*

class ScheduleFragment : Fragment() {

    val REQUEST_ADD = 1
    val REQUEST_EDIT = 2
    val RESULT_OK_ADD = 1
    val RESULT_OK_EDIT = 2
    val RESULT_OK_DELETE = 3

    var scheduleInf:ScheduleInf? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        view.save_btn.setOnClickListener {

            var dialog = AlertDialog.Builder(requireContext())
            dialog.setMessage("그룹 이름을 입력해주세요")
            var edit : EditText = EditText(context)
            dialog.setView(edit)
            dialog.setPositiveButton("확인"){ _, _ ->
                save(view.timetable.createSaveData(), edit.text.toString())
            }
            dialog.show()

//            save(view.timetable.createSaveData())
        }
        view.load_btn.setOnClickListener {
            var dialog = AlertDialog.Builder(requireContext())
            dialog.setMessage("찾는 그룹 이름을 입력해주세요")
            var edit : EditText = EditText(context)
            dialog.setView(edit)
            dialog.setPositiveButton("확인"){ _, _ ->
                loadSavedData(view, edit.text.toString())
            }
            dialog.show()

        }
        view.add_btn.setOnClickListener {
            val i = Intent(context, editActivity::class.java)
            i.putExtra("mode", REQUEST_ADD)
            startActivityForResult(i, REQUEST_ADD)
        }
        view.clear_btn.setOnClickListener {
            view.timetable.removeAll()
        }

        view.timetable.setHeaderHighlight(2)
        view.timetable.setOnStickerSelectEventListener { idx, schedules ->
            val i = Intent(context, editActivity::class.java)
            i.putExtra("mode", REQUEST_EDIT)
            i.putExtra("idx", idx)
            i.putExtra("schedules", schedules)
            startActivityForResult(i, REQUEST_EDIT)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_ADD -> if (resultCode == RESULT_OK_ADD) {
                val item =
                    data!!.getSerializableExtra("schedules") as ArrayList<Schedule>?
                timetable.add(item)
            }
            REQUEST_EDIT ->
                /** Edit -> Submit  */
                if (resultCode == RESULT_OK_EDIT) {
                    val idx = data!!.getIntExtra("idx", -1)
                    val item =
                        data.getSerializableExtra("schedules") as ArrayList<Schedule>?
                    timetable.edit(idx, item)
                } else if (resultCode == RESULT_OK_DELETE) {
                    val idx = data!!.getIntExtra("idx", -1)
                    timetable.remove(idx)
                }
        }
    }

    /** save timetableView's data to SharedPreferences in json format  */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun save(data: String, name:String) {

        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.249.18.225:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var scheduleService: ScheduleService = retrofit.create(ScheduleService::class.java)

        scheduleService.scheduleUpload(name,data).enqueue(object: Callback<ScheduleInf> {
            override fun onFailure(call: Call<ScheduleInf>, t: Throwable) {
                Log.d("fail", t.toString())
                var dialog = AlertDialog.Builder(requireContext())
                dialog.setMessage("저장 실패했습니다.")
                dialog.show()
            }
            override fun onResponse(call: Call<ScheduleInf>, response: Response<ScheduleInf>) {
                scheduleInf = response.body()
                Log.d("fail", scheduleInf.toString())
                var dialog = AlertDialog.Builder(requireContext())
                dialog.setMessage("저장 성공!")
                dialog.show()
            }
        })

        Toast.makeText(context, "saved!", Toast.LENGTH_SHORT).show()
    }

    /** get json data from SharedPreferences and then restore the timetable  */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadSavedData(view: View, name: String) {
        val intent = Intent(context, ScheduleLoadActivity::class.java)
        intent.putExtra("name", name)
        context?.startActivity(intent)
    }

//        view.timetable.removeAll()
//        val savedData = ""
//        if (savedData == null && savedData == "") return
//        view.timetable.load(savedData)
//        Toast.makeText(context, "loaded!", Toast.LENGTH_SHORT).show()

}