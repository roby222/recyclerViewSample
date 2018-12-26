package com.example.roberto.usecases

import com.example.roberto.data.UsersRepository
import com.example.roberto.domain.User

class GetUsers(private val usersRepository: UsersRepository) {

    operator fun invoke(): List<User> = usersRepository.getSavedUsers()

}
