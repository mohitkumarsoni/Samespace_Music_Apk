package com.example.samespacemusicapk.ui_layers.sheets.fragment_ui.top_tracks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.samespacemusicapk.common.SongResource
import com.example.samespacemusicapk.databinding.FragmentTopTracksBinding
import com.example.samespacemusicapk.ui_layers.adapter.SongAdapter
import com.example.samespacemusicapk.ui_layers.model.Data
import com.example.samespacemusicapk.ui_layers.sheets.activity_ui.main_activity.MainActivity
import com.example.samespacemusicapk.ui_layers.sheets.activity_ui.main_activity.MainActivityViewModel

class TopTracksFragment : Fragment() {
    private var _binding: FragmentTopTracksBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var songAdapter: SongAdapter

    lateinit var songArrList : ArrayList<Data>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopTracksBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()

        viewModel.songList.observe(viewLifecycleOwner) {
            when (it) {
                is SongResource.Success -> {
                    hidePB()
                    songArrList = ArrayList()
                    it.data?.data.let {songList ->
                        songList?.forEach {song->
                            if (song.top_track == true){
                                songArrList.add(song)
                            }
                        }
                        songAdapter.differ.submitList(songArrList)
                    }
                }

                is SongResource.Loading -> {showPB()}
                is SongResource.Error -> {
                    hidePB()
                    Log.d("TAG", it.message.toString())
                }
                else -> {hidePB()}
            }
        }


    }

    private fun setupRV() {
        songAdapter = SongAdapter(requireContext())
        binding.topTracksRv.apply {
            adapter = songAdapter
        }
    }

    private fun showPB() {
        binding.progressBar.isVisible = true
    }

    private fun hidePB() {
        binding.progressBar.isVisible = false
    }

}