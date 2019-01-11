package com.example.roberto.recyclerviewsample.utils

import com.example.roberto.recyclerviewsample.persistence.models.ChatData
import com.google.gson.Gson

fun fakeNetworkLibrary(json: String): ChatData {
    return Gson().fromJson(json, ChatData::class.java)
}