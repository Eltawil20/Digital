package sa.digo.digital.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import sa.digo.digital.ui.about_app.AboutCompany1Fragment
import sa.digo.digital.ui.about_app.AboutCompany2Fragment
import sa.digo.digital.ui.about_app.AboutCompany3Fragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import sa.digo.digital.R
import sa.digo.digital.ui.home.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize ViewPager view

        // Initialize ViewPager view
        val viewPager = findViewById<ViewPager>(R.id.viewPagerOnBoarding)
        // create ViewPager adapter
        // create ViewPager adapter
        val viewPagerAdapter: ViewPagerAdapter =
            ViewPagerAdapter(
                supportFragmentManager
            )

        // Add All Fragments to ViewPager

        // Add All Fragments to ViewPager
        viewPagerAdapter.addFragment(AboutCompany1Fragment())
        viewPagerAdapter.addFragment(AboutCompany2Fragment())
        viewPagerAdapter.addFragment(AboutCompany3Fragment())
//        viewPagerAdapter.addFragment(AboutCompanyFragment())
        tv_Skip.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }


        // Set Adapter for ViewPager

        // Set Adapter for ViewPager
        viewPager.adapter = viewPagerAdapter

        // Setup dot's indicator

        // Setup dot's indicator
        val tabLayout = findViewById<TabLayout>(R.id.tabLayoutIndicator)
        tabLayout.setupWithViewPager(viewPager)

    }
    internal class ViewPagerAdapter(supportFragmentManager: FragmentManager?) :
        FragmentPagerAdapter(supportFragmentManager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mList: MutableList<Fragment> = ArrayList()
        override fun getItem(i: Int): Fragment {
            return mList[i]
        }

        override fun getCount(): Int {
            return mList.size
        }

        fun addFragment(fragment: Fragment) {
            mList.add(fragment)
        }
    }

}