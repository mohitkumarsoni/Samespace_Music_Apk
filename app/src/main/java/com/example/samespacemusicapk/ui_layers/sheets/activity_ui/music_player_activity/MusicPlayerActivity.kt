package com.example.samespacemusicapk.ui_layers.sheets.activity_ui.music_player_activity

import android.content.ComponentName
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionToken
import coil.load
import com.example.samespacemusicapk.R
import com.example.samespacemusicapk.common.IMAGE_URL
import com.example.samespacemusicapk.common.SongResource
import com.example.samespacemusicapk.databinding.ActivityMusicPlayerBinding
import com.example.samespacemusicapk.ui_layers.model.Data
import com.example.samespacemusicapk.ui_layers.sheets.activity_ui.main_activity.MainActivity
import com.example.samespacemusicapk.ui_layers.sheets.activity_ui.main_activity.MainActivityViewModel
import com.google.common.util.concurrent.MoreExecutors
import java.net.URL


class MusicPlayerActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMusicPlayerBinding.inflate(layoutInflater)
    }
    private lateinit var player : Player
    var duration:Int = 0
    private var selectedSong : Data?=null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        player = ExoPlayer.Builder(this@MusicPlayerActivity).build()

        selectedSong = intent.getSerializableExtra("data", Data::class.java)

        selectedSong?.let {
            binding.iv.load(IMAGE_URL+ selectedSong!!.cover)
            binding.songNameTv.text = selectedSong!!.name
            binding.artistNameTv.text = selectedSong!!.artist
        }

        player.addListener(object : Player.Listener{

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when(playbackState){
                    Player.STATE_BUFFERING -> {
                        showPB()
                        binding.playPauseBtn.setImageDrawable(resources.getDrawable(R.drawable.play_ic,this@MusicPlayerActivity.theme))
                    }
                    else -> {
                        hidePB()
                        binding.playPauseBtn.setImageDrawable(resources.getDrawable(R.drawable.pause_ic, this@MusicPlayerActivity.theme))
                    }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                duration = player.duration.toInt() / 1000
                binding.seekBar.max = duration
//                binding.musicDurationTimeTv.text = getTimeString(duration)
            }

            override fun onPositionDiscontinuity(oldPosition: Player.PositionInfo,newPosition: Player.PositionInfo,reason: Int) {
                super.onPositionDiscontinuity(oldPosition, newPosition, reason)
                val currentPosition = player.currentPosition.toInt() / 1000
                binding.seekBar.progress = currentPosition
                binding.musicPlayedTimeTv.text = getTimeString(currentPosition)
                binding.musicDurationTimeTv.text = getTimeString(duration)
            }

        })

        binding.playPauseBtn.setOnClickListener {
            player.playWhenReady = !player.playWhenReady
            if (player.playWhenReady){
                binding.playPauseBtn.setImageDrawable(resources.getDrawable(R.drawable.pause_ic, this.theme))
            }
            if (!player.playWhenReady){
                binding.playPauseBtn.setImageDrawable(resources.getDrawable(R.drawable.play_ic, this.theme))
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    player.seekTo(progress.toLong() * 1000)
                    binding.musicPlayedTimeTv.text = getTimeString(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        binding.forwardBtn.setOnClickListener{
            player.seekTo(player.currentPosition + 10000)
        }

        binding.backwardBtn.setOnClickListener {
            player.seekTo(player.currentPosition - 10000)
        }

        val mediaItem = MediaItem.Builder()
            .setUri(selectedSong?.url)
            .build()
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true

        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable{
            override fun run() {
                val currentPosition = player.currentPosition.toInt() / 1000
                binding.seekBar.progress = currentPosition
                binding.musicPlayedTimeTv.text = getTimeString(currentPosition)
                binding.musicDurationTimeTv.text = getTimeString(duration)
                handler.postDelayed(this, 1000)
            }

        })

    }

    fun getTimeString(duration:Int) : String{
        val min = duration / 60
        val sec = duration % 60
        return String().format("%02d:%02d", min, sec)
    }

    fun hidePB(){
        binding.progressBar.isVisible = false
    }

    fun showPB(){
        binding.progressBar.isVisible = true
    }

}