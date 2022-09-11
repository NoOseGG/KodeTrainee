package com.example.kodetrainee.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.CharacterDetailsRepositoryImpl
import com.example.domain.model.CharacterDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepositoryImpl
) : ViewModel() {

    val characterFlow = MutableSharedFlow<CharacterDetails>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun character(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            characterDetailsRepository.character(id)
                .onSuccess {
                    characterFlow.tryEmit(it)
                }
        }
    }
}