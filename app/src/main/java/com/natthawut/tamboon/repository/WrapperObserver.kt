package com.natthawut.tamboon.repository

import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class WrapperObserver<T> : DisposableObserver<T>() {

    abstract fun success(t: T)
    abstract fun failure(errorMessage: String?)

    override fun onError(e: Throwable) {
        when (e) {
            is HttpException -> {
                when (e.code()) {
                    400 -> failure(getErrorMessage(e.response().errorBody()))
                    401 -> failure("Authentication error.")
                    404 -> failure("Request not found.")
                    500 -> failure("Server has problem")
                    else -> failure("Unknown error.")
                }
            }
            is SocketTimeoutException -> failure("Request time out.")
            is IOException -> failure("Network has problem.")
            else -> failure("Unknown error.")
        }
    }

    override fun onComplete() {
    }

    override fun onNext(@NonNull t: T) {
        success(t)
    }

    fun getErrorMessage(responseBody: ResponseBody?): String? {
        val bodyString = responseBody?.string()
        if (bodyString != null) {
            return bodyString.substring(bodyString.indexOf(") ") + 2)
                    .capitalize()
                    .trim()
        }
        return null
    }
}