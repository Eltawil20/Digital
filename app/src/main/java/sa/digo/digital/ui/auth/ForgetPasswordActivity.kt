package sa.digo.digital.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sa.digo.digital.R

import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        btn_send.setOnClickListener {
            startActivity(Intent(applicationContext, OkSendPasswordActivity::class.java))
        }


    }
}