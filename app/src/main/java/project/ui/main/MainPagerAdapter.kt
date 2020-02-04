package project.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import project.ui.main.best.BestFragment
import project.ui.main.home.HomeFragment


class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var mTabCount: Int = 0

    init {
        this.mTabCount = 0
    }

    override fun getCount(): Int {
        return mTabCount
    }

    fun setCount(count: Int) {
        mTabCount = count
    }

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> HomeFragment.newInstance()
        else -> BestFragment.newInstance()
    }
}
