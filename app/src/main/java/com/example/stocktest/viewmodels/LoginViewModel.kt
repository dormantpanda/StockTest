package com.example.stocktest.viewmodels

import com.example.stocktest.app.base.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor() : BaseViewModel() {

    fun validatePhone(phone: String): Boolean {
        val rawPhone = phone.replace(Regex("[+() ]"), "")
        if (rawPhone.length != 11) return false
        return true
    }

    fun validatePassword(password: String): Boolean {
        if (password.contains(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])"))
            && password.length > 7) return true
        return false
    }
}