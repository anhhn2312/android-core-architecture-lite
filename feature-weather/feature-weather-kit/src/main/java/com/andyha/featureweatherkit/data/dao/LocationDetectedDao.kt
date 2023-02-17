package com.andyha.featureweatherkit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andyha.coredata.base.BaseDao
import com.andyha.featureweatherkit.data.model.LocationState.LocationDetected
import kotlinx.coroutines.flow.Flow


@Dao
abstract class LocationDetectedDao : BaseDao<LocationDetected>() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertLocation(obj: LocationDetected): Long

    @Query("SELECT * FROM ${LocationDetected.TABLE_NAME} WHERE isSelected=1 LIMIT 1")
    abstract fun getSelectedLocation(): Flow<List<LocationDetected>>

    @Query("SELECT * FROM ${LocationDetected.TABLE_NAME} ORDER BY lastUpdated DESC")
    abstract fun getLocations(): Flow<List<LocationDetected>>

    @Query("UPDATE ${LocationDetected.TABLE_NAME} SET isSelected=1 WHERE address = :selectedLocation")
    abstract fun setSelected(selectedLocation: String): Int

    @Query("UPDATE ${LocationDetected.TABLE_NAME} SET isSelected=0 WHERE address <> :selectedLocation")
    abstract fun setUnselected(selectedLocation: String): Int
}