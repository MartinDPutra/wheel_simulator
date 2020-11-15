package com.martinezdputra.wheel.repository

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random")
    fun getRandomNumber(@Query("min") min: Int = 0,
                      @Query("max") max: Int = 100,
                      @Query("count") count: Int = 1): Observable<Array<Int>>
}