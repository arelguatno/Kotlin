package com.example.noteapp.unittesting

object UsernMe {

    val existingUser = listOf("arel", "john")

    fun userNameAndPassword(
        username: String,
        password: String,
        validatePass: String
    ): Boolean {
        if (username.isEmpty() || password.isEmpty() || validatePass.isEmpty())
            return false

        return true
    }
}