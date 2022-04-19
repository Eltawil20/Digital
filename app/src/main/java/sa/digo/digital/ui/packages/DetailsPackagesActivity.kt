package sa.digo.digital.ui.packages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details_packages.*
import kotlinx.android.synthetic.main.item_packages.view.*

import sa.digo.digital.api.RetrofitClient
import sa.digo.digital.model.PackagesModel
import sa.digo.digital.model.ServicesModel
import sa.digo.digital.ui.services.ServiceRequestActivity
import sa.digo.digital.util.ToolBar
import sa.digo.digital.R

class DetailsPackagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_packages)
        var packages: PackagesModel.DetailsPackagesModel = intent.getParcelableExtra("details")!!
        try {
            ToolBar(packages.title, true, DetailsPackagesActivity@ this,true)
        } catch (e: Exception) {
        }
        tv_details_packages_title.text = packages.title
        tv_details_price.text = packages.price
        tv_sub_title.text = packages.sub_title
        tv_subtype.text = packages.subtype
        var imagePackages: ImageView = findViewById(R.id.img_packages)
        Glide.with(this).load(
            RetrofitClient.api + packages.image
        ).into(imagePackages);
        for (i in packages.content) {
            tv_content_packages.append(" ⇦ $i\n\n")
        }
        btn_subscription.setOnClickListener {
            var intent = Intent(this, ServiceRequestActivity::class.java)
            intent.putExtra(this.getString(R.string.type), this.getString(R.string.type_packages))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            intent.putExtra("details", packages)
            startActivity(intent)
        }


    }
}