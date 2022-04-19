package sa.digo.digital.adapter;

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_slider.view.*
import sa.digo.digital.R

import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.SliderModel

class SliderAdapter(activity: Activity, data: List<SliderModel.DetailsSliderModel>) :
    RecyclerView.Adapter<SliderAdapter.MyViewHolder>() {
    var activity: Activity = activity
    var data: MutableList<SliderModel.DetailsSliderModel> =
        data as MutableList<SliderModel.DetailsSliderModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val url = data!![position % data.size]

        Glide.with(activity).load(
            RetrofitClient.api + url.background
        ).into(holder.imgSlider);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvContentSlider.text =
                Html.fromHtml(url.content, Html.FROM_HTML_MODE_COMPACT);
        } else {
            holder.tvContentSlider.text = Html.fromHtml(url.content);
        }
        try {
            holder.btnSlider.setOnClickListener {
                if (url.more_link.startsWith("http") ||
                    url.more_link.startsWith("https")
                ) {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(url.more_link)
                    )
                    activity.startActivity(browserIntent)
                }else{
                    Toast.makeText(activity,activity.getString(R.string.link_not_work),
                        Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
        }
        holder.tvBtnSlider.text = url.more_btn

    }


    override fun getItemCount() = if (data == null) 0 else Int.MAX_VALUE

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgSlider: ImageView = itemView.img_slider
        var btnSlider: CardView = itemView.btn_slider
        var tvContentSlider: TextView = itemView.tv_slider_content
        var tvBtnSlider: TextView = itemView.tv_btn_slider
    }
}
