package org.d3if3027.assesment1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3027.assesment1.model.Makanan
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/GabrielDFA/Assesment1/internet/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MakananService {
    @GET("makanan.json")
    suspend fun getMakanan(): List<Makanan>
}

object MakananApi{
    val service: MakananService by lazy {
        retrofit.create(MakananService ::class.java)
    }

    fun getMakananUrl(image_id: String): String {
        return "$BASE_URL$image_id.jpeg"
    }

}
enum class ApiStatus { LOADING, SUCCESS, FAILED }

