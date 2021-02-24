package edu.gustavo.erpsupermarket.dao

import androidx.room.Dao
import androidx.room.Query
import edu.gustavo.erpsupermarket.entities.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao : EntityDao<Store> {
    @Query("SELECT * FROM ${Store.TABLE_NAME} ORDER BY ${Store.COLUMN_ID}")
    fun getStores(): Flow<MutableList<Store>>

    @Query("SELECT * FROM ${Store.TABLE_NAME} WHERE ${Store.COLUMN_ID} = :id")
    fun getStoreById(id: Long) : Store
}