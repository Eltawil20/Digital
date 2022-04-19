package sa.digo.digital.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class ServicesModel(
    val success: Boolean,
    val data: List<DetailsServiceModel>,
    val message: String
) {
    @Parcelize
    data class DetailsServiceModel(val id: Int, val title: String, val content: String,var photo:String) :
        Parcelable
}
