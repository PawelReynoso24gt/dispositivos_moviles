package com.example.ejercicio1

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Activity4 : AppCompatActivity(){

    data class Sitio(val nombreSitio: String, val url: String)

    private val jsonString = """
        [
            {"nombreSitio": "google", "url": "https://www.google.com"},
            {"nombreSitio": "facebook", "url": "https://www.facebook.com"},
            {"nombreSitio": "youtube", "url": "https://www.youtube.com"}
        ]
    """

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_4)

        val spinnerNav: Spinner = findViewById(R.id.spinnerNav)
        val buttonNav2: Button = findViewById(R.id.buttonNav2)
        val webView: WebView = findViewById(R.id.webView2)

        // Configurar el WebView para que abra URLs dentro de la aplicación
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        // Parsear el JSON
        val gson = Gson()
        val listType = object : TypeToken<List<Sitio>>() {}.type
        val sitios: List<Sitio> = gson.fromJson(jsonString, listType)

        // Obtener los nombres de los sitios para el Spinner
        val nombresSitios = sitios.map { it.nombreSitio }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresSitios)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNav.adapter = adapter

        // Manejar el evento del botón
        buttonNav2.setOnClickListener {
            val selectedPosition = spinnerNav.selectedItemPosition
            val selectedSitio = sitios[selectedPosition]
            webView.loadUrl(selectedSitio.url)
        }
    }
}