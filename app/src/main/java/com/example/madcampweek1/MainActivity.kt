package com.example.madcampweek1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {
    // 권한처리는 MainActivity에서 한다
    val permissions = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        checkAndStart()

    }

    fun startProcess() {
        // 권한처리 후 일반 프로세스(화면그리기, 데이터 가져오기) 시작
        setContentView(R.layout.activity_main)
        setAdapter()
        val tabTitles = listOf<String>("PhoneBook", "Gallery", "Game")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }


    fun setAdapter() {
        val adapter = FragmentAdapter(this)
        val fragments = listOf<Fragment>(PhoneBookFragment(), GalleryFragment(), GameFragment())
        adapter.fragments.addAll(fragments)
        viewPager.adapter = adapter
    }

    // 권한처리 코드
    fun checkAndStart() {
        if( isLower23() || isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(this, permissions, 99)
        }
    }

    fun isLower23() : Boolean{
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isPermitted():Boolean {
        for(perm in permissions) {
            if(checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 99) {
            var check = true
            for(grant in grantResults) {
                if(grant != PackageManager.PERMISSION_GRANTED) {
                    check = false
                    break
                }
            }
            if(check) startProcess()
            else {
                Toast.makeText(this, "권한 승인을 하셔야지만 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
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