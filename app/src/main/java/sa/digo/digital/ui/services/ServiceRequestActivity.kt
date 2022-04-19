package sa.digo.digital.ui.services

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_service_request.*
import kotlinx.android.synthetic.main.toolbar.*
import sa.digo.digital.model.PackagesModel
import sa.digo.digital.model.ServicesModel
import sa.digo.digital.util.ToolBar
import sa.digo.digital.R


class ServiceRequestActivity : AppCompatActivity() {
    lateinit var servicesViewModel: ServiceRequestViewModel
    lateinit var service: ServicesModel.DetailsServiceModel
    lateinit var packages: PackagesModel.DetailsPackagesModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_request)
        var type=intent.getStringExtra(getString(R.string.type))

        if (type.equals(getString(R.string.type_service))){
             service = intent.getParcelableExtra("details")!!
            ToolBar(service.title, false, ServiceRequestActivity@ this,true)

        }else if(type.equals(getString(R.string.type_packages))){
             packages= intent.getParcelableExtra("details")!!
            ToolBar(packages.title, false, ServiceRequestActivity@ this,true)
        }


        servicesViewModel = ViewModelProvider(this).get(ServiceRequestViewModel::class.java)
        btn_send_service_request.setOnClickListener {
            when {

                et_name.text.isNullOrBlank() -> {
                    et_name.error=getString(R.string.empty_edittext)

                }
                et_email.text.isNullOrBlank() -> {
                    et_email.error=getString(R.string.empty_edittext)

                }
                et_phone.text.isNullOrBlank() -> {
                    et_phone.error=getString(R.string.empty_edittext)

                }
                et_organization.text.isNullOrBlank() -> {
                    et_organization.error=getString(R.string.empty_edittext)

                }
                et_details_project.text.isNullOrBlank() -> {
                    et_details_project.error=getString(R.string.empty_edittext)

                }

                else -> {
                    var hashMap= HashMap<String,String>()
                    hashMap["name"] = et_name.text.toString()
                    hashMap["order"] = title_toolbar.text.toString()
                    hashMap["email"] = et_email.text.toString()
                    hashMap["phone_number"] = et_phone.text.toString()
                    hashMap["company"] = et_organization.text.toString()
                    hashMap["details"] = et_details_project.text.toString()
                    if (type.equals(getString(R.string.type_packages))){
                        hashMap["package"] = et_details_project.text.toString()

                    }

                    servicesViewModel.sendServiceRequest(this,hashMap)
                }
            }
        }


//        servicesViewModel.sendServiceRequest()


    }
}