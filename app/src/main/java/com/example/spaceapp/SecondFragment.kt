package com.example.spaceapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.spaceapp.data.entities.ResultSpaceItem
import com.example.spaceapp.databinding.FragmentSecondBinding
import com.example.spaceapp.spacedetails.SpaceDetailVM
import com.example.spaceapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val viewModel: SpaceDetailVM by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * handle incoming data
         * safe calls ?
         * elvis ?:
         * let
         * null
         */

        arguments?.getInt("ID").let{
            // will be executed if not null
            Log.i("ID", ""+arguments?.getInt("ID"))

            viewModel.startDetailsCall(it!!)
        }

        startObserverDetails()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindDetailData(result: ResultSpaceItem){
        binding.textView.text = result.title
        binding.textView2.text = result.summary
        binding.textView3.text = result.newsSite
        binding.textView4.text = result.url

        Glide.with(binding.root)
            .load(result.imageUrl)
            .transform(CircleCrop())
            .into(binding.imageView)
    }

    fun startObserverDetails(){
        viewModel.space.observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.SUCCESS ->{
                    bindDetailData(it.data!!)

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