package com.example.stocktest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stocktest.R
import com.example.stocktest.app.base.BaseFragment
import com.example.stocktest.app.base.ViewModelFactory
import com.example.stocktest.databinding.FragmentStockListBinding
import com.example.stocktest.ui.adapters.StockListAdapter
import com.example.stocktest.ui.adapters.StockLoadStateAdapter
import com.example.stocktest.viewmodels.StockListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class StockListFragment : BaseFragment(R.layout.fragment_stock_list) {

    private var _binding: FragmentStockListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: StockListViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var stockAdapter: StockListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.progress.isVisible = true
        lifecycleScope.launch {
            viewModel.stocks.collectLatest { items ->
                stockAdapter.submitData(items)
            }
        }
    }

    override fun initViews() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            val loadStateAdapter = StockLoadStateAdapter { stockAdapter.retry() }
            rvStockList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = stockAdapter.apply {
                    onItemClickListener = { stock ->
                        val action =
                            StockListFragmentDirections
                                .actionStockListFragmentToStockInformationFragment(stock.symbol)
                        findNavController().navigate(action)
                    }
                }.withLoadStateFooter(loadStateAdapter)
            }
            lifecycleScope.launch {
                stockAdapter.loadStateFlow.collect {
                    if (it.prepend is LoadState.NotLoading && it.prepend.endOfPaginationReached) {
                        progress.isVisible = false
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}