package sa.digo.digital.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login_tab.*
import sa.digo.digital.R

import kotlinx.android.synthetic.main.fragment_login_tab.view.*

class LoginTabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_login_tab, container, false)
        root.tv_forget_password.setOnClickListener {
            startActivity(Intent(context, ForgetPasswordActivity::class.java))
        }
        root.et_email.translationX = 800F
        root.et_password.translationX = 800F
        root.et_email.alpha = 0F
        root.et_password.alpha = 0F

        root.et_email.animate().translationX(0F).alpha(1F).setDuration(800).setStartDelay(300).start()
        root.et_password.animate().translationX(0F).alpha(1F).setDuration(800).setStartDelay(500).start()


        return root
    }
}