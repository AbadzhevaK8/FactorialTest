package com.abadzheva.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class MainViewModel : ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun calculate(inputValue: String?) {
        _state.value = Progress
        if (inputValue.isNullOrBlank()) {
            _state.value = Error
            return
        }
        viewModelScope.launch {
            val number = inputValue.toLong()
            val result =
                withContext(Dispatchers.Default) {
                    factorial(number)
                }
            _state.value = Factorial(result)
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
}
