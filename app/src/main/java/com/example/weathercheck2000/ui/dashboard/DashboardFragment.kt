package com.example.weathercheck2000.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weathercheck2000.R
import com.example.weathercheck2000.WeatherCheckApplication
import com.example.weathercheck2000.databinding.FragmentDashboardBinding
import com.example.weathercheck2000.viewModels.CitiesViewModel

class DashboardFragment : Fragment() {


    private val viewModel: CitiesViewModel by activityViewModels {
        CitiesViewModel.CitiesViewModelFactory((activity?.application as WeatherCheckApplication).repository)
    }

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  THIS IS NECESSARY IN ORDER TO CONNECT THE DOTS BETWEEN FUNCTIONS FROM LAYOUT
        //  YEAH
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.dashboardFragment = this@DashboardFragment

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //-----------------

    fun clickInsertNewCity(){
        Log.d("InsertNewCity","yes0")
        if (viewModel.insertNewCity()){
            Toast.makeText(this.context,"Added",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_home)
        }else{
            Toast.makeText(this.context, "Some fields are missing",Toast.LENGTH_SHORT).show()
        }

    }
}