package sa.digo.digital.ui.services

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_service_request.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.Status
import sa.digo.digital.util.ProgressBar
import sa.digo.digital.R


class ServiceRequestViewModel : ViewModel() {
    fun sendServiceRequest(root: Activity, hashMap: HashMap<String, String>) {
        ProgressBar.setProgress(root,root.getString(R.string.sending))

        val apiInterface = RetrofitClient.apiInterface.sendService(hashMap)
        apiInterface.enqueue(object : Callback<Status> {
            override fun onResponse(
                call: Call<Status>?,
                response: Response<Status>?
            ) {
                ProgressBar.dismiss()
                if (response!!.isSuccessful) {
                    if (!response.body()!!.success.isNullOrEmpty()) {
                        Toast.makeText(root, root.getString(R.string.success), Toast.LENGTH_SHORT)
                            .show()
                        root.et_email.setText("")
                        root.et_name.setText("")
                        root.et_email.setText("")
                        root.et_phone.setText("")
                        root.et_organization.setText("")
                        root.et_details_project.setText("")

                    }
                    if (!response.body()!!.error.isNullOrEmpty()) {
                        Toast.makeText(root, root.getString(R.string.error), Toast.LENGTH_SHORT)
                            .show()

                    }

                }
            }

            override fun onFailure(call: Call<Status>?, t: Throwable?) {
                ProgressBar.dismiss()
                Toast.makeText(
                    root,
                    root.getString(R.string.check_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
    }

}