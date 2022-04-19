package sa.digo.digital.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ArticlesModel(
    val success: Boolean,
    val data: List<DetailsArticlesModel>,
    val message: String
) {
    @Parcelize
    data class DetailsArticlesModel(val id: Int, val title: String, val content: String,var photo:String) :
        Parcelable
}
