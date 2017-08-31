package com.natthawut.tamboon.remote

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

open class ApiRemote(baseUrl: String) {

    private var apiEndpoints: ApiEndpoints

    init {
        val gson = GsonBuilder()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        apiEndpoints = retrofit.create(ApiEndpoints::class.java)
    }

    fun getOrganizations(): Observable<List<Charity>> {
        return apiEndpoints.getOrganizations()
    }

    fun donate(donation: Donation): Observable<DonationResponse> {
        return apiEndpoints.donate(donation)
    }

}
