package com.andyha.featureweatherui.ui.weather.daily

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.andyha.coreui.base.fragment.ToolbarConfiguration
import com.andyha.coreui.base.theme.ThemeManager
import com.andyha.featureweatherui.databinding.FragmentDailyBinding
import com.andyha.featureweatherui.ui.base.BaseWeatherFragment
import com.andyha.featureweatherui.ui.weather.daily.adapter.DailyForecastAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DailyFragment :
    BaseWeatherFragment<FragmentDailyBinding, DailyViewModel>({ FragmentDailyBinding.inflate(it) }) {

    override fun getToolbarConfiguration() =
        ToolbarConfiguration.Builder().setDefaultToolbarEnabled(false).setHasBackButton(false)
            .setHasRefreshLayout(true).build()

    private val adapter by lazy { DailyForecastAdapter() }

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        bindData()
        super.onFragmentCreated(view, savedInstanceState)
    }

    private fun initUI() {
        viewBinding().apply {
            rvDaily.adapter = adapter
            rvDaily.addItemDecoration(
                DividerItemDecoration(
                    requireContext(), LinearLayoutManager.HORIZONTAL
                )
            )
        }
    }

    private fun bindData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dailyForecast.collect {
                    adapter.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                ThemeManager.onThemeChanged.collect { onThemeChanged() }
            }
        }
    }

    private fun onThemeChanged() {
        viewBinding().apply {
            rvDaily.removeItemDecorationAt(0)
            rvDaily.addItemDecoration(
                DividerItemDecoration(
                    requireContext(), LinearLayoutManager.HORIZONTAL
                )
            )
        }
    }

    override fun onRefreshView() {
        viewModel.getDailyForecast()
    }

    override fun onLocaleChanged() {
        super.onLocaleChanged()
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
    }

    companion object {
        fun newInstance() = DailyFragment()
    }
}