package com.momentousmoss.tz_toolsid_client.ui.test_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.momentousmoss.tz_toolsid_client.DELAY_JOB_CHECK_BLOCK
import com.momentousmoss.tz_toolsid_client.R
import com.momentousmoss.tz_toolsid_client.api.JsonService
import com.momentousmoss.tz_toolsid_client.databinding.FragmentTestBinding
import com.momentousmoss.tz_toolsid_client.utils.DPCManager
import com.momentousmoss.tz_toolsid_client.utils.ToastMessages
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestFragment : Fragment() {

    private val testViewModel by viewModel<TestViewModel>()
    private val toastMessages by inject<ToastMessages>()
    private val dpcManager by inject<DPCManager>()

    private var checkBlockJob: Job? = null

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
            logout.observe(viewLifecycleOwner) {
                this@TestFragment.logout()
            }
            fillTestData.observe(viewLifecycleOwner) {
                fillUi(binding, it)
            }
            changeBlockUi.observe(viewLifecycleOwner) {
                changeBlockUi(it, binding.blockedUiView)
            }
            showToast.observe(viewLifecycleOwner) {
                showToast(it)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}
        return binding.root
    }

    private fun logout() {
        if (!dpcManager.isLocked()) {
            checkBlockJob?.cancel()
            findNavController().navigate(
                TestFragmentDirections.actionTestFragmentToLoginFragment()
            )
        }
    }

    private fun fillUi(binding: FragmentTestBinding, testData: JsonService.TestData?) {
        binding.apply {
            val user = testData?.user
            if (user != null) {
                userName.apply {
                    visibility = View.VISIBLE
                    text = resources.getString(
                        R.string.test_user_text, user.name, user.email
                    )
                }
            }
            payloadList.apply {
                val listPayload = testData?.qr?.payload
                if (!listPayload.isNullOrEmpty()) {
                    adapter = PayloadListAdapter(listPayload)
                }
            }
        }
    }

    private fun changeBlockUi(isBlocked: Boolean?, blockedUiView: AppCompatCheckBox) {
        if (isBlocked == null) {
            showToast(R.string.test_toast_error_request)
            testViewModel.logout()
        } else {
            blockedUiView.isChecked = isBlocked
            if (isBlocked == true) blockUi() else unblockUI()
            checkBlockJob?.cancel()
            checkBlockJob = this.testViewModel.viewModelScope.launch {
                delay(DELAY_JOB_CHECK_BLOCK)
                testViewModel.checkBlock()
            }
        }
    }

    private fun blockUi() {
        dpcManager.blockUI()
    }

    private fun unblockUI() {
        dpcManager.unblockUI { this@TestFragment.activity?.stopLockTask() }
    }

    private fun showToast(messageResId: Int) {
        toastMessages.showMessage(messageResId)
    }
}