package sa.digo.digital.util

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import sa.digo.digital.R


class ToolBar {
    constructor(toolbar_title: String?,typeColor:Boolean, activity: Activity,visibilityBack:Boolean) {
//            Toolbar toolbar=activity.findViewById(R.id.toolbar);
        val toolbarTitle = activity.findViewById<TextView>(R.id.title_toolbar)
        var backBtn= activity.findViewById<CardView>(R.id.back_button_toolbar)
        val iconBtn= activity.findViewById<TextView>(R.id.icon_button_toolbar)
        if (typeColor){
            backBtn.background.setTint(Color.WHITE)
            iconBtn.setTextColor(activity.getColor(R.color.purple_700))
            toolbarTitle.setTextColor(activity.getColor(R.color.white))
        }else{
            backBtn.background.setTint(activity.getColor(R.color.purple_700))
            iconBtn.setTextColor(activity.getColor(R.color.white))
            toolbarTitle.setTextColor(activity.getColor(R.color.purple_700))
        }
        if (visibilityBack){
            backBtn.visibility= View.VISIBLE
        }else{
            backBtn.visibility= View.INVISIBLE

        }

//        backBtn.backgroundTintMode=activity.getColor(R.color.purple_700)
        toolbarTitle.text = toolbar_title
        backBtn.setOnClickListener { activity.onBackPressed() }
    }
}