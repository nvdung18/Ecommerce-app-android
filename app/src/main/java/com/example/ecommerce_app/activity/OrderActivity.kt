package com.example.ecommerce_app.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.ecommerce_app.databinding.ActivityOrderBinding
import com.example.ecommerce_app.fragment.OrderUserFragment


class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpWithViewPagerAdapter(binding.viewpager)
        binding.tabLayout.setupWithViewPager(binding.viewpager)
    }

    private fun setUpWithViewPagerAdapter(viewpager: ViewPager) {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, this)
        //init
        viewPagerAdapter.addFragment(
            OrderUserFragment.newInstance(
                "01",
                "Cho Xac Nhan",
                "123"
            ),"Cho Xac Nhan"
        )
        viewPagerAdapter.addFragment(
            OrderUserFragment.newInstance(
                "01",
                "Cho Lay Hang",
                "1234"
            ),"Cho Lay Hang"
        )
        viewPagerAdapter.addFragment(
            OrderUserFragment.newInstance(
                "01",
                "Da Lay Hang",
                "12345"
            ),"Da Lay Hang"
        )
        viewPagerAdapter.addFragment(
            OrderUserFragment.newInstance(
                "01",
                "Da Thanh Toan",
                "123456"
            ),"Da Thanh Toan"
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
