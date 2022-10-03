package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.activity_tienda_select.*

class TiendaSelect : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tienda_select)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val objetoIntent: Intent =intent
        var email=objetoIntent.getStringExtra("email")
        var nombreNegocio = objetoIntent.getStringExtra("nombre")
        val nombre = objetoIntent.getStringExtra("Nombre")
        var imagen : String = ""

        tv30.text = nombreNegocio.toString()
        var docRef =  db.collection("Tiendas").document(nombreNegocio.toString())
        docRef.get().addOnSuccessListener{ document ->
            if(document.exists()){
                imagen = document.data?.get("Logo").toString()
                Picasso.get().load(imagen).error(R.drawable.nofoun)
                    .into(icon3)
            }}

        productos2.setOnClickListener{
            val intent = Intent(this,TiendaServicio::class.java).apply {
                putExtra("email", email)
                putExtra("NombreNegocio",nombreNegocio)
                putExtra("Nombre", nombre)
            }
            startActivity(intent)
        }

        service2.setOnClickListener{
            val intent = Intent(this,TiendaProducto::class.java).apply {
                putExtra("email", email)
                putExtra("NombreNegocio",nombreNegocio)
                putExtra("Nombre", nombre)
            }
            startActivity(intent)
        }

    }
}