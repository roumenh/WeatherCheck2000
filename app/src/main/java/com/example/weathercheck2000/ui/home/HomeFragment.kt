package com.example.weathercheck2000.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathercheck2000.CitiesAdapter
import com.example.weathercheck2000.WeatherCheckApplication
import com.example.weathercheck2000.databinding.FragmentHomeBinding
import com.example.weathercheck2000.viewModels.CitiesViewModel
import com.example.weathercheck2000.viewModels.CitiesViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class HomeFragment : Fragment() {

    private val viewModel: CitiesViewModel by activityViewModels {
        CitiesViewModelFactory(
            (activity?.application as WeatherCheckApplication).database.citiesDao()
        )
    }

    private var _binding: FragmentHomeBinding? = null

    private lateinit var recyclerView: RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setup the recyclerView & assign to its layout manager
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //assign the adapter property
        val citiesAdapter = CitiesAdapter({}) // no action so far, but if will be, we will add here
        recyclerView.adapter = citiesAdapter
        //here I can build
        lifecycle.coroutineScope.launch{
            viewModel.getAllCities().collect {
                citiesAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}