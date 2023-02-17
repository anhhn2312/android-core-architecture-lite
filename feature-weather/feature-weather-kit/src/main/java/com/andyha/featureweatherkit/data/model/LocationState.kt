package com.andyha.featureweatherkit.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.android.gms.common.api.ResolvableApiException


sealed class LocationState {
    object Undefined : LocationState()
    object PermissionDenied : LocationState()
    object PermissionDeniedForever : LocationState()
    object LocationGetFailed : LocationState()
    data class LocationSettingOff(val exception: ResolvableApiException) : LocationState()

    @Entity(tableName = LocationDetected.TABLE_NAME, primaryKeys = ["address", "region", "country"])
    data class LocationDetected(
        @ColumnInfo(name = "address")
        val address: String,

        @ColumnInfo(name = "region")
        val region: String,

        @ColumnInfo(name = "country")
        val country: String,

        @ColumnInfo(name = "lat")
        val lat: Double,

        @ColumnInfo(name = "lng")
        val lng: Double,

        @ColumnInfo(name = "temperature")
        val temperature: Int? = null,

        @ColumnInfo(name = "icon")
        val icon: String? = null,

        @ColumnInfo(name = "lastUpdated")
        val lastUpdated: Long,

        @ColumnInfo(name = "isSelected")
        val isSelected: Boolean = false
    ) : LocationState() {
        companion object {
            const val TABLE_NAME = "LocationDetected"

            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LocationDetected>() {
                override fun areItemsTheSame(
                    oldItem: LocationDetected,
                    newItem: LocationDetected
                ): Boolean = oldItem.address == newItem.address

                override fun areContentsTheSame(
                    oldItem: LocationDetected,
                    newItem: LocationDetected
                ): Boolean = oldItem == newItem
            }
        }
    }
}