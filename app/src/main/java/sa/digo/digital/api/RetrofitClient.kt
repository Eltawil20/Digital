package sa.digo.digital.api


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
//    http://10.0.0.62:8000/api/
//    const val MainServer = "http://192.168.137.1:8000/api/"
    const val MainServer = "https://digo.sa/api/"
    const val api = "https://digo.sa/"
//    const val MainServer = "http://10.0.0.62:8000/api/"
//    const val MainServer = "http://162.214.75.119/~boxes/api/"

    val retrofitClient: Retrofit.Builder by lazy {

//        val interceptor = Interceptor { chain ->
//            val newRequest = chain.request().newBuilder()
//                .addHeader("Accept", "application/json")
//                .addHeader("Authorization", "Bearer " + LoginActivity.sessionManager.getString("token"))
//                .build()
//            chain.proceed(newRequest)
//        }
        val builder = OkHttpClient.Builder()
//        builder.interceptors().add(interceptor)
        val client: OkHttpClient = builder.build()
        Retrofit.Builder()
            .baseUrl(MainServer)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiInterface by lazy {
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }
}
