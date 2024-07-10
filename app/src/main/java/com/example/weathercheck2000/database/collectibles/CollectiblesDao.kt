package com.example.weathercheck2000.database.collectibles

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectiblesDao{
    @Query("SELECT * FROM collectible")
    fun getAll(): Flow<MutableList<Collectible>>

    @Query("SELECT EXISTS(SELECT * FROM collectible WHERE code = :code)")
    fun collectibleExists(code: String): Boolean

    @Insert//(onConflict = OnConflictStrategy.IGNORE)
    fun insertCollectible(collectible: Collectible)

    @Delete
    fun deleteCollectible(collectible: Collectible)

}