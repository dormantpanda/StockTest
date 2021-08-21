package com.example.stocktest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.stocktest.R
import com.example.stocktest.data.models.Stock
import com.example.stocktest.databinding.ItemStockBinding
import javax.inject.Inject

class StockListAdapter @Inject constructor() :
    PagingDataAdapter<Stock, StockListAdapter.StockViewHolder>(StockComparator) {

    var onItemClickListener: ((Stock) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            ItemStockBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    inner class StockViewHolder(
        private val binding: ItemStockBinding,
        private val listener: ((Stock) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stock: Stock) = with(binding) {
            tvCompanyCode.text = stock.symbol
            if (stock.price != null && stock.price != 0.0) {
                tvNotAvailable.isVisible = false
                tvStockPrice.isVisible = true
                tvStockPrice.text = stock.price.toString()
                tvChange.isVisible = true
                stock.change?.let { change ->
                    if (change < 0) {
                        tvChange.text = change.toString()
                        tvChange.setTextColor(
                            root.resources.getColor(
                                R.color.red,
                                root.context.theme
                            )
                        )
                    } else {
                        val positiveChange = "+$change"
                        tvChange.text = positiveChange
                        tvChange.setTextColor(
                            root.resources.getColor(
                                R.color.green,
                                root.context.theme
                            )
                        )
                    }
                }

                root.setOnClickListener {
                    listener?.invoke(stock)
                }

                root.setBackgroundColor(
                    root.resources.getColor(
                        R.color.white,
                        root.context.theme
                    )
                )
            } else {
                tvNotAvailable.isVisible = true
                tvStockPrice.isVisible = false
                tvChange.isVisible = false

                root.setOnClickListener {}

                root.setBackgroundColor(
                    root.resources.getColor(
                        R.color.light_grey,
                        root.context.theme
                    )
                )
            }

            Glide.with(root.context)
                .load(stock.logo)
                .apply(RequestOptions().placeholder(R.drawable.ic_office_building))
                .into(ivCompanyLabel)
        }
    }

    object StockComparator : DiffUtil.ItemCallback<Stock>() {
        override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
            return oldItem == newItem
        }

    }
}