package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_agregar_clientes2.*

class AgregarClientes2 : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_clientes2)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val objetoIntent: Intent =intent
        var email=objetoIntent.getStringExtra("email")
        val pass =objetoIntent.getStringExtra("pass")


        btnAgregarCliente.setOnClickListener {
            db.collection("user").document(email.toString()).collection("clientes").
            document(nombreCliente.text.toString()).set(
                hashMapOf("Nombre" to nombreCliente.text.toString(),
                    "Dirrecion" to Dirrecion.text.toString(),
                    "Correo" to Correo.text.toString(),
                    "Telefono" to Telefono1.text.toString()
                )
            )

            Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}