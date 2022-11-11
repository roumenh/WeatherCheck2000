package com.example.weathercheck2000.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathercheck2000.CitiesAdapter
import com.example.weathercheck2000.WeatherCheckApplication
import com.example.weathercheck2000.databinding.FragmentHomeBinding
import com.example.weathercheck2000.viewModels.CitiesViewModel

class HomeFragment : Fragment() {

    private val viewModel: CitiesViewModel by viewModels {
        CitiesViewModel.CitiesViewModelFactory((activity?.application as WeatherCheckApplication).repository)
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
        //val homeViewModel =
       //     ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //_binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val root: View = binding.root

        /*
        val textView: TextView = binding.textHome

        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
         */
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

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        viewModel.allCities.observe(viewLifecycleOwner) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { citiesAdapter.submitList(it) }
        }

        binding.viewModel = viewModel       // this is necessary so we can use viewModel variables inside this fragment..
        binding.lifecycleOwner = this       // + THIS ALSO!!! :) :) 

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}