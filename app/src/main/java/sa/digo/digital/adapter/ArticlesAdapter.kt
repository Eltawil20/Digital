package sa.digo.digital.adapter;

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.ArticlesModel
import sa.digo.digital.ui.articles.DetailsArticlesActivity
import kotlinx.android.synthetic.main.item_articles.view.*
import sa.digo.digital.R

import sa.digo.digital.model.ServicesModel

class ArticlesAdapter(activity: Context, data: List<ArticlesModel.DetailsArticlesModel>) :
    RecyclerView.Adapter<ArticlesAdapter.MyViewHolder>() {
    var activity: Context = activity
    var data: List<ArticlesModel.DetailsArticlesModel> = data as MutableList<ArticlesModel.DetailsArticlesModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_articles, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.text.text = Html.fromHtml(data[position].content, Html.FROM_HTML_MODE_COMPACT);
        } else {
            holder.text.text = Html.fromHtml(data[position].content);
        }
        holder.title.text= data[position].title
        Glide.with(activity).load(
            RetrofitClient.api+data[position].photo
        ).into(holder.imageArticles);

        holder.btnDetails.setOnClickListener {
            var intent= Intent(activity, DetailsArticlesActivity::class.java)
            intent.putExtra("details", data[position])
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            activity.startActivity(intent)
        }


    }


    override fun getItemCount() = data.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView =itemView.tv_title_articles
        var text: TextView =itemView.tv_text_articles
        var btnDetails: TextView =itemView.btn_details
        var imageArticles: ImageView =itemView.image_articles
    }
    fun filterList(filteredList: List<ArticlesModel.DetailsArticlesModel>) {
        data = filteredList
        notifyDataSetChanged()
    }

}
