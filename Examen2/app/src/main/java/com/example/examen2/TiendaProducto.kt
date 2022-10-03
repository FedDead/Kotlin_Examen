package com.example.examen2

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_popup.view.*
import kotlinx.android.synthetic.main.activity_tienda_producto.*

class TiendaProducto : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tienda_producto)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val objetoIntent: Intent =intent
        var email = objetoIntent.getStringExtra("email")
        var pass = objetoIntent.getStringExtra("pass")
        var nombreNegocio:String = ""
        var nombre = objetoIntent.getStringExtra("Nombre")
        var nombreNegocio2 = objetoIntent.getStringExtra("NombreNegocio")
        Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()

        var producto= arrayListOf<String>()
        var descripcion = arrayListOf<String>()
        var Precios = arrayListOf<String>()
        var Precios2:String = ""
        var Stock:String = ""
        var Url:String = ""
        var imagen = arrayListOf<String>()
        var iden = arrayListOf<String>()

        //var docRef = db.collection("Productos").whereEqualTo("").get()

        buscarClienteTienda.setOnClickListener{
            val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1
                ,producto)
            listView11.adapter=adapter

            busProductoTienda.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    busProductoTienda.clearFocus()
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
        actualizarProductosTienda.setOnClickListener{
            db.collection("Productos").whereEqualTo("Nombre del negocio",nombreNegocio2).get()
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
            listView11.adapter = myListAdapter
            listView11.setOnItemClickListener(){adapterView, view, position, id ->
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
                    db.collection("Productos").document(itemAtPos.toString()).get().addOnSuccessListener { document ->
                        if (document.exists()){
                            Stock = document.data?.get("stock").toString()
                            if (Stock.toInt()>=n.toInt()){
                                var nuevo = Stock.toInt()-n.toInt()

                                db.collection("Productos").document(itemAtPos.toString()).update("stock", nuevo.toString())
                                Toast.makeText(this, "cambiado", Toast.LENGTH_SHORT).show()
                            }
                            else{

                                Toast.makeText(this, "No contamos con tantos productos", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    mAlert.dismiss()
                }
                dialogview.CarritoComprar.setOnClickListener {
                    var n= dialogview.compra.text.toString()
                    db.collection("Productos").document(itemAtPos.toString()).get().addOnSuccessListener { document ->
                        if (document.exists()){
                            Precios2 = document.data?.get("PrecioVenta").toString()
                            nombreNegocio = document.data?.get("Nombre del negocio").toString()
                            Url = document.data?.get("url").toString()
                            db.collection("Compras").document(email.toString()).collection("Productos").document(itemAtPos.toString())
                                .set(hashMapOf(
                                    "Nombre del Negocio" to nombreNegocio.toString(),
                                    "Nombre del Comprador" to nombre,
                                    "Producto" to itemAtPos.toString(),
                                    "Cantidad" to n,
                                    "Precio" to Precios2,
                                    "url" to Url
                                ))
                            db.collection("Carrito").document(email.toString()).collection("Productos").document(itemAtPos.toString())
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