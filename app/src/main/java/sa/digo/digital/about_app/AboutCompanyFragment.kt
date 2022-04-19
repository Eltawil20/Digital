package sa.digo.digital.about_app

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import sa.digo.digital.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.fragment_about_company.view.*
import sa.digo.digital.R


class AboutCompanyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root=inflater.inflate(R.layout.fragment_about_company, container, false)
        root.btn_create_account.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }
        root.btn_to_login.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }
        return root
    }

}