package com.athar.android.weatherapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.athar.android.domain.DetailWeather
import com.athar.android.domain.Lists
import com.athar.android.domain.Location
import com.athar.android.weatherapp.databinding.ForecastListItemBinding
import com.athar.android.weatherapp.utils.BindingAdapters
import com.athar.android.weatherapp.utils.convertDate
import com.athar.android.weatherapp.utils.convertTime
import timber.log.Timber

class ForecastAdapter:RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {
    var kelvinToCelsius : Boolean = false
    private val callback =object : DiffUtil.ItemCallback<Lists>() {
        override fun areItemsTheSame(oldItem: Lists, newItem: Lists): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: Lists, newItem: Lists): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ForecastListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val detailWeather = differ.currentList[position]
        holder.bind(detailWeather)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ForecastViewHolder(private val binding:ForecastListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(detailWeather: Lists) {
            Timber.d(detailWeather.toString())
            BindingAdapters.toCelsius(binding.textViewTemp,detailWeather.main.temp, kelvinToCelsius)
            binding.textViewTime.text = detailWeather.dt_txt.convertTime()
        }
    }

    private var onItemClickListener :((DetailWeather)->Unit)?=null

    fun setOnItemClickListener(listener : (DetailWeather)->Unit){
        onItemClickListener = listener
    }
}