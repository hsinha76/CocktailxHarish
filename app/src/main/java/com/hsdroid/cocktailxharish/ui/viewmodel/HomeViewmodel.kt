package com.hsdroid.cocktailxharish.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsdroid.cocktailxharish.data.models.Cocktail
import com.hsdroid.cocktailxharish.data.repository.HomeRepository
import com.hsdroid.cocktailxharish.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val _response: MutableStateFlow<ApiState<Cocktail>> = MutableStateFlow(ApiState.EMPTY)
    val response: StateFlow<ApiState<Cocktail>> = _response

    init {
        getDrinks("")
    }

    fun getDrinks(searchKey: String) = viewModelScope.launch {
        homeRepository.getDrinks(searchKey)
            .onStart {
                _response.value = ApiState.LOADING
            }
            .catch {
                _response.value = ApiState.FAILURE(it)
            }
            .collect {
                _response.value = ApiState.SUCCESS(it)
            }
    }
}