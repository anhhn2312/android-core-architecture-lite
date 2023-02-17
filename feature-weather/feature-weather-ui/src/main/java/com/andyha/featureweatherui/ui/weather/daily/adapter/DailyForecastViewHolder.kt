package com.andyha.featureweatherui.ui.weather.daily.adapter

import androidx.constraintlayout.widget.ConstraintLayout
import com.andyha.coreui.base.adapter.BaseViewHolder
import com.andyha.featureweatherkit.data.model.DailyForecast
import com.andyha.featureweatherui.R
import com.andyha.featureweatherui.databinding.ViewHolderDailyForecastBinding


class DailyForecastViewHolder(
    private val viewBinding: ViewHolderDailyForecastBinding,
    private val highestTemp: Int,
    private val lowestTemp: Int,
) : BaseViewHolder<DailyForecast>(viewBinding.root) {

    override fun bind(item: DailyForecast) {
        viewBinding.apply {
            dailyForecast = item
            (tvMaxTemp.layoutParams as ConstraintLayout.LayoutParams).verticalBias =
                (highestTemp - (item.maxTemp ?: highestTemp)).toFloat() / (highestTemp - lowestTemp)
            (tvMinTemp.layoutParams as ConstraintLayout.LayoutParams).verticalBias =
                1- ((item.minTemp ?: lowestTemp) - lowestTemp).toFloat() / (highestTemp - lowestTemp)
            temperatureBar.setBackgroundResource(R.drawable.bg_temperature_range)
        }
    }
}