package sa.digo.digital.ui.connect_us

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_service_request.*
import kotlinx.android.synthetic.main.fragment_services.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.ServicesModel
import sa.digo.digital.model.Status
import sa.digo.digital.util.ProgressBar
import sa.digo.digital.util.SessionManager
import sa.digo.digital.R

class ContactUsViewModel: ViewModel() {
    fun sendMessage(root: Activity,hashMap: HashMap<String,String>) {
        ProgressBar.setProgress(root,root.getString(R.string.sending))
        val apiInterface = RetrofitClient.apiInterface.sendContact(hashMap)
        apiInterface.enqueue(object : Callback<Status> {
            override fun onResponse(
                call: Call<Status>?,
                response: Response<Status>?
            ) {
                ProgressBar.dismiss()
                if (response!!.isSuccessful) {
                    if (!response.body()!!.success.isNullOrEmpty()){
                        Toast.makeText(root, root.getString(R.string.success_sending), Toast.LENGTH_SHORT).show()

                    }
                    if (!response.body()!!.error.isNullOrEmpty()){
                        Toast.makeText(root, root.getString(R.string.error), Toast.LENGTH_SHORT).show()

                    }

                }
            }

            override fun onFailure(call: Call<Status>?, t: Throwable?) {
                ProgressBar.dismiss()
                Toast.makeText(root,root.getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()

            }
        })
    }

}