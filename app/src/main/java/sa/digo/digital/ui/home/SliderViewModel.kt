package sa.digo.digital.ui.home

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.loading.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sa.digo.digital.R

import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.SliderModel
import sa.digo.digital.util.SessionManager

class SliderViewModel : ViewModel() {
    private var sliderViewModel: MutableLiveData<SliderModel>? = null
    fun getSlider(root: Activity): LiveData<SliderModel>? {
        if (sliderViewModel == null) {
            sliderViewModel = MutableLiveData()
            loadFromSession(root)
        }
        return sliderViewModel
    }

    fun loadSlider(root: Activity) {
        val apiInterface = RetrofitClient.apiInterface.slider()
        apiInterface.enqueue(object : Callback<SliderModel> {
            override fun onResponse(
                call: Call<SliderModel>?,
                response: Response<SliderModel>?
            ) {
//                root.loading.visibility= View.GONE

                if (response!!.isSuccessful) {
                    if (response.body()!!.success) {
                        if (!response.body()!!.data.isNullOrEmpty()) {

                            try {
                                sliderViewModel!!.value = response.body()
                            } catch (e: Exception) {
                            }
                            SessionManager(root.applicationContext).setObject(
                                root.applicationContext.getString(R.string.array_sliders),
                                response.body()
                            )


                        }
                    }
                }
            }

            override fun onFailure(call: Call<SliderModel>?, t: Throwable?) {
//                root.loading.visibility= View.GONE
            }
        })
    }

    fun loadFromSession(root: Activity) {
        try {
            var sliderModel = SessionManager(root.applicationContext).getObject(
                root.applicationContext.getString(R.string.array_sliders),
                SliderModel::class.java
            )
            if (sliderModel!!.success) {
                try {
                    sliderViewModel!!.value = sliderModel
                } catch (e: Exception) {
                }

            }

        } catch (e: Exception) {
        }

    }

}