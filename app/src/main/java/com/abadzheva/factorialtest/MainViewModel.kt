package com.abadzheva.factorialtest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class MainViewModel : ViewModel() {
    private val coroutineScope = CoroutineScope(CoroutineName("MyScope"))
    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun calculate(inputValue: String?) {
        _state.value = Progress
        if (inputValue.isNullOrBlank()) {
            _state.value = Error
            return
        }
        coroutineScope.launch(Dispatchers.Main) {
            val number = inputValue.toLong()
            val result =
                withContext(Dispatchers.Default) {
                    factorial(number)
                }
            _state.value = Factorial(result)
            Log.d("MainViewModel", coroutineContext.toString())
        }
    }

    private suspend fun factorial(number: Long): String {
        var result = BigInteger.ONE
        for (i in 1..number) {
            result = result.multiply(BigInteger.valueOf(i))
        }
        return result.toString()
    }

//    private suspend fun factorial(number: Long): String =
//        suspendCoroutine {
//            thread {
//                var result = BigInteger.ONE
//                for (i in 1..number) {
//                    result = result.multiply(BigInteger.valueOf(i))
//                }
//                it.resumeWith(Result.success(result.toString()))
//            }
//        }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}
