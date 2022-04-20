package sa.digo.digital.ui.services

import android.app.Activity
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.fragment_services.*
import kotlinx.android.synthetic.main.fragment_services.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.PackagesModel
import sa.digo.digital.model.ServicesModel
import sa.digo.digital.util.SessionManager
import sa.digo.digital.R


class ServicesViewModel : ViewModel() {
    private var servicesLiveData: MutableLiveData<ServicesModel>? = null
    fun getServices(root: Activity): LiveData<ServicesModel>? {
        if (servicesLiveData == null) {
            servicesLiveData = MutableLiveData()
            loadFromSession(root)
        }
        return servicesLiveData
    }

    fun loadServices(root: View) {
        val container = root.findViewById<View>(R.id.shimmer_view_container)
        val recServices = root.findViewById<RecyclerView>(R.id.rec_services)
        if (isStoreData(root)) {
            recServices.visibility=View.GONE
            container.visibility=View.VISIBLE
        }
        val apiInterface = RetrofitClient.apiInterface.getServices()
        apiInterface.enqueue(object : Callback<ServicesModel> {
            override fun onResponse(
                call: Call<ServicesModel>?,
                response: Response<ServicesModel>?
            ) {

                container.visibility=View.GONE
                recServices.visibility=View.VISIBLE

                try {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            if (!response.body()!!.data.isNullOrEmpty()) {
                                servicesLiveData!!.value = response.body()
                                SessionManager(root.context).setObject(
                                    root.context.getString(R.string.array_services),
                                    response.body()
                                )


                            }
                        }
                    }
                } catch (e: Exception) {
                }
            }

            override fun onFailure(call: Call<ServicesModel>?, t: Throwable?) {
                container.visibility=View.GONE
                Toast.makeText(
                    root.context,
                    root.context.getString(R.string.check_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
    }

    fun loadFromSession(root: Activity) {
        try {
            var servicesModel = SessionManager(root.applicationContext).getObject(
                root.applicationContext.getString(R.string.array_services),
                ServicesModel::class.java
            )

            if (servicesModel!!.success) {
                servicesLiveData!!.value = servicesModel

            }

        } catch (e: Exception) {
        }

    }
    fun isStoreData(root: View): Boolean {
        var servicesModel = SessionManager(root.context).getObject(
            root.context.getString(R.string.array_packages),
            PackagesModel::class.java)
        return servicesModel == null
    }

}