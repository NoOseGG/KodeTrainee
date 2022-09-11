package com.example.kodetrainee.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kodetrainee.databinding.FragmentErrorBinding

class DefaultLoadStateAdapter(
    private val onClick: () -> Unit
) : LoadStateAdapter<LoadStateHolder>() {
    override fun onBindViewHolder(holder: LoadStateHolder, loadState: LoadState) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateHolder =
        LoadStateHolder(
            FragmentErrorBinding.inflate(
                LayoutInflater.from(parent.context)
            ),
            onClick = onClick
        )
}

class LoadStateHolder(
    private val binding: FragmentErrorBinding,
    private val onClick: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        onClick()
    }
}
