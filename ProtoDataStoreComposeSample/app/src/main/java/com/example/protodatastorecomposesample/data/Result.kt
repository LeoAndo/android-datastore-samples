package com.example.protodatastorecomposesample.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * A generic class that holds a value or an exception
 */
internal sealed class SafeResult<out R> {
    data class Success<out T>(val data: T) : SafeResult<T>()
    data class Error(val errorResult: ErrorResult) : SafeResult<Nothing>()
}

internal sealed class ErrorResult : Exception() {
    data class UnknownError(override val message: String? = "UnknownError") :
        ErrorResult()
}

internal inline fun <T> SafeResult<T>.fold(
    onSuccess: (value: T) -> Unit,
    onFailure: (ErrorResult) -> Unit,
): SafeResult<T> {
    when (this) {
        is SafeResult.Error -> {
            onFailure(errorResult)
            Log.e("fold", "error: " + errorResult.localizedMessage)
        }
        is SafeResult.Success -> {
            onSuccess(data)
        }
    }
    return this
}

internal suspend fun <T> safeCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): SafeResult<T> {
    return withContext(dispatcher) {
        Log.d("safeCall", "currentThread: " + Thread.currentThread().name)
        try {
            SafeResult.Success(apiCall.invoke())
        } catch (e: Throwable) {
            Log.e("safeCall", "error: " + e.localizedMessage)
            when (e) {
                else -> {
                    SafeResult.Error(
                        ErrorResult.UnknownError(
                            e.localizedMessage
                        )
                    )
                }
            }
        }
    }
}
