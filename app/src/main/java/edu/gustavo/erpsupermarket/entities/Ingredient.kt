package edu.gustavo.erpsupermarket.entities

import androidx.room.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import java.math.BigDecimal

@Entity(tableName = Ingredient.TABLE_NAME,
    primaryKeys = arrayOf(Ingredient.COLUMN_RECIPE, Ingredient.COLUMN_PRODUCT),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Recipe::class,
            parentColumns = arrayOf(Recipe.COLUMN_ID),
            childColumns = arrayOf(Ingredient.COLUMN_RECIPE),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = arrayOf(Product.COLUMN_ID),
            childColumns = arrayOf(Ingredient.COLUMN_PRODUCT),
            onDelete = ForeignKey.CASCADE
        )
    ),
    indices = arrayOf(Index(Ingredient.COLUMN_RECIPE), Index(Ingredient.COLUMN_PRODUCT))
)
data class Ingredient (
    @ColumnInfo(name = COLUMN_RECIPE)
    @NotNull
    var recipeId: Long = 0,
    @ColumnInfo(name = COLUMN_PRODUCT)
    @NotNull
    var productId: Long = 0,
    @ColumnInfo(name = COLUMN_QUANTITY)
    @NotNull
    var quantity: Long = 0,
    @ColumnInfo(name = COLUMN_UNIT)
    @NotNull
    var unit: String = ""
) : Serializable {
    override fun toString(): String {
        return "Ingredient {recipe: $recipeId, product: $productId, quantity: $quantity, unit: $unit}"
    }

    companion object {
        const val TABLE_NAME = "ingredients"
        const val COLUMN_RECIPE = "recipe"
        const val COLUMN_PRODUCT = "product"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_UNIT = "unit"
    }
}

class RecipeWithProducts : Serializable {
    @Relation(parentColumn = Ingredient.COLUMN_RECIPE, entityColumn = Recipe.COLUMN_ID, entity = Recipe::class)
    lateinit var recipe : Recipe
    @Relation(parentColumn = Ingredient.COLUMN_PRODUCT, entityColumn = Product.COLUMN_ID, entity = Product::class)
    lateinit var products : MutableList<Product>

    override fun toString(): String {
        var recipeProducts = ""
        for(product in products) {
            if (recipeProducts != "")
                recipeProducts += ";"
            recipeProducts += product.toString()
        }
        return "ProductWithStock {Recipe: $recipe, Products: $recipeProducts}"
    }
}

class ProductWithRecipes : Serializable {
    @Relation(parentColumn = Ingredient.COLUMN_PRODUCT, entityColumn = Product.COLUMN_ID, entity = Product::class)
    lateinit var product : Product
    @Relation(parentColumn = Ingredient.COLUMN_RECIPE, entityColumn = Recipe.COLUMN_ID, entity = Recipe::class)
    lateinit var recipes : MutableList<Recipe>

    override fun toString(): String {
        var productRecipes = ""
        for(recipe in recipes) {
            if (productRecipes != "")
                productRecipes += ";"
            productRecipes += recipe.toString()
        }
        return "ProductWithStock {Product: $product, Recipes: $productRecipes}"
    }
}