package com.example.noteapp.unittesting

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.noteapp.unittesting.local.ShoppingItemDatabase
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceCompareTest {
    private lateinit var resourceComparer: ResourceCompare

    @Before
    fun setUp() {
        resourceComparer = ResourceCompare()
    }

    @After
    fun tearDown(){
        //Destroy objects
    }

    @Test
    fun stringResourceSaveAsGivingString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "Unit Testing")

        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceSaveAsGivingString_returnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "Unit Testing")

        assertThat(result).isFalse()
    }
}