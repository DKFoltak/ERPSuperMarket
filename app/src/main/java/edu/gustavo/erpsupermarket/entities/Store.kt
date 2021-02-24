package edu.gustavo.erpsupermarket.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = Store.TABLE_NAME)
data class Store (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    @NotNull
    var id: Long = 0,
    @ColumnInfo(name = COLUMN_NAME)
    @NotNull
    var name: String = ""
    ) : Serializable {
    override fun toString(): String {
        return "Store {id: $id, name: $name}"
    }

    companion object {
        const val TABLE_NAME = "stores"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }
}