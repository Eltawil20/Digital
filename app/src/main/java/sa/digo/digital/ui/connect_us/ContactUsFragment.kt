package sa.digo.digital.ui.connect_us

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_contact_us.*
import kotlinx.android.synthetic.main.fragment_contact_us.view.*

import sa.digo.digital.util.ToolBar
import sa.digo.digital.R

class ContactUsFragment : Fragment() {
    lateinit var connectUsViewModel: ContactUsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_contact_us, container, false)
        ToolBar(getString(R.string.contact_us),false, requireActivity(),false)
        requireActivity().toolbar.visibility = View.VISIBLE
        requireActivity().home_activity.setBackgroundColor(root.context.getColor(R.color.articles_background))
        connectUsViewModel = ViewModelProvider(this).get(ContactUsViewModel::class.java)

        root.btn_send_message.setOnClickListener {
            when {

                root.et_name.text.isNullOrBlank() -> {
                    root.et_name.error=getString(R.string.empty_edittext)

                }
                root.et_email.text.isNullOrBlank() -> {
                    root.et_email.error=getString(R.string.empty_edittext)

                }
                root.et_message.text.isNullOrBlank() -> {
                    root.et_message.error=getString(R.string.empty_edittext)

                }

                else -> {
                    var hashMap= HashMap<String,String>()
                    hashMap["name"] =   root.et_name.text.toString()
                    hashMap["email"] =   root.et_email.text.toString()
                    hashMap["message"] =   root.et_message.text.toString()
                    connectUsViewModel.sendMessage(requireActivity(),hashMap)
                }
            }
        }
        return root
    }
}