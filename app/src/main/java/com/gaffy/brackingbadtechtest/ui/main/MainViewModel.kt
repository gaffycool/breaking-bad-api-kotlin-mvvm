package com.gaffy.brackingbadtechtest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gaffy.brackingbadtechtest.data.model.BreakingBadChar
import com.gaffy.brackingbadtechtest.data.repository.BreakingBadRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainViewModel(private val breakingBadRepository: BreakingBadRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState>
        get() = _state

    fun getBreakingBadChars() {
        _state.postValue(ViewState.InProgress)
        disposable.add(
            breakingBadRepository.getBreakingBadCharacters()
                .subscribe({ response ->
                    _state.postValue(ViewState.Success(response))
                }, { throwable ->
                    _state.postValue(
                        ViewState.Error(
                            throwable.localizedMessage
                        )
                    )
                })
        )
    }

    sealed class ViewState {
        object InProgress : ViewState()
        data class Success(val characters: List<BreakingBadChar>) : ViewState()
        data class Error(val errorMessage: String) : ViewState()
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}