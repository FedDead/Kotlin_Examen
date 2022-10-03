package com.example.examen2

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_carrito.*
import kotlinx.android.synthetic.main.activity_carrito.listView
import kotlinx.android.synthetic.main.activity_popup.view.*
import kotlinx.android.synthetic.main.activity_producto_cliente.*

class Carrito : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

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
        var cantidad2 : String = ""
        var stock2 : String = ""
        var nombrenegocio2 : String = ""
        var producto3 : String = ""
        var precio2 : String = ""
        var total : Int = 0
        var nuevo: String = ""
        tv8.setText(name.toString())

        var x1=db.collection("Compras").document(email.toString()).collection("Productos").whereEqualTo("Nombre del Comprador",name)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    producto.add(document.get("Producto").toString())
                    nombrenegocio.add(document.get("Nombre del Negocio").toString())
                    Precios.add("$"+document.get("Precio").toString())
                    cantidad.add("Cantidad "+document.get("Cantidad").toString())
                    imagen.add(document.get("url").toString())
                    cantidad2 = document.data?.get("Cantidad").toString()
                    precio2 = document.data?.get("Precio").toString()
                    total+= (cantidad2.toInt() * precio2.toInt())
                    textView31.setText("Total: "+total)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
        var x2=db.collection("Compras").document(email.toString()).collection("Servicios").whereEqualTo("Nombre del Comprador",name)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    producto.add(document.get("Producto").toString())
                    nombrenegocio.add(document.get("Nombre del Negocio").toString())
                    Precios.add("$"+document.get("Precio").toString())
                    cantidad.add("Cantidad "+document.get("Cantidad").toString())
                    imagen.add(document.get("url").toString())
                    cantidad2 = document.data?.get("Cantidad").toString()
                    precio2 = document.data?.get("Precio").toString()
                    total+= (cantidad2.toInt() * precio2.toInt())
                    textView31.setText("Total: "+total)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }

        comprar.setOnClickListener{
            val myListAdapter = MyListAdapterProducts(this, producto, nombrenegocio, Precios, imagen, cantidad)
            listView.adapter = myListAdapter
            listView.setOnItemClickListener(){adapterView, view, position, id ->
                val itemAtPos = adapterView.getItemAtPosition(position)
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
        }
    }

        ComprarCarrito.setOnClickListener {
            var x1=db.collection("Compras").document(email.toString()).collection("Productos").whereEqualTo("Nombre del Comprador",name)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        producto3 = document.data?.get("Producto").toString()
                        cantidad2 = document.data?.get("Cantidad").toString()
                        precio2 = document.data?.get("Precio").toString()
                        nombrenegocio2 = document.data?.get("Nombre del Negocio").toString()
                        var x2 = db.collection("Productos").document(producto3).get().addOnSuccessListener { document ->
                            if (document.exists()){
                                stock2 = document.data?.get("stock").toString()
                                nuevo = (stock2.toInt() - cantidad2.toInt()).toString()
                                var refproducto = db.collection("Productos").document(producto3).update("stock", nuevo)
                            }
                        }
                        var refproductoEliminar = db.collection("Compras").document(email.toString()).collection("Productos").document(producto3).delete()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                }

                producto.clear()
                nombrenegocio.clear()
                Precios.clear()
                cantidad.clear()
                imagen.clear()
                textView31.setText("Total: ")
            }
        }
    }

