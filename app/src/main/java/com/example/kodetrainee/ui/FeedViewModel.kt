package com.example.kodetrainee.ui

import androidx.lifecycle.ViewModel
import com.example.data.repository.CharacterRepositoryImpl
import com.example.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val characterRepository: CharacterRepositoryImpl
) : ViewModel() {
}