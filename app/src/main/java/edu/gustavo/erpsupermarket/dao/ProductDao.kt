package edu.gustavo.erpsupermarket.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import edu.gustavo.erpsupermarket.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao : EntityDao<Product> {
    @Query("SELECT * FROM ${Product.TABLE_NAME} ORDER BY ${Product.COLUMN_ID}")
    fun getProducts(): Flow<MutableList<Product>>

    @Transaction
    @Query("SELECT * FROM ${Product.TABLE_NAME} INNER JOIN ${Stock.TABLE_NAME} ON ${Product.COLUMN_ID}=${Stock.COLUMN_PRODUCT} WHERE ${Stock.COLUMN_STORE} = :store")
    fun getProductsByStore(store: Long): Flow<MutableList<Product>>

    @Transaction
    @Query("SELECT * FROM ${Stock.TABLE_NAME} WHERE ${Stock.COLUMN_PRODUCT} = :product AND ${Stock.COLUMN_STORE} = :store")
    fun getProductStock(product: Long, store: Long): Flow<StockProductWithStore>
}