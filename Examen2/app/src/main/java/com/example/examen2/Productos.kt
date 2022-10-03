package com.example.examen2

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_productos.*
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_editar_cliente.*
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.activity_popup.view.*
import kotlinx.android.synthetic.main.activity_productos.listView
import kotlinx.android.synthetic.main.activity_setting.*


class Productos : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()


        val objetoIntent: Intent =intent
        var email=objetoIntent.getStringExtra("email")
        var pass=objetoIntent.getStringExtra("pass")
        var nombreNegocio = objetoIntent.getStringExtra("NombreNegocio")



        var producto= arrayListOf<String>()
        var descripcion = arrayListOf<String>()
        var Precios = arrayListOf<String>()
        var imagen = arrayListOf<String>()
        var iden = arrayListOf<String>()
        var negocio = arrayListOf<String>()
        var ne: String = ""
           // .document("Nombre del negocio")
        var docRef =  db.collection("user").document(email.toString())
        docRef.get().addOnSuccessListener{ document ->
            if(document.exists()){
                ne = document.data?.get("Nombre del negocio").toString()
            }}
        Toast.makeText(this, nombreNegocio.toString(), Toast.LENGTH_SHORT).show()

        //Toast.makeText(this, ne.toString(), Toast.LENGTH_SHORT).show()
        buscarCliente.setOnClickListener{
            val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1
                ,producto )
            listView.adapter=adapter

            busProducto.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    busProducto.clearFocus()
                    if (producto.contains(p0)) {
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

        actualizarProductos.setOnClickListener{
            val x=db.collection("Productos").whereEqualTo("Nombre del negocio",nombreNegocio)
                .get()
                .addOnSuccessListener { result ->
                    producto.clear()
                    descripcion.clear()
                    Precios.clear()
                    imagen.clear()
                    iden.clear()
                    for (document in result) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                            producto.add(document.get("Producto").toString())
                            descripcion.add(document.get("Descripcion").toString())
                            imagen.add(document.get("url").toString())
                            iden.add("stock "+document.get("stock").toString())
                            Precios.add("Precio Venta "+document.get("PrecioVenta").toString()+" - "+"Costo "
                                    +document.get("PrecioCosto").toString())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                }

            val myListAdapter = MyListAdapterProducts(this, producto, descripcion, Precios, imagen, iden)
            listView.adapter = myListAdapter
            listView.setOnItemClickListener(){adapterView, view, position, id ->
                val itemAtPos = adapterView.getItemAtPosition(position)
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()

            }
        }

        AgregarProducto2.setOnClickListener{
            val intent2 = Intent(this,  AgregarProductos2::class.java).apply {
                putExtra("email", email.toString())
                putExtra("pass", pass.toString())
            }
            startActivity(intent2)
        }

        listView.setOnItemLongClickListener(OnItemLongClickListener{ parent, view, position, id ->
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