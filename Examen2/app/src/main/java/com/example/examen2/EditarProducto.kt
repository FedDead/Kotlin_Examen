package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_agregar_productos2.*
import kotlinx.android.synthetic.main.activity_editar_producto.*
import kotlinx.android.synthetic.main.activity_editar_producto.Descripcion
import kotlinx.android.synthetic.main.activity_editar_producto.FotoUrl
import kotlinx.android.synthetic.main.activity_editar_producto.PrecioCosto
import kotlinx.android.synthetic.main.activity_editar_producto.PrecioVenta
import kotlinx.android.synthetic.main.activity_editar_producto.Producto
import kotlinx.android.synthetic.main.activity_editar_producto.Stock

class EditarProducto : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val objetoIntent: Intent =intent
        var email=objetoIntent.getStringExtra("email")
        val nombre = objetoIntent.getStringExtra("nombre")

        val visibilidad = arrayOf("Si", "No")
        val adaptador12 =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, visibilidad)
        spinner12.adapter = adaptador12

        Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(this, nombre.toString(), Toast.LENGTH_SHORT).show()


        Toast.makeText(this, nombre.toString(), Toast.LENGTH_SHORT).show()
        //Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()
        db.collection("Productos").document(nombre.toString())
            .get().addOnSuccessListener{
                Producto.setText(it.get("Producto")as String?)
                Descripcion.setText(it.get("Descripcion")as String?)
                PrecioVenta.setText(it.get("PrecioVenta")as String?)
                PrecioCosto.setText(it.get("PrecioCosto")as String?)
                FotoUrl.setText(it.get("url" )as String?)
                Stock.setText(it.get("stock")as String?)
            }

        btnEditarProduct.setOnClickListener {
            eliminar(email.toString(), nombre.toString())
            var nombreTienda : String = ""
            var categoriaTienda : String = ""
            var docRef2 =  db.collection("user").document(email.toString())
            var docRef3 =  db.collection("Productos").document(Producto.text.toString())
            docRef2.get().addOnSuccessListener{ document ->
                if(document.exists()){
                    nombreTienda = document.data?.get("Nombre del Negocio").toString()
            db.collection("Productos").document(Producto.text.toString()).set(
                hashMapOf("Nombre del negocio" to nombreTienda,
                    "Producto" to Producto.text.toString(),
                    "Descripcion" to Descripcion.text.toString(),
                    "PrecioVenta" to PrecioVenta.text.toString(),
                    "PrecioCosto" to PrecioCosto.text.toString(),
                    "url" to FotoUrl.text.toString(),
                    "stock" to Stock.text.toString(),
                    "Visibilidad" to spinner12.selectedItem.toString()))
                }
            }
            finish()
        }
        btnEliminarProduct.setOnClickListener {
            db.collection("Productos").document(nombre.toString()).delete()
            finish()
        }
    }
    private fun eliminar(email:String, nombre:String){
        db.collection("Productos").document(nombre).delete()
    }
}
