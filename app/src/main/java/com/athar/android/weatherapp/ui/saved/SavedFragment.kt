package com.athar.android.weatherapp.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.athar.android.weatherapp.R
import com.athar.android.weatherapp.databinding.FragmentSavedBinding
import com.athar.android.weatherapp.ui.MainActivity
import com.athar.android.weatherapp.utils.hide
import com.athar.android.weatherapp.utils.show
import com.athar.android.weatherapp.vm.LocationViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SavedFragment : Fragment() {
    private lateinit var fragmentSavedBinding: FragmentSavedBinding
    private lateinit var viewModel: LocationViewModel
    private lateinit var locationAdapter: SavedLocationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSavedBinding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        initRecyclerView()
        setupTouchHelper(view)
        getLocation()
    }

    private fun getLocation() {
        viewModel.getLocation().observe(viewLifecycleOwner, { response ->
            lifecycleScope.launch {
                viewModel.progress.value = true
                delay(100)
                if (response.isNotEmpty()) {
                    locationAdapter.differ.submitList(response)
                    fragmentSavedBinding.textViewNoBookmarks.hide()
                    fragmentSavedBinding.recyclerView.show()
                } else {
                    fragmentSavedBinding.textViewNoBookmarks.show()
                    fragmentSavedBinding.recyclerView.hide()
                }
                viewModel.progress.value = false
            }
        })
    }

    private fun setupTouchHelper(view: View) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val location = locationAdapter.differ.currentList[position]
                viewModel.deleteLocation(location)
                Snackbar.make(view, "Deleted Successfully", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            viewModel.saveLocation(location)
                        }
                        show()
                    }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(fragmentSavedBinding.recyclerView)
        }
    }

    private fun initRecyclerView() {
        locationAdapter = (activity as MainActivity).savedAdapter
        locationAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_location", it)
            }
            findNavController().navigate(
                R.id.action_savedFragment_to_detailFragment,
                bundle
            )
        }
        fragmentSavedBinding.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = locationAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}