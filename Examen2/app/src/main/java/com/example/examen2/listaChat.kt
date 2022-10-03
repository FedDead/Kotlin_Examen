package com.example.examen2

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_lista_chat.*

class listaChat : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_chat)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val objetoIntent: Intent =intent
        var email=objetoIntent.getStringExtra("email")
        var pass=objetoIntent.getStringExtra("pass")
        var nombreNegocio = objetoIntent.getStringExtra("NombreNegocio")
        var nombre = objetoIntent.getStringExtra("Nombre")
        Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()

        var tienda= arrayListOf<String>()
        var categorianegocio = arrayListOf<String>()
        var categoriavendedor = arrayListOf<String>()
        var imagen = arrayListOf<String>()

        buscarTiendaChat.setOnClickListener{
            val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1
                ,tienda )
            listView4Chat.adapter=adapter

            busTiendaChat.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    busTiendaChat.clearFocus()
                    if (tienda.contains(p0)) {
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
        actualizarTiendaChat.setOnClickListener{
            val x=db.collection("Tiendas").get()
                .addOnSuccessListener { result ->
                    tienda.clear()
                    categorianegocio.clear()
                    categoriavendedor.clear()
                    imagen.clear()
                    for (document in result) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        tienda.add(document.get("Nombre del Negocio").toString())
                        categorianegocio.add(document.get("Categoria del negocio").toString())
                        imagen.add(document.get("Logo").toString())
                        categoriavendedor.add(document.get("Categoria del vendedor").toString())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                }
            val myListAdapter = CarritoAdapter(this, tienda, categorianegocio, imagen, categoriavendedor)
            listView4Chat.adapter = myListAdapter
        }


        listView4Chat.setOnItemClickListener(){ parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, chat::class.java).apply {
                putExtra("email", email.toString())
                putExtra("nombre", nombre)
                putExtra("NombreNegocio",selectedItem)
            }
            startActivity(intent)
            true
        }

    }
}