package com.example.kodetrainee.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.data.repository.CharacterRepositoryImpl
import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.domain.repository.CharacterRepository
import com.example.kodetrainee.paging.CharacterPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val characterRepository: CharacterRepositoryImpl,
) : ViewModel() {

    private var species = ""
    private var searchBy = ""
    val characters = Pager(PagingConfig( pageSize = 20, enablePlaceholders = true, initialLoadSize = 20)) {
        CharacterPagingSource(characterRepository, species, searchBy)
    }.flow.cachedIn(viewModelScope)

    val charactersFlow = MutableSharedFlow<List<Character>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun allCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.characters(1)
                .onSuccess {
                    println(it.results)
                    charactersFlow.tryEmit(it.results)
                }
                .onFailure {
                }
        }
    }

    fun setSpecies(species: String) {
        this.species = species
    }

    fun setSearchBy(searchBy: String) {
        this.searchBy = searchBy
    }
}