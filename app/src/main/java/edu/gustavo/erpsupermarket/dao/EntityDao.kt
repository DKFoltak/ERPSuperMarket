package edu.gustavo.erpsupermarket.dao

import androidx.room.*
import edu.gustavo.erpsupermarket.entities.Ingredient

@Dao
interface EntityDao<in T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: T)

    @Update
    suspend fun update(entity: T)

    @Delete
    suspend fun delete(entity: T)
}