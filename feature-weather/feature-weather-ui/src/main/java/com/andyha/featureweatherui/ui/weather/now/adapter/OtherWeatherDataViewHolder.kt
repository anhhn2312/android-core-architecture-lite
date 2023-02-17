package com.andyha.featureweatherui.ui.weather.now.adapter

import com.andyha.coreui.base.adapter.BaseViewHolder
import com.andyha.featureweatherkit.data.model.CurrentWeatherData
import com.andyha.featureweatherkit.data.model.CurrentWeatherData.*
import com.andyha.featureweatherui.databinding.ViewHolderOtherWeatherDataBinding


class OtherWeatherDataViewHolder(
    private val viewBinding: ViewHolderOtherWeatherDataBinding,
) : BaseViewHolder<CurrentWeatherData>(viewBinding.root) {

    override fun bind(item: CurrentWeatherData) {
        viewBinding.apply {
            item.image?.let { icon.setImageResource(it) }
            tvTitle.text = item.title?.let { itemView.context.getString(it) }
            tvContent.text =
                when (item) {
                    Humid -> item.weather?.humidity.toString() + "%"
                    Wind -> item.weather?.windSpeed.toString() + " km/h\n" + item.weather?.windDirection
                    Pressure -> item.weather?.pressure.toString() + " mb"
                    Cloud -> item.weather?.cloudCover.toString() + "%"
                    Visibility -> item.weather?.visibility.toString() + " km"
                    Uv -> item.weather?.uv.toString()
                    else -> null
                }
        }
    }
}