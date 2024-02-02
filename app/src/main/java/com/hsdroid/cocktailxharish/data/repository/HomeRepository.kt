package com.hsdroid.cocktailxharish.data.repository

import com.hsdroid.cocktailxharish.data.models.Cocktail
import com.hsdroid.cocktailxharish.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService) {

    fun getDrinks(searchKey: String): Flow<Cocktail> = flow {
        emit(apiService.getResponse(searchKey))
    }.flowOn(Dispatchers.IO)
}