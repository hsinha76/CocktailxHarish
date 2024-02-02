package com.hsdroid.cocktailxharish.utils

sealed class ApiState<out T> {
    data object EMPTY : ApiState<Nothing>()
    data object LOADING : ApiState<Nothing>()
    class SUCCESS<out T>(val data: T) : ApiState<T>()
    class FAILURE(val throwable: Throwable) : ApiState<Nothing>()
}