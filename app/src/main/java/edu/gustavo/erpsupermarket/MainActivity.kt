package edu.gustavo.erpsupermarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import edu.gustavo.erpsupermarket.viewModel.MainViewModel
import edu.gustavo.erpsupermarket.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var navController : NavController
    val mainViewModel : MainViewModel by viewModels{ MainViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment())
                    .commitNow()
        }
        navController = Navigation.findNavController(
            this,
            R.id.navigation_graph
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_stores -> {
            navController.navigate(R.id.action_mainFragment_to_storeListFragment)
            true
        }
        R.id.action_products -> {
            navController.navigate(R.id.action_mainFragment_to_productListFragment)
            true
        }
        R.id.action_recipes -> {
            navController.navigate(R.id.action_mainFragment_to_recipeListFragment)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}