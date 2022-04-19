package sa.digo.digital.api

import retrofit2.Call
import retrofit2.http.*
import sa.digo.digital.model.*

interface ApiInterface {

    @GET("blogs/{id}")
    fun getBlogs(@Path("id")id:String) : Call<ArticlesModel>
    @GET("services")
    fun getServices() : Call<ServicesModel>
    @GET("packages")
    fun getPackages() : Call<PackagesModel>
    @POST("order")
    fun sendService(@Body hashMap: Map<String, String>) : Call<Status>
    @POST("contact")
    fun sendContact(@Body hashMap: Map<String, String>) : Call<Status>
    @GET("sliders")
    fun slider() : Call<SliderModel>
//    @GET("constants")
//    fun constants() : Call<Constants>
//    @POST("delivery_box")
//    fun deliveryBox(@Body sendDeliveryBox: SendDeliveryBox): Call<DeliveryBox>


}