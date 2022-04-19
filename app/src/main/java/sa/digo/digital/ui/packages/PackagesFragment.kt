package sa.digo.digital.ui.packages

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
import kotlinx.android.synthetic.main.fragment_packages.view.*
import kotlinx.android.synthetic.main.fragment_services.view.*

import sa.digo.digital.adapter.PackagesAdapter
import sa.digo.digital.adapter.ServicesAdapter
import sa.digo.digital.model.PackagesModel
import sa.digo.digital.model.ServicesModel
import sa.digo.digital.util.ToolBar
import java.util.ArrayList
import sa.digo.digital.R


class PackagesFragment : Fragment() {
    lateinit var packagesViewModel: PackagesViewModel
    var mData: List<PackagesModel.DetailsPackagesModel> = ArrayList<PackagesModel.DetailsPackagesModel>()
    var packagesAdapter: PackagesAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_packages, container, false)
        ToolBar(getString(R.string.packages),true, requireActivity(),false)
        requireActivity().toolbar.visibility = View.VISIBLE
        var aa="d"
        requireActivity().home_activity.background = ContextCompat.getDrawable(root.context,R.drawable.background_services)
        packagesAdapter= PackagesAdapter(requireActivity(),mData,2,R.layout.item_packages)
        root.search_packages.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(root.search_packages.text.toString())
            }
        })
        root.refresh_packages.setOnRefreshListener {
            packagesViewModel.loadPackages(root)
            root.rec_packages.visibility=View.GONE
            val container = root.findViewById<View>(R.id.shimmer_view_container_package)
            container.visibility=View.VISIBLE
            if ( root.refresh_packages.isRefreshing){
                root.refresh_packages.isRefreshing=false
            }



        }
        packagesViewModel = ViewModelProvider(this).get(PackagesViewModel::class.java)
        packagesViewModel.loadPackages(root)

        activity?.let { packagesViewModel.getPackages(it) }!!
            .observe(viewLifecycleOwner) { packages ->
                if (!packages.data.isNullOrEmpty()) {
                    packagesAdapter = PackagesAdapter(requireActivity(), packages.data,2,R.layout.item_packages)
                    root.rec_packages.adapter = packagesAdapter
                    mData = packages.data
                    packagesAdapter!!.notifyDataSetChanged()
                }
            }
        return root
    }
    fun filter(text: String) {
        val filteredList: ArrayList<PackagesModel.DetailsPackagesModel> = ArrayList<PackagesModel.DetailsPackagesModel>()
        for (item in mData) {
            if (item.title.trim().toLowerCase()
                    .contains(text.trim { it <= ' ' }.toLowerCase())
            ) {
                filteredList.add(item)
            }
        }
        packagesAdapter!!.filterList(filteredList)
    }

}