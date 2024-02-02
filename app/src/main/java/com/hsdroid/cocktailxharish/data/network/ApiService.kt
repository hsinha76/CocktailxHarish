package com.hsdroid.cocktailxharish.data.network

import com.hsdroid.cocktailxharish.data.models.Cocktail
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search.php")
    suspend fun getResponse(@Query("s") searchKey: String): Cocktail
}