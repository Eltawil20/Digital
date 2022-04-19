package sa.digo.digital.ui.articles

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fragment_articles.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.ArticlesModel
import sa.digo.digital.R

class ArticlesViewModel: ViewModel() {
    private var articlesLiveData: MutableLiveData<ArticlesModel>? = null
    fun getArticles(): LiveData<ArticlesModel>? {
        if (articlesLiveData == null) {
            articlesLiveData = MutableLiveData()
        }
        return articlesLiveData
    }
    fun loadArticles(page:Int,root:View){
        val container = root.findViewById<View>(R.id.shimmer_view_container)

        if (page==1){
            container.visibility = View.VISIBLE
            root.loadItemsLayout_recyclerView.visibility=View.GONE
            if (root.refresh_article.isRefreshing) {
                root.refresh_article.isRefreshing=false
            }

        }else{
            container.visibility = View.GONE
        }
        val apiInterface = RetrofitClient.apiInterface.getBlogs("$page")
        apiInterface.enqueue(object : Callback<ArticlesModel> {
            override fun onResponse(call: Call<ArticlesModel>?, response: Response<ArticlesModel>?) {
                try {
                    container.visibility = View.GONE
                    root.rec_articles.visibility=View.VISIBLE

                    root.loadItemsLayout_recyclerView.visibility=View.GONE
                    if (root.refresh_article.isRefreshing) {
                        root.refresh_article.isRefreshing=false
                    }
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success){
                            if (!response.body()!!.data.isNullOrEmpty()){
                                articlesLiveData!!.value=response.body()

                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d("abdallah",e.message.toString())
                    Toast.makeText(root.context,root.context.getString(R.string.error_api), Toast.LENGTH_SHORT).show()

                }
            }
            override fun onFailure(call: Call<ArticlesModel>?, t: Throwable?) {
                container.visibility = View.GONE
                root.loadItemsLayout_recyclerView.visibility=View.GONE
                if (root.refresh_article.isRefreshing) {
                    root.refresh_article.isRefreshing=false
                }
                Toast.makeText(root.context,root.context.getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()

            }
        })
    }

}