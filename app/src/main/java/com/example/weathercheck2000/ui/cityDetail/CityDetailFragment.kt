package com.example.weathercheck2000.ui.cityDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.weathercheck2000.WeatherCheckApplication
import com.example.weathercheck2000.viewModels.CitiesViewModel
import androidx.fragment.app.Fragment
import com.example.weathercheck2000.databinding.FragmentCityDetailBinding

class CityDetailFragment : Fragment() {

    // attach shared View Model
    private val viewModel: CitiesViewModel by viewModels {
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.cityDetailFragment = this@CityDetailFragment

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}