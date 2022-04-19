package sa.digo.digital.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_ok_send_password.*
import sa.digo.digital.R

class OkSendPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_send_password)
        btn_ok_send_password.setOnClickListener {
            startActivity(Intent(applicationContext, ChangePasswordActivity::class.java))
        }

    }
}