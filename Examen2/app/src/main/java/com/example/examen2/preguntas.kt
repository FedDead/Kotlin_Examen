package com.example.examen2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class preguntas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preguntas)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()
        val bundle=intent.extras
        val email=bundle?.getString("email")
        val pass = bundle?.getString("pass")
        val nombre =bundle?.getString("Nombre")
        val municipio = bundle?.getString("Municipio")
        val colonia =bundle?.getString("Colonia")
        val calle = bundle?.getString("Calle")
        val telefono =bundle?.getString("Telefono")
        val ife = bundle?.getString("IFE")
    }
}