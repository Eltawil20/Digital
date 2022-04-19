package sa.digo.digital.ui.articles

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide

import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.ArticlesModel
import kotlinx.android.synthetic.main.activity_details_articale.*
import sa.digo.digital.R

class DetailsArticlesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_articale)
        var articles: ArticlesModel.DetailsArticlesModel = intent.getParcelableExtra("details")!!
        Glide.with(this).load(RetrofitClient.api+articles.photo).into(img_details_article)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_details.text = Html.fromHtml(articles.content, Html.FROM_HTML_MODE_COMPACT);
        } else {
            tv_details.text = Html.fromHtml(articles.content);
        }
    }
}