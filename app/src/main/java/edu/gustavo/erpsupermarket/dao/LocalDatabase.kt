package edu.gustavo.erpsupermarket.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.gustavo.erpsupermarket.entities.*

@Database(entities = arrayOf(Ingredient::class, Product::class, Recipe::class,
    Stock::class, Store::class),
    version = LocalDatabase.VERSION, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun ingredientDao(): IngredientDao
    abstract fun productDao(): ProductDao
    abstract fun recipeDao(): RecipeDao
    abstract fun stockDao(): StockDao
    abstract fun storeDao(): StoreDao

    companion object {
        const val DB_NAME = "localDatabase"
        const val VERSION = 1

        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    LocalDatabase.DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}