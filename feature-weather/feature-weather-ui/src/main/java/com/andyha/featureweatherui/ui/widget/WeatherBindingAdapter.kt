package com.andyha.featureweatherui.ui.widget

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.andyha.coreextension.getDrawableResourceByName
import com.andyha.coreextension.getStringResourceByName
import com.andyha.coreui.base.ui.widget.textview.BaseTextView
import com.andyha.coreutils.time.TimeFormatter
import com.andyha.featureweatherui.R

@BindingAdapter(value = ["lastUpdated"])
fun setLastUpdated(textView: BaseTextView, timestamp: Long) {
    textView.text = textView.context.getString(
        R.string.last_updated,
        TimeFormatter.timestampToHourMinute(textView.context, timestamp)
    )
}

@BindingAdapter(value = ["weekDay"])
fun setWeekDay(textView: BaseTextView, timestamp: Long) {
    textView.text = TimeFormatter.timestampToWeekDay(textView.context, timestamp)
}

@BindingAdapter(value = ["hour"])
fun setHour(textView: TextView, timestamp: Long) {
    textView.text = TimeFormatter.timestampToHourMinute(textView.context, timestamp)
}

@BindingAdapter(value = ["date"])
fun setDate(textView: TextView, timestamp: Long) {
    textView.text = TimeFormatter.timestampToDate(textView.context, timestamp)
}

@BindingAdapter(value = ["airQualityImage"])
fun setAirQualityImage(imageView: ImageView, quality: Int) {
    imageView.context.getDrawableResourceByName("ic_aqi_$quality")?.let {
        imageView.setImageResource(it)
    }
}

@BindingAdapter(value = ["airQualityDescription"])
fun setAirQualityImage(textView: BaseTextView, quality: Int?) {
    quality?.let { quality ->
        textView.context.getStringResourceByName("air_quality_$quality")?.let {
            textView.setCustomText(it)
        }
    } ?: run { textView.setText(R.string.air_quality_na) }
}