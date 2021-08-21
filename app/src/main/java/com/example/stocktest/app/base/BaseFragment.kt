package com.example.stocktest.app.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import dagger.android.support.DaggerFragment

abstract class BaseFragment(@LayoutRes layoutId: Int) : DaggerFragment(layoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    abstract fun initViews()
}