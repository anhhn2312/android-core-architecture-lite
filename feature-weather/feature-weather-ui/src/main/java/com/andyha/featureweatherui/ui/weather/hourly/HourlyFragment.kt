package com.andyha.featureweatherui.ui.weather.hourly

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.andyha.coreui.base.fragment.ToolbarConfiguration
import com.andyha.featureweatherui.databinding.FragmentHourlyBinding
import com.andyha.featureweatherui.ui.base.BaseWeatherFragment
import com.andyha.featureweatherui.ui.weather.hourly.adapter.HourlyForecastAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HourlyFragment :
    BaseWeatherFragment<FragmentHourlyBinding, HourlyViewModel>({ FragmentHourlyBinding.inflate(it) }) {

    override fun getToolbarConfiguration() =
        ToolbarConfiguration.Builder()
            .setDefaultToolbarEnabled(false)
            .setHasBackButton(false)
            .setHasRefreshLayout(true)
            .build()

    private val adapter by lazy { HourlyForecastAdapter() }

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        bindData()
        super.onFragmentCreated(view, savedInstanceState)
    }

    private fun initUI() {
        viewBinding().apply {
            rvHourly.adapter = adapter
        }
    }

    private fun bindData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hourlyForecast.collect {
                    adapter.submit(it)
                }
            }
        }
    }

    override fun onRefreshView() {
        viewModel.getHourlyForecast()
    }

    override fun onLocaleChanged() {
        super.onLocaleChanged()
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
    }

    companion object {
        fun newInstance() = HourlyFragment()
    }
}