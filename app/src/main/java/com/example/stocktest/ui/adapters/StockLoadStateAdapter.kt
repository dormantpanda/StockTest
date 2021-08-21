package com.example.stocktest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stocktest.R
import com.example.stocktest.databinding.LoadStateItemBinding

class StockLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<StockLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(parent, retry)

    inner class LoadStateViewHolder(parent: ViewGroup, private val retry: () -> Unit) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_item, parent, false)
        ) {

        private val binding = LoadStateItemBinding.bind(itemView)
        fun bind(loadState: LoadState) = with(binding) {

            btRetry.setOnClickListener {
                retry
            }

            btRetry.isVisible = loadState is LoadState.Error
            progress.isVisible = loadState is LoadState.Loading
        }
    }
}