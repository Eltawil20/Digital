package sa.digo.digital.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.rec_services
import kotlinx.android.synthetic.main.fragment_services.view.*

import sa.digo.digital.adapter.PackagesAdapter
import sa.digo.digital.adapter.ServicesAdapter
import sa.digo.digital.adapter.SliderAdapter
import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.ServicesModel
import sa.digo.digital.ui.packages.PackagesFragment
import sa.digo.digital.ui.packages.PackagesViewModel
import sa.digo.digital.ui.services.ServicesFragment
import sa.digo.digital.ui.services.ServicesViewModel
import java.util.*
import sa.digo.digital.R


open class HomeFragment : Fragment() {
    lateinit var servicesViewModel: ServicesViewModel
    lateinit var packagesViewModel: PackagesViewModel
    lateinit var sliderViewModel: SliderViewModel
    private var imageUrls = arrayListOf<String>()
    var mData: List<ServicesModel.DetailsServiceModel> =
        ArrayList<ServicesModel.DetailsServiceModel>()
    var servicesAdapter: ServicesAdapter? = null

    var rcvBanner: RecyclerView? = null
    var urls: List<String>? = null
    var timer: Timer? = null
    var timerTask: TimerTask? = null
    var position = 0
    var layoutManager: LinearLayoutManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        requireActivity().toolbar.visibility = View.GONE
        servicesAdapter = ServicesAdapter(root.context, mData)
        layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
        root.rec_slider!!.layoutManager = layoutManager
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(root.rec_slider)

        rcvBanner = root.rec_slider
        root.btn_all_services.setOnClickListener {
            replaceFragment(ServicesFragment())
        }
        root.btn_all_packages.setOnClickListener {
            replaceFragment(PackagesFragment())

        }
        root.refresh_home.setOnRefreshListener {
            servicesViewModel.loadServices(root)
            packagesViewModel.loadPackages(root)
            sliderViewModel.loadSlider(requireActivity())
            root.rec_services.visibility=View.GONE
            root.rec_packages.visibility=View.GONE
            val container = root.findViewById<View>(R.id.shimmer_view_container)
            val containerPackages = root.findViewById<View>(R.id.shimmer_view_container_package)
            container.visibility=View.VISIBLE
            containerPackages.visibility=View.VISIBLE
            root.refresh_home.isRefreshing = false
        }
        sliderViewModel = ViewModelProvider(this).get(SliderViewModel::class.java)
        sliderViewModel.loadSlider(requireActivity())
        sliderViewModel.getSlider(requireActivity())!!.observe(viewLifecycleOwner) { slider ->
            if (!slider.data.isNullOrEmpty()) {
                imageUrls.clear()
                for (i in slider.data) {
                    imageUrls.add(RetrofitClient.api + i.background)
                }
            }

            root.rec_slider.adapter = SliderAdapter(requireActivity(), slider.data)
            if (urls != null) {
                position = Int.MAX_VALUE / 2
                rcvBanner!!.scrollToPosition(position)
            }
            try {
                root.rec_slider.smoothScrollBy(imageUrls.size, 0)

                root.rec_slider.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == 1) {
                            stopAutoScrollBanner()
                        } else if (newState == 0) {
                            position = layoutManager!!.findFirstCompletelyVisibleItemPosition()
                            runAutoScrollBanner()
                        }
                    }
                })
            } catch (e: Exception) {
            }


        }
        servicesViewModel = ViewModelProvider(this).get(ServicesViewModel::class.java)
        servicesViewModel.loadServices(root)
//        activity?.let {  }
        root.search_service.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(root.search_service.text.toString())
            }
        })
//

        servicesViewModel.getServices(requireActivity())!!
            .observe(viewLifecycleOwner) { services ->
                servicesAdapter = ServicesAdapter(root.context, services.data)
                root.rec_services.setAdapter(servicesAdapter)
                mData = services.data
                servicesAdapter!!.notifyDataSetChanged()
//                root.rec_services.adapter = ServicesAdapter(root.context, services.data)
            }
        packagesViewModel = ViewModelProvider(this).get(PackagesViewModel::class.java)
        packagesViewModel.loadPackages(root)
        packagesViewModel.getPackages(requireActivity())!!
            .observe(viewLifecycleOwner) { packages ->
                if (!packages.data.isNullOrEmpty()) {
                    root.rec_packages.adapter = PackagesAdapter(
                        requireActivity(),
                        packages.data,
                        1,
                        R.layout.item_packages_custom
                    )
                }
            }
        return root
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutHome, fragment)
        transaction.commit()
    }

    private fun stopAutoScrollBanner() {
        if (timer != null && timerTask != null) {
            timerTask!!.cancel()
            timer!!.cancel()
            timer = null
            timerTask = null
            position = layoutManager!!.findFirstCompletelyVisibleItemPosition()
        }
    }

    private fun runAutoScrollBanner() {
        if (timer == null && timerTask == null) {
            timer = Timer()
            timerTask = object : TimerTask() {
                override fun run() {
                    if (position == Int.MAX_VALUE) {
                        position = Int.MAX_VALUE / 2
                        rcvBanner!!.scrollToPosition(position)
                        rcvBanner!!.smoothScrollBy(imageUrls.size, 0)
                    } else {
                        position++
                        rcvBanner!!.smoothScrollToPosition(position)
                    }
                }
            }
            timer!!.schedule(timerTask, 8000, 3000)
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            runAutoScrollBanner()
        } catch (e: Exception) {
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            stopAutoScrollBanner()
        } catch (e: Exception) {
        }
    }

    fun filter(text: String) {
        val filteredList: ArrayList<ServicesModel.DetailsServiceModel> =
            ArrayList<ServicesModel.DetailsServiceModel>()
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