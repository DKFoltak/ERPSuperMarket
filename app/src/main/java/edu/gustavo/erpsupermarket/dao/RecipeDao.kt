package edu.gustavo.erpsupermarket.dao

import androidx.room.Dao
import androidx.room.Query
import edu.gustavo.erpsupermarket.entities.Product
import edu.gustavo.erpsupermarket.entities.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao : EntityDao<Recipe> {
    @Query("SELECT * FROM ${Recipe.TABLE_NAME} ORDER BY ${Recipe.COLUMN_ID}")
    fun getRecipes(): Flow<MutableList<Recipe>>

    @Query("SELECT * FROM ${Recipe.TABLE_NAME} WHERE ${Product.COLUMN_ID} = :product ORDER BY ${Recipe.COLUMN_ID}")
    fun getRecipesByProduct(product: Long): Flow<MutableList<Recipe>>
}