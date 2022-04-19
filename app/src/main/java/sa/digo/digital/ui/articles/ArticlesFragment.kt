package sa.digo.digital.ui.articles

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_home.*

import sa.digo.digital.adapter.ArticlesAdapter
import sa.digo.digital.model.ArticlesModel
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.fragment_articles.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.rec_packages
import kotlinx.android.synthetic.main.fragment_packages.view.*
import sa.digo.digital.R
import sa.digo.digital.adapter.PackagesAdapter
import sa.digo.digital.adapter.ServicesAdapter
import sa.digo.digital.model.PackagesModel
import sa.digo.digital.model.ServicesModel
import sa.digo.digital.util.ToolBar
import java.util.ArrayList

class ArticlesFragment : Fragment() {
    lateinit var articlesViewModel: ArticlesViewModel
    var page = 1
    lateinit var myLayoutManager: LinearLayoutManager

    var articlesList = arrayListOf<ArticlesModel.DetailsArticlesModel>()
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    //    var mData: List<ArticlesModel.DetailsArticlesModel> = ArrayList<ArticlesModel.DetailsArticlesModel>()
    var articlesAdapter: ArticlesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_articles, container, false)
        ToolBar(getString(R.string.articles), false, requireActivity(), false)
        requireActivity().toolbar.visibility = View.VISIBLE
        requireActivity().home_activity.setBackgroundColor(root.context.getColor(R.color.articles_background))
        articlesAdapter = ArticlesAdapter(root.context, articlesList)
        swipeRefreshLayout = root.findViewById(R.id.refresh_article)



        root.search_articles.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(root.search_articles.text.toString())
            }
        })

        myLayoutManager = LinearLayoutManager(root.context)
        articlesAdapter = ArticlesAdapter(root.context, articlesList)
        root.rec_articles.adapter = articlesAdapter

        articlesViewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        articlesViewModel.loadArticles(page, root)

        swipeRefreshLayout.setOnRefreshListener {
            articlesViewModel.loadArticles(1, root)
            root.rec_articles.visibility=View.GONE

        }
        articlesViewModel.getArticles()!!.observe(viewLifecycleOwner) { articles ->
            articlesAdapter = ArticlesAdapter(requireActivity(), articles.data)
            root.rec_articles.adapter = articlesAdapter
            root.rec_articles.layoutManager = myLayoutManager

            for (i in articles.data) {
                articlesList.add(i)
            }
            articlesAdapter!!.notifyDataSetChanged()


            val scrollListener: RecyclerView.OnScrollListener =
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (dy > 0) {
                            if (loadItemsLayout_recyclerView.visibility != View.VISIBLE) {
                                var numberPages = articles.message.replace(Regex("[^0-9]"), "")
                                if (myLayoutManager.childCount + myLayoutManager.findFirstVisibleItemPosition()
                                    == articlesList.size
                                ) {
                                    if (page < numberPages.toInt()) {
                                        page += 1
                                        loadItemsLayout_recyclerView.visibility = View.VISIBLE
                                        articlesViewModel.loadArticles(page, root)
                                    }
                                }
                            }
                        }
                    }
                }
            root.rec_articles.addOnScrollListener(scrollListener)
        }



        return root
    }

    fun filter(text: String) {
        val filteredList: ArrayList<ArticlesModel.DetailsArticlesModel> =
            ArrayList<ArticlesModel.DetailsArticlesModel>()
        for (item in articlesList) {
            if (item.title.trim().toLowerCase()
                    .contains(text.trim { it <= ' ' }.toLowerCase())
            ) {
                filteredList.add(item)
            }
        }
        articlesAdapter!!.filterList(filteredList)
    }


}