package com.example.examen2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_carrito.*
import kotlinx.android.synthetic.main.activity_popup.view.*
import kotlinx.android.synthetic.main.activity_pedidos.*

class Pedidos : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val objetoIntent: Intent =intent
        var email=objetoIntent.getStringExtra("email")
        var pass=objetoIntent.getStringExtra("pass")
        var name=objetoIntent.getStringExtra("Nombre")
        Toast.makeText(this, name.toString(), Toast.LENGTH_SHORT).show()

        val producto= arrayListOf<String>()
        val nombrenegocio = arrayListOf<String>()
        val Precios = arrayListOf<String>()
        val imagen = arrayListOf<String>()
        val cantidad = arrayListOf<String>()
        var estado : String = ""
        tv88.setText(name.toString())

        var x1=db.collection("Carrito").document(email.toString()).collection("Productos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    producto.add(document.get("Producto").toString())
                    nombrenegocio.add(document.get("Nombre del Negocio").toString())
                    Precios.add("$"+document.get("Precio").toString())
                    cantidad.add("Cantidad "+document.get("Cantidad").toString())
                    imagen.add(document.get("url").toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
        var x2=db.collection("Carrito").document(email.toString()).collection("Servicios")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    producto.add(document.get("Producto").toString())
                    nombrenegocio.add(document.get("Nombre del Negocio").toString())
                    Precios.add("$"+document.get("Precio").toString())
                    cantidad.add("Cantidad "+document.get("Cantidad").toString())
                    imagen.add(document.get("url").toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }

        verPedidos.setOnClickListener {
            val myListAdapter = MyListAdapterProducts(this, producto, nombrenegocio, Precios, imagen, cantidad)
            listView9.adapter = myListAdapter
            listView9.setOnItemClickListener(){adapterView, view, position, id ->
                val itemAtPos = adapterView.getItemAtPosition(position)
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                var refestado = db.collection("Carrito").document(email.toString()).collection("Productos").document(itemAtPos.toString())
                    .get().addOnSuccessListener { document ->
                        if (document.exists()){
                            estado = document.data?.get("Estado del Producto").toString()
                            textView60.setText("Estado: "+estado)
                        }
                    }
                var refestado2 = db.collection("Carrito").document(email.toString()).collection("Servicios").document(itemAtPos.toString())
                    .get().addOnSuccessListener { document ->
                        if (document.exists()){
                            estado = document.data?.get("Estado del Producto").toString()
                            textView60.setText("Estado: "+estado)
                        }
                    }
            }
        }

    }
}