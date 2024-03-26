package com.example.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
class MainActivity : AppCompatActivity() {
    var pokeImageURL =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPokemonImageURL()
        Log.d("pokeImageURL","poke image URL set")
        val button = findViewById<Button>(R.id.pokeButton)
        val imageView = findViewById<ImageView>(R.id.pokeImage)
        getNextImage(button,imageView)
    }
    private fun getNextImage(button: Button, imageView: ImageView) {
        button.setOnClickListener {
            getPokemonImageURL()
            Glide.with(this)
                .load(pokeImageURL)
                .fitCenter()
                .into(imageView)
        }
    }
    private fun getPokemonImageURL() {
        val client = AsyncHttpClient()
        //https://pokeapi.co/api/v2/pokemon/ditto
        //https://dog.ceo/api/breeds/image/random
        client["https://pokeapi.co/api/v2/pokemon/ditto", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                pokeImageURL = json.jsonObject.getString("message")
                Log.d("Pokemon", "response successful$json")
            }
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Pokemon Error", errorResponse)
            }
        }]
    }
}