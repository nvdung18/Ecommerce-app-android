package com.example.ecommerce_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.ActivityHomeBinding
import com.example.ecommerce_app.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var bottomNavigationView:BottomNavigationView
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomNavMenu
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(binding.navFragment.id, HomeFragment()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.homeMenu -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.shopMenu -> {
                val fragment = ShopFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.bagMenu -> {
                val fragment = BagFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.favMenu -> {
                val fragment = FavFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.profileMenu -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName).addToBackStack("profile_fragment")
                    .commit()
                return true
            }
        }
        return false
    }
}