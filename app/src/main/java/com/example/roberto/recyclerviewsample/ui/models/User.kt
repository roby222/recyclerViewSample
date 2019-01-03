package com.example.roberto.recyclerviewsample.ui.models

import com.example.roberto.domain.User as DomainUser

data class User(val id: Long, val name: String, val avatarId: String)

fun DomainUser.toPresentationModel(): User =
    User(id, name, avatarId)