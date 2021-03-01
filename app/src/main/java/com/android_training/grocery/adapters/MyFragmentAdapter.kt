package com.android_training.grocery.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android_training.grocery.fragments.ProductFragment
import com.android_training.grocery.models.Subcategory

class MyFragmentAdapter(fm: FragmentManager):FragmentPagerAdapter(fm) {

    var mFragmentList: ArrayList<Fragment> = ArrayList()
    var mTitleList: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mTitleList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }

    fun addFragment(subcategory: Subcategory){
        mTitleList.add(subcategory.subName)
        mFragmentList.add(ProductFragment.newInstance(subcategory.subId))
    }
}