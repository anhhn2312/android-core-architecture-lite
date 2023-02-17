package com.andyha.featureweatherui.ui.weather.daily.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.andyha.coreui.base.adapter.BaseListAdapter
import com.andyha.coreui.base.adapter.BaseViewHolder
import com.andyha.featureweatherkit.data.model.DailyForecast
import com.andyha.featureweatherui.databinding.ViewHolderDailyForecastBinding


class DailyForecastAdapter : BaseListAdapter<DailyForecast>(DailyForecast.DIFF_CALLBACK) {

    private var highestTemp: Int = 0
    private var lowestTemp: Int = 0


    override fun getViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): ViewBinding {
        return ViewHolderDailyForecastBinding.inflate(layoutInflater, parent, false)
    }

    override fun createViewHolder(viewBinding: ViewBinding): BaseViewHolder<DailyForecast> {
        return DailyForecastViewHolder(
            viewBinding as ViewHolderDailyForecastBinding,
            highestTemp,
            lowestTemp
        )
    }

    override fun submitList(list: List<DailyForecast>?) {
        list?.let { list ->
            if(list.isEmpty()) return@let
            highestTemp = list.maxOf { it.maxTemp ?: 0 }
            lowestTemp = list.minOf { it.minTemp ?: 0 }
        }
        super.submitList(list)
    }
}