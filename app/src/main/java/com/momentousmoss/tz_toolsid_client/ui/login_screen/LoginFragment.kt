package com.momentousmoss.tz_toolsid_client.ui.login_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.momentousmoss.tz_toolsid_client.R
import com.momentousmoss.tz_toolsid_client.databinding.FragmentLoginBinding
import com.momentousmoss.tz_toolsid_client.utils.ToastMessages
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val toastMessages by inject<ToastMessages>()
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        binding.apply {
            viewModel = loginViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        loginViewModel.apply {
            navigateToTestFragment.observe(viewLifecycleOwner) {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToTestFragment(it)
                )
            }
            showToast.observe(viewLifecycleOwner) {
                toastMessages.showMessage(it)
            }
        }
        return binding.root
    }
}