package edu.gustavo.erpsupermarket.store

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import edu.gustavo.erpsupermarket.MainActivity
import edu.gustavo.erpsupermarket.R

class StoreListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_store_list, container, false)
        view.findViewById<FloatingActionButton>(R.id.floatingActionButton_store_add)
            .setOnClickListener { it ->
                Snackbar.make(it, R.string.newStore, Snackbar.LENGTH_LONG)
                    .setAction(R.string.createStore, {
                        (activity as MainActivity).mainViewModel.upsertStore(it.context, 0L)
                    }).show()
            }
        activity?.setTitle(R.string.stores)
        setHasOptionsMenu(true);
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById(R.id.recyclerView_store_list) as RecyclerView
        val mainViewModel = (activity as MainActivity).mainViewModel
        val adapter = StoreAdapter(mainViewModel)
        mainViewModel.liveData { services -> services.getStores() }
            .observe(viewLifecycleOwner, Observer { stores ->
                stores?.let { adapter.submitList(it) }
            })
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = GridLayoutManager(getContext(), 3)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

}