package com.example.stocktest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stocktest.R
import com.example.stocktest.app.base.BaseFragment
import com.example.stocktest.app.base.ViewModelFactory
import com.example.stocktest.databinding.FragmentStockInformationBinding
import com.example.stocktest.viewmodels.StockInformationViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class StockInformationFragment : BaseFragment(R.layout.fragment_stock_information) {

    private var _binding: FragmentStockInformationBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<StockInformationFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: StockInformationViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.candlesResponse.observe(viewLifecycleOwner) { prices ->
            binding.chart.apply {
                setPoints(prices)
                isVisible = true
            }
            binding.progress.isVisible = false
        }
        viewModel.getStockCandles(args.symbol)
    }

    override fun initViews() = with(binding) {
        toolbar.apply {
            title = args.symbol
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
        btBuy.setOnClickListener {
            Snackbar.make(root, getString(R.string.snack_buy), Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}