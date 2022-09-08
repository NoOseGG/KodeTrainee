package com.example.kodetrainee.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Character
import com.example.kodetrainee.adapter.CharacterAdapter
import com.example.kodetrainee.databinding.FragmentFeedBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.appcompat.widget.SearchView.OnQueryTextListener

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: FeedViewModel by viewModels()
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
        viewModel.charactersFlow.onEach { characters ->
            println(characters)
            adapter.submitList(characters)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchCharacters(newText.toString())
                return false
            }

        })


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    ALL -> showAllCharacters()
                    HUMAN -> showSpeciesCharacters(SPECIES_HUMAN)
                    ALIEN -> showSpeciesCharacters(SPECIES_ALIEN)
                    MYTHOLOGICAL -> showSpeciesCharacters(SPECIES_MYTHOLOGICAL)
                    UNKNOWN -> showSpeciesCharacters(SPECIES_UNKNOWN)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                println("${tab?.text.toString()} unselected")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.swipeRefresh.setOnRefreshListener {
            showToast("Data refreshed")
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun initial() {
        with(binding) {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }
    }

    private fun showAllCharacters() {
        viewModel.allCharacters()
    }

    private fun showSpeciesCharacters(species: String) {
        viewModel.speciesCharacters(species)
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
        const val MYTHOLOGICAL = 3
        const val UNKNOWN = 4

        const val SPECIES_HUMAN = "Human"
        const val SPECIES_ALIEN = "Alien"
        const val SPECIES_MYTHOLOGICAL = "Mythological"
        const val SPECIES_UNKNOWN = "Unknown"
    }
}