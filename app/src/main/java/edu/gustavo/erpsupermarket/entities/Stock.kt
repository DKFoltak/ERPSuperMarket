package edu.gustavo.erpsupermarket.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = Stock.TABLE_NAME,
    primaryKeys = arrayOf(Stock.COLUMN_STORE, Stock.COLUMN_PRODUCT),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Store::class,
            parentColumns = arrayOf(Store.COLUMN_ID),
            childColumns = arrayOf(Stock.COLUMN_STORE),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = arrayOf(Product.COLUMN_ID),
            childColumns = arrayOf(Stock.COLUMN_PRODUCT),
            onDelete = CASCADE
        )
    ),
    indices = arrayOf(Index(value = arrayOf(Stock.COLUMN_STORE), unique = false))
)
data class Stock (
    @ColumnInfo(name = COLUMN_STORE)
    @NotNull
    var store: Long = 0,
    @ColumnInfo(name = COLUMN_PRODUCT)
    @NotNull
    var product: Long = 0,
    @ColumnInfo(name = COLUMN_QUANTITY)
    @NotNull
    var quantity: Long = 0,
    @ColumnInfo(name = COLUMN_UNIT)
    @NotNull
    var unit: String = ""
) : Serializable {
    override fun toString(): String {
        return "Stock {store: $store, product: $product, quantity: $quantity, unit: $unit}"
    }

    companion object {
        const val TABLE_NAME = "stocks"
        const val COLUMN_STORE = "store_id"
        const val COLUMN_PRODUCT = "product_id"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_UNIT = "unit"
    }
}

class StockProduct : Serializable {
    @Embedded
    lateinit var stock: Stock
    @Relation(parentColumn = Stock.COLUMN_PRODUCT, entityColumn = Product.COLUMN_ID, entity = Product::class)
    lateinit var products : MutableList<Product>

    override fun toString(): String {
        var stockProducts = ""
        for(product in products)
        {
            if (stockProducts != "")
                stockProducts += ";"
            stockProducts += product.toString()
        }
        return "StockProduct {Stock: $stock, Products: $stockProducts}"
    }
}

class StockProductWithStore : Serializable
{
    @Embedded
    lateinit var stock: Stock
    @Relation(parentColumn = Stock.COLUMN_PRODUCT, entityColumn = Product.COLUMN_ID, entity = Product::class)
    lateinit var product : Product
    @Relation(parentColumn = Stock.COLUMN_STORE, entityColumn = Store.COLUMN_ID, entity = Store::class)
    lateinit var store : Store

    override fun toString(): String {
        return "ProductWithStores {Stock: $stock, Product: $product, Store: $store}"
    }
}