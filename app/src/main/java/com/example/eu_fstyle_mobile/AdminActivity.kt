package com.example.eu_fstyle_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eu_fstyle_mobile.src.view.admin.BillAdminFragment
import com.example.eu_fstyle_mobile.src.view.admin.CategoryAdminFragment
import com.example.eu_fstyle_mobile.src.view.admin.HomeAdminFragment
import com.example.eu_fstyle_mobile.src.view.admin.ProfileAdminFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminActivity : AppCompatActivity() {
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.bottom_home -> {
                replaceFragment(HomeAdminFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_category -> {
                replaceFragment(CategoryAdminFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_bill -> {
                replaceFragment(BillAdminFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_profile -> {
                replaceFragment(ProfileAdminFragment())
                return@OnNavigationItemSelectedListener true
            }
            else -> {false}
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        replaceFragment(HomeAdminFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }
}