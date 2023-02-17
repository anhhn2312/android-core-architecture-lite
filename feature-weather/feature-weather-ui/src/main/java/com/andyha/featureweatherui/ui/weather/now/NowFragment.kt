package com.andyha.featureweatherui.ui.weather.now

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.andyha.coreui.base.fragment.ToolbarConfiguration
import com.andyha.featureweatherui.databinding.FragmentNowBinding
import com.andyha.featureweatherui.ui.base.BaseWeatherFragment
import com.andyha.featureweatherui.ui.utils.NowGridItemDecoration
import com.andyha.featureweatherui.ui.weather.now.adapter.NowAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NowFragment :
    BaseWeatherFragment<FragmentNowBinding, NowViewModel>({ FragmentNowBinding.inflate(it) }) {

    private val adapter by lazy { NowAdapter() }

    override fun getToolbarConfiguration() =
        ToolbarConfiguration.Builder()
            .setDefaultToolbarEnabled(false)
            .setHasBackButton(false)
            .setHasRefreshLayout(true)
            .build()

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        bindData()
        super.onFragmentCreated(view, savedInstanceState)
    }

    private fun initUI() {
        viewBinding().apply {
            recyclerView.adapter = adapter
            (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup =
                object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position == 0 || position == adapter.itemCount - 1) 3
                        else 1
                    }
                }
            recyclerView.addItemDecoration(NowGridItemDecoration(requireContext(), 3))
        }
    }

    private fun bindData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentWeather.collectLatest {
                    if (adapter.itemCount == 0) {
                        adapter.submitList(it)
                    } else {
                        adapter.notifyItemRangeChanged(0, adapter.itemCount)
                    }
                }
            }
        }
    }

    override fun onRefreshView() {
        appNavigator.navigateToLogin()
//        viewModel.getCurrentWeather()
    }

    override fun onLocaleChanged() {
        super.onLocaleChanged()
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
    }

    companion object {
        fun newInstance() = NowFragment()
    }
}