package com.example.noteapp.unittesting

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UsernMeTest{

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            username = "",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly repeated password returns true`() {
        val result = RegistrationUtil.validateRegistrationInput(
            username = "Arel",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `username already exists returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            username = "Carl",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password`() {
        val result = RegistrationUtil.validateRegistrationInput(
            username = "Carl",
            password = "",
            confirmedPassword = ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password was repeated incorrectly`() {
        val result = RegistrationUtil.validateRegistrationInput(
            username = "Carl",
            password = "123",
            confirmedPassword = "1234"
        )
        assertThat(result).isFalse()
    }


    @Test
    fun `password contains less than 2 digits`() {
        val result = RegistrationUtil.validateRegistrationInput(
            username = "Carl",
            password = "asdasd1",
            confirmedPassword = "asdasd1"
        )
        assertThat(result).isFalse()
    }
}