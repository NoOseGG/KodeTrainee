package com.example.kodetrainee.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.model.Character
import com.example.kodetrainee.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val onClick: (Character) -> Unit
) : PagingDataAdapter<Character, CharacterViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context)
            ),
            onClick = onClick
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(item) }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class CharacterViewHolder(
    private val binding: ItemCharacterBinding,
    private val onClick: (Character) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Character) {
        with(binding) {
            imgAvatar.load(item.image)
            tvCharacterName.text = item.name
            tvCharacterSpecies.text = item.species
            root.setOnClickListener {
                onClick(item)
            }
        }
    }
}
