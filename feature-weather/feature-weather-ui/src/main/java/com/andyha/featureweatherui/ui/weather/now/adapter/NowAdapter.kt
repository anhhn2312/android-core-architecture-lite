package com.andyha.featureweatherui.ui.weather.now.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andyha.featureweatherkit.data.model.CurrentWeatherData
import com.andyha.featureweatherkit.data.model.CurrentWeatherData.AirQuality
import com.andyha.featureweatherkit.data.model.CurrentWeatherData.Now
import com.andyha.featureweatherui.databinding.ViewHolderAirQualityBinding
import com.andyha.featureweatherui.databinding.ViewHolderCurrentWeatherBinding
import com.andyha.featureweatherui.databinding.ViewHolderOtherWeatherDataBinding


class NowAdapter :
    ListAdapter<CurrentWeatherData, RecyclerView.ViewHolder>(CurrentWeatherData.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CURRENT_WEATHER -> CurrentWeatherViewHolder(
                ViewHolderCurrentWeatherBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            VIEW_TYPE_AIR_QUALITY -> AirQualityViewHolder(
                ViewHolderAirQualityBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> OtherWeatherDataViewHolder(
                ViewHolderOtherWeatherDataBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CurrentWeatherViewHolder)?.bind(getItem(position))
        (holder as? AirQualityViewHolder)?.bind(getItem(position))
        (holder as? OtherWeatherDataViewHolder)?.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == Now) VIEW_TYPE_CURRENT_WEATHER
        else if (getItem(position) == AirQuality) VIEW_TYPE_AIR_QUALITY
        else VIEW_TYPE_OTHER_DATA
    }

    companion object {
        const val VIEW_TYPE_CURRENT_WEATHER = 1
        const val VIEW_TYPE_OTHER_DATA = 2
        const val VIEW_TYPE_AIR_QUALITY = 3
    }
}