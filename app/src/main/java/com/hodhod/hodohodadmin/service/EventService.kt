package com.hodhod.hodohodadmin.service

import io.reactivex.Completable
import retrofit2.http.GET

interface EventService {
    @GET("eventtypes")
    fun getEventsTypes(): Completable
}