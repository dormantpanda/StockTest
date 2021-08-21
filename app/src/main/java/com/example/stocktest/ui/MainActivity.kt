package com.example.stocktest.ui

import androidx.activity.viewModels
import com.example.stocktest.R
import com.example.stocktest.app.base.BaseActivity
import com.example.stocktest.viewmodels.MainViewModel
import com.example.stocktest.app.base.ViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.activity_main) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    val viewModel: MainViewModel by viewModels {viewModelFactory}
}