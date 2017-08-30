package com.natthawut.tamboon.remote

import io.reactivex.Observable
import retrofit2.http.GET

interface ApiEndpoints {

    @GET("/")
    fun getOrganizations(): Observable<List<Charity>>

}
