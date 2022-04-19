package sa.digo.digital.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sa.digo.digital.R

import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        btn_change_password.setOnClickListener {
            startActivity(Intent(applicationContext, OkChangePasswordActivity::class.java))



        }
    }
}