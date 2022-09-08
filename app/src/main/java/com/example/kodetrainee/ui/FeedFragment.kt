package com.example.kodetrainee.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.kodetrainee.R
import com.example.kodetrainee.adapter.CharacterAdapter
import com.example.kodetrainee.databinding.FragmentFeedBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: FeedViewModel by viewModels()
    private var _characters: List<Character>? = null
    private val characters get() = requireNotNull(_characters)
    private val adapter by lazy {
        CharacterAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFeedBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initial()
        viewModel.allCharacters()
        viewModel.charactersFlow.onEach {
            _characters = it.results
            println(it.results)
            adapter.submitList(it.results)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    ALL -> showAllCharacters()
                    HUMAN -> showSpeciesCharacters("Human")
                    ALIEN -> showSpeciesCharacters("Alien")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                println("${tab?.text.toString()} unselected")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun initial() {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
        }
    }

    private fun showAllCharacters() {
        adapter.submitList(characters)
    }

    private fun showSpeciesCharacters(species: String) {
        val speciesList = characters?.filter {
            it.species == species
        }
        adapter.submitList(speciesList)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val ALL = 0
        const val HUMAN = 1
        const val ALIEN = 2
    }
}