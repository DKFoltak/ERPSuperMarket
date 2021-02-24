package edu.gustavo.erpsupermarket.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = Recipe.TABLE_NAME)
data class Recipe (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    @NotNull
    var id: Long = 0,
    @ColumnInfo(name = COLUMN_NAME)
    @NotNull
    var name: String = "",
    @ColumnInfo(name = COLUMN_SUMMARY)
    @NotNull
    var summary: String = "",
    @ColumnInfo(name = COLUMN_DESCRIPTION)
    @NotNull
    var description: String = ""
) : Serializable {
    override fun toString(): String {
        return "Recipe {id: $id, name: $name, summary: $summary, description: $description}"
    }

    companion object {
        const val TABLE_NAME = "recipes"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_SUMMARY = "summary"
        const val COLUMN_DESCRIPTION = "description"
    }
}