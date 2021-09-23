package com.athar.android.weatherapp.ui.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.athar.android.domain.Location
import com.athar.android.weatherapp.databinding.LocationListItemBinding

class SavedLocationAdapter: RecyclerView.Adapter<SavedLocationAdapter.SavedLocationViewHolder>() {
    private val callback =object : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedLocationViewHolder {
        val binding = LocationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedLocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedLocationViewHolder, position: Int) {
        val location = differ.currentList[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class SavedLocationViewHolder(private val binding: LocationListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(location: Location) {
            binding.textViewName.text = location.name
            binding.textViewLatLng.text = location.latitude.toString().plus(":").plus(location.longitude.toString())
            binding.root.setOnClickListener{
                onItemClickListener?.let {
                    it(location)
                }
            }
        }
    }

    private var onItemClickListener :((Location)->Unit)?=null

    fun setOnItemClickListener(listener : (Location)->Unit){
        onItemClickListener = listener
    }
}