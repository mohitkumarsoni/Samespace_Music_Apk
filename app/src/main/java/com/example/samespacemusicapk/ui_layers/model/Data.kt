package com.example.samespacemusicapk.ui_layers.model

import java.io.Serializable

data class Data(
    val accent: String?,
    val artist: String?,
    val cover: String?,
    val date_created: String?,
    val date_updated: String?,
    val id: Int?,
    val name: String?,
    val sort: Any?,
    val status: String?,
    val top_track: Boolean?,
    val url: String?,
    val user_created: String?,
    val user_updated: String?
):Serializable