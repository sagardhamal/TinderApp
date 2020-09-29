package com.pibusa.tinderapp.ui

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pibusa.tinderapp.data.network.response.TinderProfileResponse
import com.pibusa.tinderapp.data.network.state.DataState
import com.pibusa.tinderapp.data.repository.MainRepository

import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val TAG = "MainViewModel"
    private val _mainViewModel = MutableLiveData<DataState<TinderProfileResponse?>?>()
    val profileLiveData: LiveData<DataState<TinderProfileResponse?>?> get() = _mainViewModel
    fun getTinderProfile(count: String) {
        viewModelScope.launch {
            _mainViewModel.postValue(DataState.Loading)
            val result = repository.getTinderProfile(count);
            if (result != null) {
                _mainViewModel.postValue(DataState.Success(result))
            }
        }

    }

}