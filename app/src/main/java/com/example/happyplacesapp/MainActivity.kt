package com.example.happyplacesapp

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.happyplacesapp.databinding.ActivityMainBinding
import com.example.happyplacesapp.databinding.WebviewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Error
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: WebviewBinding

    companion object {
        const val TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl("google.com")

        val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
            putExtra("test", "this is main")
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        if(binding.webView.canGoBack()){
            binding.webView.goBack()
        }else{
            finish()
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()

        val array = listOf(1,2,3,4,5)
        for(x in array.indices){
            val gg = array[x] + 1
            Log.d(TAG, gg.toString())
        }
    }

    private fun callBackGround() {

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://run.mocky.io/v3/e48243b9-cc66-4d5c-916f-59d9bc767f08")
                var connection = url.openConnection() as HttpURLConnection

                connection.doInput = true
                connection.doOutput = true

                val httpResult: Int = connection.responseCode

                if (httpResult == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
//
//                    val reader = BufferedReader(InputStreamReader(inputStream))
//                    val stringBuilder = StringBuilder()
//
//                    var line: String? = ""
//                    try {
//                        while (reader.readLine().also { line = it } != null) {
//                            stringBuilder.append(line + "\n")
//                        }
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    } finally {
//                        try {
//                            inputStream.close()
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        }
//                    }
//
//                    var result = stringBuilder.toString()

                }

            } catch (e: Error) {

            }
        }
    }
}


