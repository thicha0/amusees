package com.polytech.myapplication

import io.reactivex.Observable
import retrofit2.http.GET

interface INetworkAPI {
    @GET("/api/records/1.0/search/?dataset=liste-et-localisation-des-musees-de-france%40culture&rows=2")
    fun getAllMusees(): Response
}

