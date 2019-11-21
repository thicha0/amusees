package com.polytech.amusees.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val BASE_URL =
    "https://data.opendatasoft.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface MyApiService {
    @GET("api/records/1.0/search/?dataset=liste-et-localisation-des-musees-de-france%40culture&rows=10")
    fun getMusees(): Deferred<Response>
}

object MyApi {
    val retrofitService : MyApiService by lazy { retrofit.create(MyApiService::class.java) }
}

data class Response(
    val nhits: Int,
    val parameters: Parameters,
    val records: List<Record>
)

data class Parameters(
    val dataset: List<String>,
    val format: String,
    val rows: Int,
    val timezone: String
)

data class Record(
    val datasetid: String?,
    val fields: Fields?,
    val geometry: Geometry?,
    val record_timestamp: String?,
    val recordid: String
)

data class Fields(
    val adr: String?,
    val coordonnees_cp: List<Double>?,
    val coordonnees_finales: List<Double>?,
    val cp: String?,
    val date_appellation: String?,
    val fax: String?,
    val fermeture_annuelle: String?,
    val new_regions: String?,
    val nom_du_musee: String?,
    val nomdep: String?,
    val periode_ouverture: String?,
    val ref_musee: String?,
    val sitweb: String?,
    val telephone1: String?,
    val ville: String?
)

data class Geometry(
    val coordinates: List<Double>?,
    val type: String?
)