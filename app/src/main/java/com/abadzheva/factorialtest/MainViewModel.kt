package com.abadzheva.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
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
            delay(1000)
            // calculate factorial
            _state.value = Result(number.toString())
        }
    }
}
