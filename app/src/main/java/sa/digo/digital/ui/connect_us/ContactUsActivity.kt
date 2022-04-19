package sa.digo.digital.ui.connect_us

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_contact_us.*
import kotlinx.android.synthetic.main.activity_service_request.et_email
import kotlinx.android.synthetic.main.activity_service_request.et_name

import sa.digo.digital.util.ToolBar
import sa.digo.digital.R

class ContactUsActivity : AppCompatActivity() {
    lateinit var connectUsViewModel: ContactUsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        ToolBar(getString(R.string.contact_us), false, ServiceRequestActivity@ this,true)

        connectUsViewModel = ViewModelProvider(this).get(ContactUsViewModel::class.java)

        btn_send_message.setOnClickListener {
            when {

                et_name.text.isNullOrBlank() -> {
                    et_name.error=getString(R.string.empty_edittext)

                }
                et_email.text.isNullOrBlank() -> {
                    et_email.error=getString(R.string.empty_edittext)

                }
                et_message.text.isNullOrBlank() -> {
                    et_message.error=getString(R.string.empty_edittext)

                }

                else -> {
                    var hashMap= HashMap<String,String>()
                    hashMap["name"] = et_name.text.toString()
                    hashMap["email"] = et_email.text.toString()
                    hashMap["message"] = et_message.text.toString()
                    connectUsViewModel.sendMessage(this,hashMap)
                }
            }
        }

    }
}