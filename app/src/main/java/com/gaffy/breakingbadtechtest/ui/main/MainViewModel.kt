package com.gaffy.breakingbadtechtest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gaffy.breakingbadtechtest.data.model.BreakingBadChar
import com.gaffy.breakingbadtechtest.data.repository.BreakingBadRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainViewModel(private val breakingBadRepository: BreakingBadRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val _state = MutableLiveData<ViewState>()
    var allCharacters: List<BreakingBadChar>? = null
    val state: LiveData<ViewState>
        get() = _state

    fun getBreakingBadChars(characterName: String = "", season: Int = 0) {
        _state.postValue(ViewState.InProgress)
        allCharacters?.let { result ->
            filterResult(result, characterName, season)
        } ?: disposable.add(
            breakingBadRepository.getBreakingBadCharacters()
                .subscribe({ response ->
                    allCharacters = response
                    filterResult(response, characterName, season)
                }, { throwable ->
                    _state.postValue(
                        ViewState.Error(
                            throwable.localizedMessage ?: "Unknown Error"
                        )
                    )
                })
        )
    }

    private fun filterResult(response: List<BreakingBadChar>, characterName: String, season: Int) {
        val result = response.filter { character ->
            characterName.isEmpty() ||
                    character.name.toLowerCase().startsWith(characterName.toLowerCase())
        }.filter { character -> season == 0 || character.appearance.contains(season) }

        if (result.isEmpty()) {
            _state.postValue(ViewState.Error("No Result Found"))
        } else {
            _state.postValue(ViewState.Success(result))
        }
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