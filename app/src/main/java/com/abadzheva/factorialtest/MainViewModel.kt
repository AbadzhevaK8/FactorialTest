package com.abadzheva.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    private val _factorial = MutableLiveData<String>()
    val factorial: LiveData<String>
        get() = _factorial

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get() = _progress

    fun calculate(inputValue: String?) {
        _progress.value = true
        if (inputValue.isNullOrBlank()) {
            _progress.value = false
            _error.value = true
            return
        }
        viewModelScope.launch {
            val number = inputValue.toLong()
            delay(1000)
            // calculate factorial
            _progress.value = false
            _factorial.value = number.toString()
        }
    }
}
