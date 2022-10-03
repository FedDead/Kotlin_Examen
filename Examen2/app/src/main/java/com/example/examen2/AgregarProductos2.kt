package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_agregar_productos2.*
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.activity_registro.*

class AgregarProductos2 : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_productos2)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val objetoIntent: Intent =intent
        var email=objetoIntent.getStringExtra("email")
        var pass=objetoIntent.getStringExtra("pass")

        val visibilidad = arrayOf("Si", "No")
        val adaptador1 =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, visibilidad)
        spinner7.adapter = adaptador1

        btnAgregarProduct.setOnClickListener {
            var nombreTienda : String = ""
            var categoriaTienda : String = ""
            var docRef2 =  db.collection("user").document(email.toString())
            var docRef3 =  db.collection("Productos").document(Producto.text.toString())
            docRef2.get().addOnSuccessListener{ document ->
                if(document.exists()){
                    nombreTienda = document.data?.get("Nombre del Negocio").toString()
                    var docRef =  db.collection("Tiendas").document(nombreTienda)
                    docRef.get().addOnSuccessListener { document ->
                        if(document.exists()){
                            categoriaTienda = document.data?.get("Categoria del negocio").toString()
                            docRef3.set(
                                hashMapOf(
                                    "Nombre del negocio" to nombreTienda,
                                    "Categoria de la tienda" to categoriaTienda,
                                    "Producto" to Producto.text.toString(),
                                    "Descripcion" to Descripcion.text.toString(),
                                    "PrecioVenta" to PrecioVenta.text.toString(),
                                    "PrecioCosto" to PrecioCosto.text.toString(),
                                    "url" to FotoUrl.text.toString(),
                                    "stock" to Stock.text.toString(),
                                    "Visibilidad" to spinner7.selectedItem.toString()
                                )
                            )
                        }
                        else{
                            Toast.makeText(this, "Categoria del negocio no encontrada", Toast.LENGTH_SHORT).show()
                        }
                    }
                    Toast.makeText(this, nombreTienda, Toast.LENGTH_SHORT).show()
                    }
                else{
                    Toast.makeText(this, "Nombre de la tienda no encontrada", Toast.LENGTH_SHORT).show()
                }
            }

            Toast.makeText(this, "Producto agregado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}