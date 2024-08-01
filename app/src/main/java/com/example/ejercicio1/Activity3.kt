package com.example.ejercicio1

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class Activity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3) // Asegúrate de que `activity_3` es el nombre correcto de tu layout

        val editTextTextUrl: EditText = findViewById(R.id.editTextTextUrl)
        val buttonNav: Button = findViewById(R.id.buttonNav)
        val webView: WebView = findViewById(R.id.WebView1)

        // Configurar el WebView para que abra URLs dentro de la aplicación
        webView.webViewClient = WebViewClient()

        // Habilitar JavaScript si es necesario
        webView.settings.javaScriptEnabled = true

        buttonNav.setOnClickListener {
            val url = editTextTextUrl.text.toString()
            if (url.isNotEmpty()) {
                webView.loadUrl(url)
            }
        }
    }
}