package sa.digo.digital.ui.services

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_articles.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_services.view.*
import kotlinx.android.synthetic.main.fragment_services.view.rec_services
import sa.digo.digital.R

import sa.digo.digital.adapter.ServicesAdapter
import sa.digo.digital.model.ServicesModel
import sa.digo.digital.util.ToolBar
import java.util.ArrayList


class ServicesFragment : Fragment() {
    lateinit var servicesViewModel: ServicesViewModel
    var mData: List<ServicesModel.DetailsServiceModel> = ArrayList<ServicesModel.DetailsServiceModel>()
    var servicesAdapter: ServicesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_services, container, false)
        // Inflate the layout for this fragment
        servicesAdapter= ServicesAdapter(root.context,mData)
        ToolBar(getString(R.string.services),true, requireActivity(),false)
        requireActivity().toolbar.visibility = View.VISIBLE
        requireActivity().home_activity.background = ContextCompat.getDrawable(root.context,R.drawable.background_services)
        root.search_services.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(root.search_services.text.toString())
            }
        })

//        requireActivity().home_activity.setBackgroundColor(R.drawable.background_services)
        servicesViewModel=ViewModelProvider(this).get(ServicesViewModel::class.java)
        root.refresh_services.setOnRefreshListener {
            servicesViewModel.loadServices(root)
            root.rec_services.visibility=View.GONE
            val container = root.findViewById<View>(R.id.shimmer_view_container)
            container.visibility=View.VISIBLE
            if ( root.refresh_services.isRefreshing){
                root.refresh_services.isRefreshing=false
            }

        }

        servicesViewModel.loadServices(root)
        activity?.let { servicesViewModel.getServices(it) }!!.observe(viewLifecycleOwner){ services->
            if (!services.data.isNullOrEmpty()){
                servicesAdapter = ServicesAdapter(root.context, services.data)
                root.rec_services.adapter = servicesAdapter
                mData = services.data
                servicesAdapter!!.notifyDataSetChanged()            }


        }
        return root
    }
    fun filter(text: String) {
        val filteredList: ArrayList<ServicesModel.DetailsServiceModel> = ArrayList<ServicesModel.DetailsServiceModel>()
        for (item in mData) {
            if (item.title.trim().toLowerCase()
                    .contains(text.trim { it <= ' ' }.toLowerCase())
            ) {
                filteredList.add(item)
            }
        }
        servicesAdapter!!.filterList(filteredList)
    }


}
