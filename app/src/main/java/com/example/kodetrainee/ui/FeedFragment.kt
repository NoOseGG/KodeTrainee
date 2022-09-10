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
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: FeedViewModel by viewModels()
    private val adapter by lazy {
        CharacterAdapter() {
            val action = FeedFragmentDirections.actionFeedFragmentToCharacterDetailsFragment(it.id)
            findNavController().navigate(action)
        }
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
        viewModel.characters.onEach { characters ->
            adapter.submitData(characters)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(searchBy: String?): Boolean {
                viewModel.setSearchBy(searchBy.toString())
                showSearchCharacters(SPECIES_ALL)
                return false
            }
        })

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position != null) checkSpecies(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                println("${tab?.text.toString()} unselected")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                if(tab?.position != null) checkSpecies(tab.position)
            }

        })

        binding.swipeRefresh.setOnRefreshListener {
            showToast("Data refreshed")
            adapter.refresh()
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

    private fun checkSpecies(species: Int) {
        when(species) {
            ALL -> showSpeciesCharacters(SPECIES_ALL)
            HUMAN -> showSpeciesCharacters(SPECIES_HUMAN)
            ALIEN -> showSpeciesCharacters(SPECIES_ALIEN)
            MYTHOLOGICAL -> showSpeciesCharacters(SPECIES_MYTHOLOGICAL)
            UNKNOWN -> showSpeciesCharacters(SPECIES_UNKNOWN)
        }
    }

    private fun showSpeciesCharacters(species: String) {
        viewModel.setSpecies(species)
        viewModel.setSearchBy(SPECIES_ALL)
        adapter.refresh()
    }

    private fun showSearchCharacters(species: String) {
        viewModel.setSpecies(SPECIES_ALL)
        adapter.refresh()
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

        const val SPECIES_ALL = ""
        const val SPECIES_HUMAN = "Human"
        const val SPECIES_ALIEN = "Alien"
        const val SPECIES_MYTHOLOGICAL = "Mythological"
        const val SPECIES_UNKNOWN = "Unknown"
    }
}