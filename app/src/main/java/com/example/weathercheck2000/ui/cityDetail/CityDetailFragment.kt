package com.example.weathercheck2000.ui.cityDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.weathercheck2000.WeatherCheckApplication
import com.example.weathercheck2000.databinding.FragmentCityDetailBinding
import com.example.weathercheck2000.network.WeatherApi
import com.example.weathercheck2000.viewModels.CitiesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityDetailFragment : Fragment() {

    // attach shared View Model
    private val viewModel: CitiesViewModel by activityViewModels {
        CitiesViewModel.CitiesViewModelFactory((activity?.application as WeatherCheckApplication).repository)
    }

    private var _binding: FragmentCityDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  THIS IS NECESSARY IN ORDER TO CONNECT THE DOTS BETWEEN FUNCTIONS FROM LAYOUT
        //  YEAH
        Log.d("binding",binding.toString())
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.cityDetailFragment = this@CityDetailFragment  // it kinda works/ does not work even without this




        var temperatureMin = "(loading...)"
        var temperatureMax = "(loading...)"
        var temperatureNow = "(loading...)"
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // fetch forecast
                val forecastResult = WeatherApi.retrofitService.getForecast(viewModel.city.value!!.lat,viewModel.city.value!!.lon)
                temperatureMin = forecastResult.daily.temperature2mMin.first().toString()
                temperatureMax = forecastResult.daily.temperature2mMax.first().toString()
                // fetch current weather
                val actualWeather = WeatherApi.retrofitService.getCurrentWeather(viewModel.city.value!!.lat,viewModel.city.value!!.lon)
                temperatureNow = actualWeather.currentWeather.temperature.toString()
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
            binding.temperatureMinValue.text        = "$temperatureMin °C"
            binding.temperatureMaxValue.text        = "$temperatureMax °C"
            binding.temperatureCurrentValue.text    = "$temperatureNow °C"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}