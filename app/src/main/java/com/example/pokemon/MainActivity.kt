package com.example.pokemon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class MyAppGlideModule : AppGlideModule()


class MainActivity : AppCompatActivity() {
    var pokeImageURL = ""
    var pokeName = ""
    var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPokemonURL();
        Log.d("pokeImageURL", "pokemon image URL set")

        val button = findViewById<Button>(R.id.pokeButton)
        val imageView = findViewById<ImageView>(R.id.pokeImage)
        val name = findViewById<TextView>(R.id.nameText)
        val src = findViewById<TextView>(R.id.srcurl)

        getNextImage(button, imageView, name, src)

    }

    private fun getPokemonURL() {
        val client = AsyncHttpClient()
        val randomPokemonId = Random.nextInt(1, 899) //for random pokemon

        client["https://pokeapi.co/api/v2/pokemon-form/$randomPokemonId/", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Pokemon", "response successful$json")
                pokeImageURL = json.jsonObject.getJSONObject("sprites").getString("front_default")
                pokeName = json.jsonObject.getString("name")
                url = json.jsonObject.getJSONObject("pokemon").getString("url")

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

    private fun getNextImage(
        button: Button,
        imageView: ImageView,
        textView: TextView,
        src: TextView
    ) {
        button.setOnClickListener {
            getPokemonURL()
            textView.text = pokeName
            src.text = url

            Glide.with(this)
                .load(pokeImageURL)
                .fitCenter()
                .into(imageView)
        }
    }
}
