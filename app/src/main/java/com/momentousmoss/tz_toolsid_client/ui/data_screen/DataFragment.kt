package com.momentousmoss.tz_toolsid_client.ui.data_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.momentousmoss.tz_toolsid_client.R
import com.momentousmoss.tz_toolsid_client.databinding.FragmentDataBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataFragment : Fragment() {

    private val dataViewModel by viewModel<DataViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_data,
            container,
            false
        )
        binding.apply {
            viewModel = dataViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        dataViewModel.apply {
            navigateToLoginFragment.observe(viewLifecycleOwner) {
                findNavController().navigate(
                    DataFragmentDirections.actionDataFragmentToLoginFragment()
                )
            }
        }
        return binding.root
    }
}