package com.example.spaceapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spaceapp.databinding.FragmentFirstBinding
import com.example.spaceapp.spacelist.SpaceAdapter
import com.example.spaceapp.spacelist.SpaceListViewModel
import com.example.spaceapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val viewModel: SpaceListViewModel by viewModels()
    private lateinit var adapter: SpaceAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObservers()

        /**
         * passing data between fragments
         * 1. shared viewmodel
         * 2. interfaces
         * 3. bundle class
         */

        adapter = SpaceAdapter(SpaceAdapter.OnClickListener{
            Toast.makeText(activity, "${it.id}", Toast.LENGTH_LONG).show()
            val bundle = Bundle()
            bundle.putInt("ID", it.id)

            // mode of navigation - where and what data

            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)

        })

        /**
         * pass id to second fragment on navigate
         * recieve the id , make the other api call
         */

        _binding?.recyclerView?.adapter = adapter
        _binding?.recyclerView?.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun startObservers(){
        viewModel.repository.observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.SUCCESS ->{
                    //pass data to recyclerview
                    // Log.i("Data", it.data?.get(0)?.name.toString())
                    Log.i("Data", ""+ (it.data))
                    adapter.submitList(it.data)
                }
                Resource.Status.ERROR ->{
                    Log.i("Error", it.message.toString())
                }
                Resource.Status.LOADING ->{
                    // Progress dialog
                }
            }
        }
    }
}