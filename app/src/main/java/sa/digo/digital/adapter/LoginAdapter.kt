package sa.digo.digital.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import sa.digo.digital.ui.auth.LoginTabFragment
import sa.digo.digital.ui.auth.SignUpTabFragment

class LoginAdapter(fm: FragmentManager, totalTabs: Int) : FragmentPagerAdapter(fm) {
    var myTotalTabs:Int=totalTabs
    override fun getCount(): Int {
         return  myTotalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                SignUpTabFragment()

            }
            1 -> {
                LoginTabFragment()
            }

            else -> {
                return   getItem(position)
            }
        }

    }
}