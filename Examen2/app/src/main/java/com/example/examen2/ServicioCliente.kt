package com.example.examen2

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_popup.view.*
import kotlinx.android.synthetic.main.activity_servicio_cliente.*
import kotlinx.android.synthetic.main.activity_servicio_cliente.listView5
import kotlinx.android.synthetic.main.activity_tienda_servicio.*

class ServicioCliente : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicio_cliente)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val objetoIntent: Intent =intent
        var email=objetoIntent.getStringExtra("email")
        var pass=objetoIntent.getStringExtra("pass")
        val nombre =objetoIntent.getStringExtra("Nombre")
        var nombreNegocio = objetoIntent.getStringExtra("NombreNegocio")
        Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()

        var servicio= arrayListOf<String>()
        var descripcion = arrayListOf<String>()
        var Precios = arrayListOf<String>()
        var imagen = arrayListOf<String>()

        var Precios2:String = ""
        var Url:String = ""

        buscarServicioCliente.setOnClickListener{
            val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1
                ,servicio )
            listView5.adapter=adapter

            busServicioCliente.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    busServicioCliente.clearFocus()
                    if (servicio.contains(p0)) {
                        adapter.filter.filter(p0)
                    } else {
                        Toast.makeText(applicationContext, "Item not found", Toast.LENGTH_LONG)
                            .show()
                    }
                    return false
                }
                override  fun onQueryTextChange(p0: String?): Boolean {
                    adapter.filter.filter(p0)
                    return false
                }
            })

        }
        actualizarServicioCliente.setOnClickListener{
            db.collection("Servicios").whereEqualTo("Visibilidad","Si").get()
                .addOnSuccessListener { result ->
                    servicio.clear()
                    descripcion.clear()
                    Precios.clear()
                    imagen.clear()
                    for (document in result) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        servicio.add(document.get("Servicio").toString())
                        descripcion.add(document.get("Descripcion").toString())
                        imagen.add(document.get("url").toString())
                        Precios.add("Precio: $"+document.get("Precio").toString())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                }
            val myListAdapter = CarritoAdapter(this, servicio, descripcion, imagen, Precios)
            listView5.adapter = myListAdapter
            listView5.setOnItemClickListener(){adapterView, view, position, id ->
                val itemAtPos = adapterView.getItemAtPosition(position)
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
                val dialogview= LayoutInflater.from(this).inflate(R.layout.activity_popup,null)
                val mbuilder= AlertDialog.Builder(this).setView(dialogview)
                val mAlert = mbuilder.show()
                Toast.makeText(this, itemAtPos.toString(), Toast.LENGTH_SHORT).show()
                dialogview.titulo.setText(itemAtPos.toString())
                //var n= dialogview.compra.text.toString()
                dialogview.comprare.setOnClickListener {
                    var n= dialogview.compra.text.toString()
                    db.collection("Servicios").document(itemAtPos.toString()).get().addOnSuccessListener { document ->
                        if (document.exists()){
                            Precios2 = document.data?.get("Precio").toString()
                            nombreNegocio = document.data?.get("Nombre del negocio").toString()
                            Url = document.data?.get("url").toString()
                            db.collection("Carrito").document(email.toString()).collection("Servicios").document(itemAtPos.toString())
                                .set(hashMapOf(
                                    "Nombre del Negocio" to nombreNegocio.toString(),
                                    "Nombre del Comprador" to nombre,
                                    "Producto" to itemAtPos.toString(),
                                    "Cantidad" to n,
                                    "Precio" to Precios2,
                                    "url" to Url,
                                    "Estado del Producto" to "En camino"
                                ))
                        }
                    }
                    mAlert.dismiss()
                }

                dialogview.CarritoComprar.setOnClickListener {
                    var n= dialogview.compra.text.toString()
                    db.collection("Servicios").document(itemAtPos.toString()).get().addOnSuccessListener { document ->
                        if (document.exists()){
                            Precios2 = document.data?.get("Precio").toString()
                            nombreNegocio = document.data?.get("Nombre del negocio").toString()
                            Url = document.data?.get("url").toString()
                            db.collection("Compras").document(email.toString()).collection("Servicios").document(itemAtPos.toString())
                                .set(hashMapOf(
                                    "Nombre del Negocio" to nombreNegocio.toString(),
                                    "Nombre del Comprador" to nombre,
                                    "Producto" to itemAtPos.toString(),
                                    "Cantidad" to n,
                                    "Precio" to Precios2,
                                    "url" to Url,
                                    "Estado del Producto" to "En camino"
                                ))
                        }
                    }

                    Toast.makeText(this, "Agregado a carrito", Toast.LENGTH_SHORT).show()
                    mAlert.dismiss()
                }
            }
        }

    }
}