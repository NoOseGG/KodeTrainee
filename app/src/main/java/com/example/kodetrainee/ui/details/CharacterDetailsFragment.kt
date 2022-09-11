package com.example.kodetrainee.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.domain.model.Character
import com.example.domain.model.CharacterDetails
import com.example.kodetrainee.R
import com.example.kodetrainee.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val args: CharacterDetailsFragmentArgs by navArgs()
    private val viewModel: CharacterDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCharacterDetailsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.character(args.characterId)

        viewModel.characterFlow.onEach { character ->
            updateUi(character)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        with(binding) {
            imgArrowBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun updateUi(character: CharacterDetails) {
        with(binding) {
            imgCharacterDetailsAvatar.load(character.image)
            tvCharacterDetailsName.text = character.name
            tvCharacterDetailsSpecies.text = character.species
            tvStatus.text = getString(R.string.fragment_details_status, character.status)
            tvType.text = getString(R.string.fragment_details_type, character.type)
            tvGender.text = getString(R.string.fragment_details_gender, character.gender)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}