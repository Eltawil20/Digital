package sa.digo.digital.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_home.*

import sa.digo.digital.ui.articles.ArticlesFragment
import sa.digo.digital.ui.connect_us.ContactUsFragment
import sa.digo.digital.ui.packages.PackagesFragment
import sa.digo.digital.ui.services.ServicesFragment
import sa.digo.digital.R
import sa.digo.digital.util.SessionManager


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        replaceFragment(HomeFragment())
        SessionManager(applicationContext).setBoolean(getString(R.string.first_open),true)

        nav_home.setOnClickListener {
            replaceFragment(HomeFragment())
        }
        nav_packages.setOnClickListener {
            replaceFragment(PackagesFragment())
        }
        nav_services.setOnClickListener {
            replaceFragment(ServicesFragment())
        }
        nav_contact_us.setOnClickListener {
            replaceFragment(ContactUsFragment())

        }
        nav_articles.setOnClickListener {
            replaceFragment(ArticlesFragment())

        }


//        val fragment: Fragment = tasks()
//        val fragmentManager: FragmentManager = getActivity().getSupportFragmentManager()
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.content_frame, fragment)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutHome, fragment)
        transaction.commit()
    }
}