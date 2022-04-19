package sa.digo.digital.util

import android.app.Activity
import android.app.Dialog
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import sa.digo.digital.R


class ProgressBar {

    companion object{
        var dialog: Dialog? = null
        fun setProgress(activity: Activity, type: String?) {
            dialog = Dialog(activity)
            val inflater = activity.layoutInflater
            val view: View = inflater.inflate(R.layout.item_custom_progress_bar, null)
            dialog!!.setContentView(view)
            dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
            val textView = view.findViewById<TextView>(R.id.type_dialog)
            textView.text = type
            dialog!!.setCancelable(false)
            if (!dialog!!.isShowing) {
                dialog!!.show()
            }
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog!!.window!!.attributes)
            layoutParams.width = (displayMetrics.widthPixels * 0.4f).toInt()
            dialog!!.window!!.attributes = layoutParams
        }

        fun dismiss() {
            if (isShowing()) {
                dialog!!.dismiss()
            }
        }

        fun isShowing(): Boolean {
            return dialog!!.isShowing
        }
    }


}