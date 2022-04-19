package sa.digo.digital.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class SliderModel(
    val success: Boolean,
    val data: List<DetailsSliderModel>,
    val message: String
) {
    @Parcelize
    data class DetailsSliderModel(
        val title: String, val sub_title: String,
        val content: String, val more_btn: String,
        val more_link: String, var background: String
    ) :
        Parcelable
}
