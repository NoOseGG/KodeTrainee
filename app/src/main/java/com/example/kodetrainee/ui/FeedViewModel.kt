package com.example.kodetrainee.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.CharacterRepositoryImpl
import com.example.domain.model.Characters
import com.example.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val characterRepository: CharacterRepositoryImpl
) : ViewModel() {

    val charactersFlow = MutableSharedFlow<Characters>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun allCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            println("11111111111111111111111111111111")
            characterRepository.characters()
                .onSuccess {
                    println("SUCCESS")
                    charactersFlow.tryEmit(it)
                }
                .onFailure {
                    println("222222222222222222222222 ${it.message}")
                }
        }
    }
}