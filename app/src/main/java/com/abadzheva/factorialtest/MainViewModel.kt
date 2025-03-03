package com.abadzheva.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

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
            val result = factorial(number)
            _state.value = Result(result.toString())
        }
    }

    private fun factorial(number: Long): Long {
        var result = 1L
        for (i in 1..number) {
            result *= i
        }
        return result
    }
}
