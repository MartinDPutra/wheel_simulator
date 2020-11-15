package com.martinezdputra.wheel.repository

import io.reactivex.Observable
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {
    fun getRandomNumber(): Observable<Int> {
        return apiService.getRandomNumber().map {
            return@map it[0]
        }
    }
}