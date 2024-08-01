package com.example.ejercicio1

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2)

        val txtNombre: TextView =findViewById(R.id.txtNombre)
        val txtEdad: TextView =findViewById(R.id.TxtEdad)
        val txtCorreo: TextView =findViewById(R.id.TxtCorreo)

        val nombre=intent.getStringExtra("nombre")
        val edad=intent.getStringExtra("edad")
        val correo=intent.getStringExtra("correo")

        txtNombre.text=nombre
        txtEdad.text=edad
        txtCorreo.text=correo

        }
    }
