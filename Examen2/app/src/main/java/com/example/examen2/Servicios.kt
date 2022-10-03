package com.example.examen2

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_productos.*
import kotlinx.android.synthetic.main.activity_productos.buscarCliente
import kotlinx.android.synthetic.main.activity_productos.listView
import kotlinx.android.synthetic.main.activity_servicios.*

class Servicios : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val objetoIntent: Intent =intent
        var email=objetoIntent.getStringExtra("email")
        var pass=objetoIntent.getStringExtra("pass")
        var nombreNegocio = objetoIntent.getStringExtra("NombreNegocio")
        Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()

        var servicio= arrayListOf<String>()
        var descripcion = arrayListOf<String>()
        var Precios = arrayListOf<String>()
        var imagen = arrayListOf<String>()

        buscarServicio.setOnClickListener{
            val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1
                ,servicio )
            listView2.adapter=adapter

            busServicio2.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    busServicio2.clearFocus()
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
        actualizarServicios.setOnClickListener{
            val x=db.collection("Servicios").whereEqualTo("Nombre del negocio",nombreNegocio).get()
                .addOnSuccessListener { result ->
                    servicio.clear()
                    descripcion.clear()
                    Precios.clear()
                    imagen.clear()  //Aqui iria la condicion para validar que el producto sea del negocio que se esta fijando
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
            listView2.adapter = myListAdapter
            listView2.setOnItemClickListener(){adapterView, view, position, id ->
                val itemAtPos = adapterView.getItemAtPosition(position)
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
            }
        }

        AgregarServicio.setOnClickListener{
            val intent2 = Intent(this,  ServiciosAgregar::class.java).apply {
                putExtra("email", email.toString())
                putExtra("pass", pass.toString())
            }
            startActivity(intent2)
        }

        listView2.setOnItemLongClickListener(AdapterView.OnItemLongClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            val intent = Intent(this, EditarProducto::class.java).apply {
                putExtra("email", email.toString())
                putExtra("nombre", selectedItem)
            }
            startActivity(intent)
            true
        })

    }
}