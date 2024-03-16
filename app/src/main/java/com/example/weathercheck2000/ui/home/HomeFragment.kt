package com.example.weathercheck2000.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathercheck2000.CitiesAdapter
import com.example.weathercheck2000.WeatherCheckApplication
import com.example.weathercheck2000.databinding.FragmentHomeBinding
import com.example.weathercheck2000.viewModels.CitiesViewModel

class HomeFragment : Fragment() {

    private val viewModel: CitiesViewModel by activityViewModels {
        CitiesViewModel.CitiesViewModelFactory((activity?.application as WeatherCheckApplication).citiesRepository)
    }

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // setup the recyclerView & assign to its layout manager
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //assign the adapter property
        val citiesAdapter = CitiesAdapter(CitiesAdapter.CitiesListener { cities ->
            viewModel.setDetailCity(cities)
        //    findNavController().navigate(R.id.action_navigation_home_to_cityDetailFragment)
            // TODO add arguments? is it necessary?
            })
        recyclerView.adapter = citiesAdapter



        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        viewModel.allCity.observe(viewLifecycleOwner) { cities ->
            // Update the cached copy of the cities in the adapter.
            cities.let { citiesAdapter.submitList(it) }
        }

        binding.viewModel = viewModel       // this is necessary so we can use viewModel variables inside this fragment..
        binding.lifecycleOwner = this       // + THIS ALSO!!! :) :) or = lifecycleOwner  (like in dashboard fragment -WHY?)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}