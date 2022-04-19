package sa.digo.digital.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PackagesModel(
    val success: Boolean,
    val data: List<DetailsPackagesModel>,
    val message: String
) {
    @Parcelize
    data class DetailsPackagesModel(val id: Int, val title: String,
                                    val sub_title: String, val subtype: String,
                                    val content: List<String>, val price: String,var image:String) : Parcelable
}
