package com.momentousmoss.tz_toolsid_client.ui.test_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.momentousmoss.tz_toolsid_client.R
import com.momentousmoss.tz_toolsid_client.databinding.FragmentTestBinding
import com.momentousmoss.tz_toolsid_client.utils.DPCManager
import com.momentousmoss.tz_toolsid_client.utils.ToastMessages
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestFragment : Fragment() {

    private val testViewModel by viewModel<TestViewModel>()
    private val toastMessages by inject<ToastMessages>()
    private val dpcManager by inject<DPCManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        arguments?.let {
            testViewModel.init(TestFragmentArgs.fromBundle(requireArguments()).token)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTestBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_test,
            container,
            false
        )
        binding.apply {
            viewModel = testViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        testViewModel.apply {
            navigateToLoginFragment.observe(viewLifecycleOwner) {
                findNavController().navigate(
                    TestFragmentDirections.actionTestFragmentToLoginFragment()
                )
            }
            showToast.observe(viewLifecycleOwner) {
                toastMessages.showMessage(it)
            }
            blockUI.observe(viewLifecycleOwner) {
                dpcManager.blockUI()
            }
            unblockUI.observe(viewLifecycleOwner) {
                dpcManager.unblockUI { this@TestFragment.activity?.stopLockTask() }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}
        return binding.root
    }
}