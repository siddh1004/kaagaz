package com.portfolio.kaagazcamera.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portfolio.kaagazcamera.data.helper.ContextProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class ViewModelBase<T : Any> : ViewModel() {

    @Inject
    lateinit var coroutineContext: ContextProvider

    @Suppress("PropertyName")
    protected val _viewState = MutableLiveData<ViewState<T>>()
    val viewState: LiveData<ViewState<T>>
        get() = _viewState

    protected fun executeUseCase(action: suspend () -> Unit) {
        _viewState.value = Loading()
        viewModelScope.launch(coroutineContext.main) { action() }
    }
}