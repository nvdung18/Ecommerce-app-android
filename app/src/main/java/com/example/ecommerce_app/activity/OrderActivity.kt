package com.example.ecommerce_app.activity

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.ecommerce_app.databinding.ActivityOrderBinding
import com.example.ecommerce_app.fragment.OrderUserFragment
import com.example.ecommerce_app.fragment.ProfileFragment


class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backImg.setOnClickListener {
            back()
        }

        setUpWithViewPagerAdapter(binding.viewpager)
        binding.tabLayout.setupWithViewPager(binding.viewpager)
    }

    private fun back() {
        onBackPressed()
    }

    @Override
    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val backFragment = fragmentManager.findFragmentByTag("profile_fragment")
        val fragment = ProfileFragment()

        if (backFragment is ProfileFragment) {
            // If the current fragment is OrderUserFragment, go back to the previous fragment
            val fragment = ProfileFragment()
            supportFragmentManager.beginTransaction().replace(com.example.ecommerce_app.R.id.nav_fragment, fragment, fragment.javaClass.simpleName).addToBackStack("profile_fragment")
                .commit()
            return
//            fragmentManager.popBackStack()
        }
        super.onBackPressed();
    }
    private fun setUpWithViewPagerAdapter(viewpager: ViewPager) {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, this)
        //init
        viewPagerAdapter.addFragment(
            OrderUserFragment.newInstance(
                "01",
                "Wait for confirmation",
                "123"
            ),"Wait for confirmation"
        )
        viewPagerAdapter.addFragment(
            OrderUserFragment.newInstance(
                "01",
                "Order confirmed",
                "1234"
            ),"Order confirmed"
        )
        viewPagerAdapter.addFragment(
            OrderUserFragment.newInstance(
                "01",
                "Delivering",
                "12345"
            ),"Delivering"
        )
        viewPagerAdapter.addFragment(
            OrderUserFragment.newInstance(
                "01",
                "Order delivered",
                "123456"
            ),"Order delivered"
        )
        viewPagerAdapter.notifyDataSetChanged()
        viewpager.adapter = viewPagerAdapter
    }

}


class ViewPagerAdapter(fm: FragmentManager, behavior: Int, context: Context): FragmentPagerAdapter(fm, behavior) {
    //hold list of fragment
    private val fragmentsList: ArrayList<OrderUserFragment> = ArrayList()

    //list title of categoriesa
    private val fragmentTitleList: ArrayList<String> = ArrayList()
    private val context: Context

    init {
        this.context = context
    }

    override fun getCount(): Int {
        return fragmentsList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentsList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    public fun addFragment(fragment: OrderUserFragment, title: String) {
        fragmentsList.add(fragment)
        fragmentTitleList.add(title)
    }
}
