package edu.gustavo.erpsupermarket.store

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.gustavo.erpsupermarket.R
import edu.gustavo.erpsupermarket.entities.Store
import edu.gustavo.erpsupermarket.viewModel.MainViewModel

class StoreAdapter(val viewModel: MainViewModel) :
    ListAdapter<Store, StoreAdapter.ViewHolder>(StoreComparator) {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id = view.findViewById<View>(R.id.store_id_value) as TextView
        var name = view.findViewById<View>(R.id.store_name_value) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.store_list_item, parent, false)
        val holder = ViewHolder(view)
        view.setOnClickListener {
            viewModel.selectStoreById(holder.id.text.toString().toLong())
            it.findNavController().navigate(R.id.action_storeListFragment_to_productListFragment)
        }
        view.setOnLongClickListener {
            Snackbar.make(view, R.string.deleteStore, Snackbar.LENGTH_LONG)
                .setAction(R.string.accionDeleteStore) {
                    AlertDialog.Builder(it.context)
                        .setMessage(R.string.deleteWarning)
                        .setTitle(R.string.deleteWarningTitle)
                        .setPositiveButton(R.string.okButton) { _, _ ->
                            viewModel.selectStoreById(holder.id.text.toString().toLong())
                            viewModel.executeService { services -> services.deleteStore(viewModel.store!!) }
                        }
                        .create()
                        .show()
                }
                .setAction(R.string.accionDeleteStore) {
                    viewModel.upsertStore(it.context, holder.id.text.toString().toLong())
                }
                .show()
            true
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = getItem(position)
        holder.name.text = store.name
    }

    companion object StoreComparator : DiffUtil.ItemCallback<Store>() {
        override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean {
            return oldItem.id == newItem.id
        }
    }
}