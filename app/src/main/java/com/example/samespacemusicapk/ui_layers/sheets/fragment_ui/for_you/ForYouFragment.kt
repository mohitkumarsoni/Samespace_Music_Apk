package com.example.samespacemusicapk.ui_layers.sheets.fragment_ui.for_you

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.samespacemusicapk.R
import com.example.samespacemusicapk.common.SongResource
import com.example.samespacemusicapk.databinding.FragmentForYouBinding
import com.example.samespacemusicapk.ui_layers.adapter.SongAdapter
import com.example.samespacemusicapk.ui_layers.sheets.activity_ui.main_activity.MainActivity
import com.example.samespacemusicapk.ui_layers.sheets.activity_ui.main_activity.MainActivityViewModel

class ForYouFragment : Fragment() {
    private var _binding : FragmentForYouBinding ?= null
    private val binding get() = _binding!!
    private lateinit var viewModel : MainActivityViewModel
    private lateinit var songAdapter: SongAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentForYouBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()

        viewModel.songList.observe(viewLifecycleOwner){
            when(it){
                is SongResource.Success -> {
                    hidePB()
                    it.data?.let {responseData ->
                        songAdapter.differ.submitList(responseData.data)
                    }
                }
                is SongResource.Error -> {
                    hidePB()
                    it.message?.let {errorMessage ->
                        Log.d("TAG", errorMessage)
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
                is SongResource.Loading -> {showPB()}
                else -> {hidePB()}
            }
        }

    }

    private fun setupRV(){
        songAdapter = SongAdapter(requireContext())
        binding.forYouRv.apply {
            adapter = songAdapter
        }
    }

    private fun showPB(){
        binding.progressBar.isVisible = true
    }

    private fun hidePB(){
        binding.progressBar.isVisible = false
    }

}