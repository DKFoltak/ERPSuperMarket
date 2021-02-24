package edu.gustavo.erpsupermarket.viewModel

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.gustavo.erpsupermarket.R
import edu.gustavo.erpsupermarket.Services
import edu.gustavo.erpsupermarket.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(var application: Application,
                    var store: Store? = null,
                    var product: Product? = null,
                    var recipe: Recipe? = null
) : ViewModel() {
    private var services : Services = Services(application)

    fun <T> liveData(service: (Services) -> Flow<MutableList<T>>) : LiveData<MutableList<T>> {
        return service(services).asLiveData()
    }

    fun executeService(service: suspend CoroutineScope.(services: Services) -> Unit) {
        viewModelScope.launch { service }
    }

    fun selectStoreById(id : Long) {
        store = services.getStoreById(id)
    }


    fun upsertStore(context: Context, id: Long) {
        val editView = LayoutInflater.from(context).inflate(R.layout.store_name_input, null)
        val storeName = editView.findViewById<View>(R.id.store_edit_text) as EditText
        AlertDialog.Builder(context)
            .setMessage(R.string.changeStoreName)
            .setTitle(R.string.editingStore)
            .setView(editView)
            .setPositiveButton(R.string.okButton) { _, _ ->
                executeService {
                    services.upsertStore(
                        Store(
                            id,
                            storeName.text.toString()
                        )
                    )
                }
            }
            .create()
            .show()
    }
}