package edu.gustavo.erpsupermarket.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import edu.gustavo.erpsupermarket.entities.Ingredient
import edu.gustavo.erpsupermarket.entities.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao : EntityDao<Ingredient> {
    @Transaction
    @Query("SELECT * FROM ${Ingredient.TABLE_NAME} ORDER BY ${Ingredient.COLUMN_PRODUCT}")
    fun getIngredients(): Flow<MutableList<Ingredient>>
    @Transaction
    @Query("SELECT * FROM ${Ingredient.TABLE_NAME} WHERE ${Ingredient.COLUMN_RECIPE} = :recipe ORDER BY ${Ingredient.COLUMN_PRODUCT}")
    fun getIngredientsByRecipe(recipe: Long): Flow<MutableList<Ingredient>>
}