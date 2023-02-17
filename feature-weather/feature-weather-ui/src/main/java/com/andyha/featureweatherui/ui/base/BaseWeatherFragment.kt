package com.andyha.featureweatherui.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.andyha.coreextension.openAppSettings
import com.andyha.coreui.base.fragment.BaseFragment
import com.andyha.coreui.base.ui.widget.textview.BaseTextView
import com.andyha.coreutils.setOnDebounceClick
import com.andyha.featureweatherkit.data.model.LocationState.PermissionDenied
import com.andyha.featureweatherkit.data.model.LocationState.PermissionDeniedForever
import com.andyha.featureweatherui.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


abstract class BaseWeatherFragment<VB : ViewBinding, VM : BaseWeatherViewModel>(binding: (LayoutInflater) -> VB) :
    BaseFragment<VB, VM>(binding) {

    private val llPermissionSettings: View by lazy { requireView().findViewById(R.id.llPermissionSetting) }
    private val tvMessage: BaseTextView by lazy { requireView().findViewById(R.id.tvMessage) }
    private val btnAction: BaseTextView by lazy { requireView().findViewById(R.id.btnAction) }

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getCurrentLocationState()
        bindData()
    }

    private fun bindData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentLocationState.collectLatest {
                    when (it) {
                        PermissionDenied -> onPermissionDenied()
                        PermissionDeniedForever -> onPermissionDeniedForever()
                        else -> llPermissionSettings.isVisible = false
                    }
                }
            }
        }
    }

    private fun onPermissionDenied() {
        llPermissionSettings.isVisible = true
        tvMessage.setCustomText(
            R.string.grant_location_permission_message,
            getString(R.string.app_name)
        )
        btnAction.setCustomText(R.string.allow)
        btnAction.setOnDebounceClick { viewModel.requestLocationUpdate() }
    }

    private fun onPermissionDeniedForever() {
        llPermissionSettings.isVisible = true
        tvMessage.setCustomText(R.string.location_permission_denied_forever_message)
        btnAction.setCustomText(R.string.settings)
        btnAction.setOnDebounceClick { requireContext().openAppSettings() }
    }

    override fun getBaseRefreshFragmentLayout(): Int {
        return R.layout.fragment_base_weather
    }

    override fun hasBackground(): Boolean {
        return true
    }
}