package edu.gustavo.erpsupermarket.entities

import androidx.room.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = Product.TABLE_NAME)
data class Product (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    @NotNull
    var id: Long = 0,
    @ColumnInfo(name = COLUMN_NAME)
    @NotNull
    var name: String = "",
    @ColumnInfo(name = COLUMN_BARCODE)
    @NotNull
    var barcode: String = "",
    @ColumnInfo(name = COLUMN_BRAND)
    @NotNull
    var brand: String = ""
) : Serializable {
    override fun toString(): String {
        return "Product {id: $id, name: $name, barcode: $barcode, brand: $brand}"
    }

    companion object {
        const val TABLE_NAME = "products"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_BARCODE = "barcode"
        const val COLUMN_BRAND = "brand"
    }
}

class ProductWithStocks : Serializable {
    @Embedded(prefix = Product.TABLE_NAME)
    lateinit var product : Product
    @Relation(parentColumn = Product.COLUMN_ID, entityColumn = Store.COLUMN_ID, entity = Store::class)
    lateinit var stocks : List<Stock>

    override fun toString(): String {
        var productStock = ""
        for(stock in stocks) {
            if (productStock != "")
                productStock += ";"
            productStock += stock.toString()
        }
        return "ProductWithStocks {Product: $product, Stocks: $productStock}"
    }
}