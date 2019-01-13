package com.example.roberto.recyclerviewsample.utils

import com.example.roberto.recyclerviewsample.persistence.models.dto.ChatDataDTO
import com.google.gson.Gson

fun fakeNetworkLibrary(json: String): ChatDataDTO {
    return Gson().fromJson(json, ChatDataDTO::class.java)
}