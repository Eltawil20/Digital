package sa.digo.digital.adapter;

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_packages.view.*
import kotlinx.android.synthetic.main.item_services.view.*
import sa.digo.digital.R

import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.PackagesModel
import sa.digo.digital.model.ServicesModel
import sa.digo.digital.ui.packages.DetailsPackagesActivity
import sa.digo.digital.ui.services.ServiceRequestActivity

class PackagesAdapter(activity: Activity, data: List<PackagesModel.DetailsPackagesModel>,type:Int,layout:Int) :
    RecyclerView.Adapter<PackagesAdapter.MyViewHolder>() {
    var activity: Activity = activity
    var data: List<PackagesModel.DetailsPackagesModel> = data as MutableList<PackagesModel.DetailsPackagesModel>
   var layout=layout
    private var type=type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(type==1){
            holder.cardPackages.background.setTint(activity.getColor(R.color.purple_700))
            holder.tvDetails.setTextColor(activity.getColor(R.color.purple_700))
            holder.tvPrice.setTextColor(activity.getColor(R.color.white))
            holder.tvTitlePackages.setTextColor(activity.getColor(R.color.white))
            holder.cardDetails.background.setTint(activity.getColor(R.color.white))
        }
        Glide.with(activity).load(
            RetrofitClient.api+data[position].image
        ).into(holder.imagePackages);
        holder.tvPrice.text=data[position].price
        holder.tvTitlePackages.text=data[position].title
//        holder.itemView.setOnClickListener {
//            var intent= Intent(activity, ServiceRequestActivity::class.java)
//            intent.putExtra(activity.getString(R.string.type),activity.getString(R.string.type_packages))
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
//            intent.putExtra("details", data[position])
//            activity.startActivity(intent)
//        }

        holder.cardDetails.setOnClickListener {
            var intent= Intent(activity, DetailsPackagesActivity::class.java)
            intent.putExtra("details", data[position])
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;

            activity.startActivity(intent)
        }
    }


    override fun getItemCount() = data.size
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var imagePackages: ImageView =itemView.img_packages
       var tvDetails: TextView =itemView.tv_details
       var tvPrice: TextView =itemView.tv_price
       var tvTitlePackages: TextView =itemView.tv_title_packages
//       var tvPrice: TextView =itemView.tv_price
       var cardDetails: CardView =itemView.card_details
       var cardPackages: CardView =itemView.card_packages

    }
    fun filterList(filteredList: List<PackagesModel.DetailsPackagesModel>) {
        data = filteredList
        notifyDataSetChanged()
    }
}
