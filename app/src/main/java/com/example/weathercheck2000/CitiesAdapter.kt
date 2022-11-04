package com.example.weathercheck2000

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathercheck2000.database.cities.Cities
import com.example.weathercheck2000.databinding.CityItemBinding

class CitiesAdapter (private val onItemClicked: (Cities) -> Unit) :
    ListAdapter<Cities, CitiesAdapter.CitiesViewHolder>(DiffCallback){

    // Viewholder will allow to access views created from layout file in code
    class CitiesViewHolder(private var binding: CityItemBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(cities: Cities){
                    binding.cityNameTextView.text = cities.name
                    binding.cityLatitudeTextView.text = cities.lat
                    binding.cityLongitudeTextView.text = cities.lon
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
        }
        return viewHolder
    }

    //override the onBindViewHolder() to bind the view at the specified position
    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(getItem(position))
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