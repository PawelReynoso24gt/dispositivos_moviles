package com.example.ejercicio1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val JsonString: String = "{\n" +
            "  \"Estados\": [\n" +
            "    \"Soltero\",\n" +
            "    \"Casado\",\n" +
            "    \"Divorciado\"\n" +
            "]\n" +
            "}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val InputNombre: EditText = findViewById(R.id.InputNombre)
        val InputEdad: EditText = findViewById(R.id.InputEdad)
        val InputCorreo: EditText = findViewById(R.id.InputCorreo)

        val button: Button =findViewById(R.id.button)
        button.setOnClickListener {
        val intent=Intent(this,Activity2::class.java)
            intent.putExtra("nombre",InputNombre.text.toString())
            intent.putExtra("edad",InputEdad.text.toString())
            intent.putExtra("correo",InputCorreo.text.toString())
            startActivity(intent)
            Toast.makeText(this, "Welcome", Toast.LENGTH_LONG).show()
        }

        // button2
        val buttonA: Button=findViewById(R.id.buttonWebView)
        buttonA.setOnClickListener {
            val intent = Intent(this, Activity3::class.java)
            startActivity(intent)
            Toast.makeText(this, "Welcome", Toast.LENGTH_LONG).show()
        }


        val StringArrayCarreras = arrayOf("Ingenieria", "Meidicina", "Liceniatura")

        val spinner: Spinner = findViewById(R.id.spinnerCarrera)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, StringArrayCarreras)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val gson = Gson()
        val itemsResponse = gson.fromJson(JsonString, estados::class.java)

        val spinnerEstados: Spinner = findViewById(R.id.spinnerEstado)
        val adapterEstados = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsResponse.Estados)
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEstados.adapter = adapterEstados

        // api departamentos
        val spinnerAPIDeptos: Spinner = findViewById(R.id.spinnerAPIDeptos)
        fetchDepartamentos { departamentos ->
            runOnUiThread {
                val adapterDeptos = ArrayAdapter(this, android.R.layout.simple_spinner_item, departamentos)
                adapterDeptos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerAPIDeptos.adapter = adapterDeptos
            }
        }

        // api carreras
        val spinnerAPICarreras: Spinner = findViewById(R.id.spinnerAPICarreras)
        fetchCarreras { carreras ->
            runOnUiThread {
                val adapterCarreras = ArrayAdapter(this, android.R.layout.simple_spinner_item, carreras)
                adapterCarreras.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerAPICarreras.adapter = adapterCarreras
            }
        }

        // api estado civil
        val spinnerAPIEstado: Spinner = findViewById(R.id.spinnerAPIEstado)
        fetchEstadoCivil { estados ->
            runOnUiThread {
                val adapterEstado = ArrayAdapter(this, android.R.layout.simple_spinner_item, estados)
                adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerAPIEstado.adapter = adapterEstado
            }
        }
    }

    private fun fetchDepartamentos(callback: (List<String>) -> Unit) {
        val url = "https://556b483a7c19490eb538c8422d5b4de1.api.mockbin.io/"
        fetchData(url, "departamento", callback)
    }

    private fun fetchCarreras(callback: (List<String>) -> Unit) {
        val url = "https://d4b15080dbbf4351a9b66894619a7044.api.mockbin.io/"
        fetchData(url, "nombre", callback)
    }

    private fun fetchEstadoCivil(callback: (List<String>) -> Unit) {
        val url = "https://343cb33690b54a78a359782661aabbf9.api.mockbin.io/"
        fetchData(url, "estado_civil", callback)
    }

    private fun fetchData(url: String, key: String, callback: (List<String>) -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    if (response.isSuccessful) {
                        val json = responseBody.string()
                        val listType = object : TypeToken<List<Map<String, String>>>() {}.type
                        val dataList = Gson().fromJson<List<Map<String, String>>>(json, listType)
                        val names = dataList.map { it[key] ?: "" }
                        callback(names)
                    } else {
                        // Manejar error en la respuesta
                    }
                }
            }
        })
    }
}

data class Departamento(val departamento: String)
data class Carrera(val nombre: String)
data class EstadoCivil(val estado_civil: String)