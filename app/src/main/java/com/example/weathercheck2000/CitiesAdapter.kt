package com.example.weathercheck2000

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathercheck2000.database.cities.Cities
import com.example.weathercheck2000.databinding.CityItemBinding
import com.example.weathercheck2000.network.WeatherApi
import com.example.weathercheck2000.viewModels.CitiesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CitiesAdapter (
    private val onItemClicked: (Cities) -> Unit
) :
    ListAdapter<Cities, CitiesAdapter.CitiesViewHolder>(DiffCallback){

    // Viewholder will allow to access views created from layout file in code
    class CitiesViewHolder(private var binding: CityItemBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(cities: Cities, temperature : String){
                    binding.cityNameTextView.text = cities.name
                    binding.cityLatitudeTextView.text = cities.lat
                    binding.cityLongitudeTextView.text = cities.lon
                    binding.cityTemperatureTextView.text = temperature
                }
            }

    // inflate the layout....
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val viewHolder = CitiesViewHolder(
            CityItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        // onclick listener
        viewHolder.itemView.setOnClickListener{
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
            Log.d("Cities Adapter", "on click")
        }
        return viewHolder
    }

    //override the onBindViewHolder() to bind the view at the specified position
    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val item = getItem(position)
        var temperature = "(loading...)"
        CoroutineScope(Dispatchers.Main).launch {
            // TODO : Not ideal! Should be done somehow better
            // TODO: This is probably not correct, need to improve
            try {
                val result = WeatherApi.retrofitService.getForecast(item.lat,item.lon)
                temperature = result.daily.temperature2mMin.first().toString()
                Log.d("RetrofitCoroutine", temperature)
            } catch (e: Exception) {
                temperature = "Error : ${e.message}"
            }
            holder.bind(getItem(position), temperature)
        }


    }

    /*
    DiffCallBack is an object that helps the ListAdapter determine which items in the new
    and old lists are different when updating the list.
    */
    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<Cities>(){

            override fun areItemsTheSame(oldItem: Cities, newItem: Cities): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cities, newItem: Cities): Boolean {
                return oldItem == newItem
            }
        }
    }
}