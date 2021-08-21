package com.example.stocktest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.stocktest.R
import com.example.stocktest.app.base.BaseFragment
import com.example.stocktest.app.base.ViewModelFactory
import com.example.stocktest.databinding.FragmentLoginBinding
import com.example.stocktest.viewmodels.LoginViewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import javax.inject.Inject

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: LoginViewModel by viewModels {viewModelFactory}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViews() = with(binding) {
        val slots = UnderscoreDigitSlotsParser().parseSlots(PHONE_MASK)
        val watcher: FormatWatcher = MaskFormatWatcher(MaskImpl.createTerminated(slots))
        watcher.installOn(etPhone)

        //preventing bug with deleting start of mask
        etPhone.doAfterTextChanged {
            if (it?.length in 1..4){
                etPhone.setSelection(4)
            }
        }

        btLogin.setOnClickListener {
            var isPasswordCorrect = false
            var isPhoneCorrect = false
            if (viewModel.validatePassword(etPassword.text.toString()).not()){
                tilPassword.error = getString(R.string.error_password)
            } else {
                tilPassword.error = null
                isPasswordCorrect = true
            }
            if (viewModel.validatePhone(etPhone.text.toString()).not()){
                tilPhone.error = getString(R.string.error_phone)
            } else {
                tilPhone.error = null
                isPhoneCorrect = true
            }
            if (isPasswordCorrect && isPhoneCorrect){
                findNavController().navigate(R.id.action_loginFragment_to_stockListFragment)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val PHONE_MASK = "+7 (___) ___ __ __"
    }
}