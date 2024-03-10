package com.example.samespacemusicapk.ui_layers.sheets.activity_ui.main_activity

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.samespacemusicapk.R
import com.example.samespacemusicapk.databinding.ActivityMainBinding
import com.example.samespacemusicapk.mvvm.repository.SongRepository

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var viewModel : MainActivityViewModel
    lateinit var player:Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.navView.setupWithNavController(supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)!!.findNavController())
        player = ExoPlayer.Builder(this).build()

        val factory = MainActivityViewModelProvider(SongRepository()) ;
        viewModel = ViewModelProvider(this,factory)[MainActivityViewModel::class.java]

    }

}