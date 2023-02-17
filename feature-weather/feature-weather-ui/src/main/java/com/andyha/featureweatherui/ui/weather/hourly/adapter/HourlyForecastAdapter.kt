package com.andyha.featureweatherui.ui.weather.hourly.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.andyha.coreui.base.adapter.multiSectionsSelection.BaseMultiSectionListAdapter
import com.andyha.featureweatherkit.data.model.HourlyForecast
import com.andyha.featureweatherui.databinding.ViewHolderHourlyForecastBinding
import com.andyha.featureweatherui.databinding.ViewHolderWeekDayBinding


class HourlyForecastAdapter :
    BaseMultiSectionListAdapter<Long, HourlyForecast>(HourlyForecast.DIFF_CALLBACK) {

    override fun createHeaderViewHolder(parent: ViewGroup) =
        WeekDayViewHolder(
            ViewHolderWeekDayBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun createItemViewHolder(parent: ViewGroup) =
        HourlyForecastViewHolder(
            ViewHolderHourlyForecastBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun isItemSelectable() = false
}