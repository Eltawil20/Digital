package sa.digo.digital.adapter;

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_services.view.*
import sa.digo.digital.R

import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.ServicesModel
import sa.digo.digital.ui.services.ServiceRequestActivity

class ServicesAdapter(activity: Context, data: List<ServicesModel.DetailsServiceModel>) :
    RecyclerView.Adapter<ServicesAdapter.MyViewHolder>() {
    var activity: Context = activity
    var data: List<ServicesModel.DetailsServiceModel> = data as MutableList<ServicesModel.DetailsServiceModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_services, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text=data[position].title

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.content.text = Html.fromHtml(data[position].content, Html.FROM_HTML_MODE_COMPACT);
        } else {
            holder.content.text = Html.fromHtml(data[position].content);
        }
        holder.cardOrderNow.setOnClickListener {
            var intent= Intent(activity, ServiceRequestActivity::class.java)
            intent.putExtra(activity.getString(R.string.type),activity.getString(R.string.type_service))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;

            intent.putExtra("details", data[position])
            activity.startActivity(intent)
        }
        Glide.with(activity).load(
            RetrofitClient.api+data[position].photo
        ).into(holder.imageServices);
        holder.btnServices.setOnClickListener {
            if (holder.linearContent.isVisible){
                holder.linearContent.visibility=View.GONE
                holder.btnServices.rotation= 0F
            }else{
                holder.linearContent.visibility=View.VISIBLE
                holder.btnServices.rotation= -90F

            }
        }
    }


    override fun getItemCount() = data.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView =itemView.tv_title_services
        var content: TextView =itemView.tv_content_services
        var btnServices: TextView =itemView.btn_more_details_services
        var imageServices: ImageView =itemView.img_services
        var cardOrderNow: CardView =itemView.btn_order_now
        var linearContent: LinearLayout =itemView.linear_content_service
    }

    fun filterList(filteredList: List<ServicesModel.DetailsServiceModel>) {
        data = filteredList
        notifyDataSetChanged()
    }

}
