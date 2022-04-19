package sa.digo.digital.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import sa.digo.digital.R

import sa.digo.digital.adapter.LoginAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var tabLayoutLogin: TabLayout
    lateinit var viewPagerLogin: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tabLayoutLogin=findViewById<TabLayout>(R.id.tab_login)
        viewPagerLogin=findViewById<ViewPager>(R.id.view_pager_login)
        tabLayoutLogin.addTab(tabLayoutLogin.newTab().setText(getString(R.string.sign_up)))
        tabLayoutLogin.addTab(tabLayoutLogin.newTab().setText(getString(R.string.sign_in)))

        tabLayoutLogin.tabGravity=TabLayout.GRAVITY_FILL
        viewPagerLogin.adapter= LoginAdapter(supportFragmentManager,tabLayoutLogin.tabCount)
        viewPagerLogin.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(tabLayoutLogin))
        tabLayoutLogin.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPagerLogin.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })




    }
}