package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_servicios_editar.*

class ServiciosEditar : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios_editar)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val objetoIntent: Intent =intent
        var email=objetoIntent.getStringExtra("email")
        val nombre = objetoIntent.getStringExtra("nombre")

        Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(this, nombre.toString(), Toast.LENGTH_SHORT).show()


        Toast.makeText(this, nombre.toString(), Toast.LENGTH_SHORT).show()
        //Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()
        db.collection("Servicios").document(nombre.toString())
            .get().addOnSuccessListener{
                Servicio.setText(it.get("Servicio")as String?)
                DescripcionServicio.setText(it.get("Descripcion")as String?)
                PrecioVentaServicio.setText(it.get("PrecioVenta")as String?)
                PrecioCostoServicio.setText(it.get("PrecioCosto")as String?)
                FotoUrlServicio.setText(it.get("url" )as String?)
                StockServicio.setText(it.get("stock")as String?)
            }

        btnEditarServicio.setOnClickListener {
            eliminar(email.toString(), nombre.toString())

            db.collection("Servicios").document(Servicio.text.toString()).set(
                hashMapOf("Servicio" to Servicio.text.toString(),
                    "Descripcion" to DescripcionServicio.text.toString(),
                    "PrecioVenta" to PrecioVentaServicio.text.toString(),
                    "PrecioCosto" to PrecioCostoServicio.text.toString(),
                    "url" to FotoUrlServicio.text.toString(),
                    "stock" to StockServicio.text.toString()
                )
            )
            finish()
        }
        btnEliminarServicio.setOnClickListener {
            db.collection("Servicios").document(nombre.toString()).delete()
            finish()
        }
    }
    private fun eliminar(email:String, nombre:String){
        db.collection("Servicios").document(nombre).delete()
    }
}