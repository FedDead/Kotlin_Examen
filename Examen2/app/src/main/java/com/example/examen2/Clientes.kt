package com.example.examen2

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_clientes.*
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_estado_producto.*
import kotlinx.android.synthetic.main.activity_estado_producto.view.*
import kotlinx.android.synthetic.main.activity_popup.view.*
import kotlinx.android.synthetic.main.activity_registro.*


class Clientes : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val objetoIntent: Intent = intent
        var email = objetoIntent.getStringExtra("email")
        val pass = objetoIntent.getStringExtra("pass")
        var nombreNegocio = objetoIntent.getStringExtra("NombreNegocio")
        Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()

        val producto = arrayListOf<String>()
        val nombrenegocio = arrayListOf<String>()
        val Precios = arrayListOf<String>()
        val imagen = arrayListOf<String>()
        val cantidad = arrayListOf<String>()
        var estado: String = ""


        var x1=db.collection("Carrito").document(email.toString()).collection("Productos").whereEqualTo("Nombre del Negocio",nombreNegocio)
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
        var x2=db.collection("Carrito").document(email.toString()).collection("Servicios").whereEqualTo("Nombre del Negocio",nombreNegocio)
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

        buscarCliente.setOnClickListener{
            val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1
                ,producto )
            listView3.adapter=adapter

            busCliente.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    busCliente.clearFocus()
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
        actualizarCliente.setOnClickListener{
            val myListAdapter = MyListAdapterProducts(this, producto, nombrenegocio, Precios, imagen, cantidad)
            listView3.adapter = myListAdapter
            listView3.setOnItemClickListener(){adapterView, view, position, id ->
                val itemAtPos = adapterView.getItemAtPosition(position)
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
                //Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
                val dialogview= LayoutInflater.from(this).inflate(R.layout.activity_estado_producto,null)
                val mbuilder= AlertDialog.Builder(this).setView(dialogview)
                val mAlert = mbuilder.show()
                dialogview.titulo2.setText(itemAtPos.toString())
                dialogview.ActualizarEstado.setOnClickListener {
                    var n= dialogview.estadoactu.text.toString()
                    Toast.makeText(this, n, Toast.LENGTH_SHORT).show()
                    db.collection("Carrito").document(email.toString()).collection("Productos").document(itemAtPos.toString()).update("Estado del Producto", n)
                    mAlert.dismiss()
                }

            }
        }

       /* listView3.setOnItemLongClickListener(OnItemLongClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()


            val intent = Intent(this, editarCliente::class.java).apply {
                putExtra("email", email.toString())
                //putExtra("id", cambio.toString())
                putExtra("nombre", selectedItem.toString())
            }
            startActivity(intent)
            true
        })*/

    }

    }