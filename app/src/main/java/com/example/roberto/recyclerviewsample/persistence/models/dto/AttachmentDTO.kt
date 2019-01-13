package com.example.roberto.recyclerviewsample.persistence.models.dto

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class AttachmentDTO(@PrimaryKey val id: String, val title: String, val url: String, val thumbnailUrl: String)