package sa.digo.digital.ui.packages

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.loading.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.PackagesModel
import sa.digo.digital.util.SessionManager
import sa.digo.digital.R


class PackagesViewModel : ViewModel() {
    private var packagesLiveData: MutableLiveData<PackagesModel>? = null
    fun getPackages(root: Activity): LiveData<PackagesModel>? {
        if (packagesLiveData == null) {
            packagesLiveData = MutableLiveData()
            loadFromSession(root)
        }
        return packagesLiveData
    }

    fun loadPackages(root: View) {
        val container = root.findViewById<View>(R.id.shimmer_view_container_package)
        val recPackages = root.findViewById<RecyclerView>(R.id.rec_packages)
        if (isStoreData(root)) {
            recPackages.visibility=View.GONE
            container.visibility=View.VISIBLE
        }
        val apiInterface = RetrofitClient.apiInterface.getPackages()
        apiInterface.enqueue(object : Callback<PackagesModel> {
            override fun onResponse(
                call: Call<PackagesModel>?,
                response: Response<PackagesModel>?
            ) {
                container.visibility=View.GONE
                recPackages.visibility=View.VISIBLE
                try {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            if (!response.body()!!.data.isNullOrEmpty()) {
                                packagesLiveData!!.value = response.body()
                                SessionManager(root.context).setObject(
                                    root.context.getString(R.string.array_packages),
                                    response.body()
                                )


                            }
                        }
                    }
                } catch (e: Exception) {
                }
            }

            override fun onFailure(call: Call<PackagesModel>?, t: Throwable?) {
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
                root.applicationContext.getString(R.string.array_packages),
                PackagesModel::class.java
            )
            if (servicesModel!!.success) {
                packagesLiveData!!.value = servicesModel
            }

        } catch (e: Exception) {
        }

    }

    fun isStoreData(root: View): Boolean {
        var servicesModel = SessionManager(root.context).getObject(
            root.context.getString(R.string.array_packages),
            PackagesModel::class.java
        )
        return servicesModel == null
    }

}