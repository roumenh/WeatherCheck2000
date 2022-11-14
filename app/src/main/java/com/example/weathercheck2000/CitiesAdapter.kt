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


class CitiesAdapter (val clickListener: CitiesListener) :
    ListAdapter<Cities, CitiesAdapter.CitiesViewHolder>(DiffCallback){

    // Viewholder will allow to access views created from layout file in code
    class CitiesViewHolder(private var binding: CityItemBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(clickListener: CitiesListener, cities: Cities, temperature : String){
                    binding.cities = cities  // this won't work without the <data> tag in city_item.xaml
                    binding.clickListener = clickListener // without this, the click listener below and defined in city_item.xaml wont work
                    binding.cityTemperatureTextView.text = temperature
                    // ^^ TODO need to align this - how to connect Cities class with Forecast class?
                    binding.executePendingBindings()  // not sure what this is good for
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
        /*
        viewHolder.itemView.setOnClickListener{
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
            Log.d("Cities Adapter", "on click")
        }
        */  // COMMENTING THE ONLICK LISTENEE OUT, WE WILL USE NOW  CLICK LISTENER IN THE CLASS

        return viewHolder
    }

    //override the onBindViewHolder() to bind the view at the specified position
    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val item = getItem(position)
        var temperature = "(loading...)"
        CoroutineScope(Dispatchers.Main).launch {
            // TODO : Not ideal! Should be done somehow better
            // TODO: This is probably not correct, need to improve, but don't know how yet
            try {
                val result = WeatherApi.retrofitService.getCurrentWeather(item.lat,item.lon)
                temperature = result.currentWeather.temperature.toString()
                Log.d("RetrofitCoroutine", temperature)
            } catch (e: Exception) {
                temperature = "Error : ${e.message}"
            }
            holder.bind(clickListener, getItem(position), "$temperature °C")
        }


    }

    // lets try to setup a onclick listener first...
    class CitiesListener(val clickListener: (cities: Cities) -> Unit) {
        fun onClick(cities: Cities) = clickListener(cities)
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