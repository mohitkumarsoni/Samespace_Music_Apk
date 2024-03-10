package com.example.samespacemusicapk.ui_layers.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.samespacemusicapk.common.IMAGE_URL
import com.example.samespacemusicapk.databinding.MusicListItemRvBinding
import com.example.samespacemusicapk.ui_layers.model.Data
import com.example.samespacemusicapk.ui_layers.sheets.activity_ui.music_player_activity.MusicPlayerActivity

class SongAdapter(var context: Context) : Adapter<SongAdapter.MyViewHolder>() {

    inner class MyViewHolder(var binding:MusicListItemRvBinding) : ViewHolder(binding.root)

    private var diffCallback = object : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    var differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(MusicListItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val song = differ.currentList[position]

        holder.binding.apply {
            songImageIv.load(IMAGE_URL+song.cover)
            songNameTv.text = song.name
            artistNameTv.text = song.artist
        }

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, MusicPlayerActivity::class.java).putExtra("data",song))
        }

    }
}