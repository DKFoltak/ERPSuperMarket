package edu.gustavo.erpsupermarket

import android.app.Application
import androidx.annotation.WorkerThread
import edu.gustavo.erpsupermarket.dao.*
import edu.gustavo.erpsupermarket.entities.*
import kotlinx.coroutines.flow.Flow

class Services(val application: Application) {
    private val ingredientDao: IngredientDao = LocalDatabase.getInstance(application).ingredientDao()
    private val productDao: ProductDao = LocalDatabase.getInstance(application).productDao()
    private val recipeDao: RecipeDao = LocalDatabase.getInstance(application).recipeDao()
    private val stockDao: StockDao = LocalDatabase.getInstance(application).stockDao()
    private val storeDao: StoreDao = LocalDatabase.getInstance(application).storeDao()

    fun getStores() : Flow<MutableList<Store>> {
        return storeDao.getStores()
    }

    fun getStoreById(id: Long) : Store {
        return storeDao.getStoreById(id)
    }

    @WorkerThread
    suspend fun upsertStore(store: Store) {
        if (store.id != 0L)
            storeDao.update(store)
        else
            storeDao.insert(store)
    }

    @WorkerThread
    suspend fun deleteStore(store: Store) {
        storeDao.delete(store)
    }

    fun getProducts(store: Store?) : Flow<MutableList<Product>> {
        return if (store == null)
            productDao.getProducts()
        else
            productDao.getProductsByStore(store.id)
    }

    @WorkerThread
    suspend fun upsertProduct(product: Product) {
        if (product.id != 0L)
            productDao.update(product)
        else
            productDao.insert(product)
    }

    @WorkerThread
    suspend fun deleteProduct(product: Product) {
        productDao.delete(product)
    }

    @WorkerThread
    suspend fun upsertStock(stock: Stock) {
        if ((stock.store != 0L) && (stock.product != 0L))
            stockDao.update(stock)
        else
            stockDao.insert(stock)
    }

    @WorkerThread
    suspend fun deleteStock(stock: Stock) {
        stockDao.delete(stock)
    }

    fun getRecipes(product: Product?) : Flow<MutableList<Recipe>> {
        if (product == null)
            return recipeDao.getRecipes()
        else
            return recipeDao.getRecipesByProduct(product.id)
    }

    fun getIngredients(recipe: Recipe?) : Flow<MutableList<Ingredient>> {
        return if (recipe == null)
            ingredientDao.getIngredients()
        else
            ingredientDao.getIngredientsByRecipe(recipe.id)
    }

    @WorkerThread
    suspend fun upsertRecipe(recipe: Recipe, ingredients: MutableList<Ingredient>) {
        if (recipe.id != 0L)
            recipeDao.update(recipe)
        else
            recipeDao.insert(recipe)

        for(ingredient in ingredients) {
            if (ingredient.recipeId != 0L)
                ingredientDao.update(ingredient)
            else {
                ingredient.recipeId = recipe.id
                ingredientDao.insert(ingredient)
            }
        }
    }

    @WorkerThread
    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.delete(recipe)
    }
}