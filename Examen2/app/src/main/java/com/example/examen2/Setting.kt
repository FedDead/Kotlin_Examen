package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.activity_registro2.*
import kotlinx.android.synthetic.main.activity_setting.*

class Setting : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val bundle=intent.extras
        var email=bundle?.getString("email")
        var pass = bundle?.getString("pass")
        var nombreNegocio = bundle?.getString("nombreNegocio")
        var imagen = bundle?.getString("imagen")
        var nombre: String = ""

        val metodo_pago = arrayOf("Efectivo", "Tarjeta", "Efectivo y Tarjeta")
        val adaptador1 =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, metodo_pago)
        spinner1.adapter = adaptador1

        val categoria_vendedor = arrayOf("Producto", "Servicio", "Productos y servicios")
        val adaptador2 =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoria_vendedor)
        spinner2.adapter = adaptador2

        val categoria_negocio = arrayOf("Farmacéuticas", "Automotrices", "Chocolates","Electrónica", "Abarrotes", "Telecomunicaciones")
        val adaptador3 =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoria_negocio)
        spinner3.adapter = adaptador3
                Toast.makeText(this, nombreNegocio, Toast.LENGTH_SHORT).show()
                var RefTienda = db.collection("Tiendas").document(nombreNegocio.toString())
                RefTienda.get().addOnSuccessListener {
                    nombreNegocio2.setText(it.get("Nombre del Negocio")as String?)
                    logo5.setText(it.get("Logo")as String?)
                }

        EditarSetting.setOnClickListener(){
            var RefTienda2 = db.collection("Tiendas").document(nombreNegocio2.text.toString())
            RefTienda2.set(
                hashMapOf(
                    "Nombre del Negocio" to nombreNegocio2.text.toString(),
                    "Metodo de pago" to spinner1.selectedItem.toString(),
                    "Categoria del vendedor" to spinner2.selectedItem.toString(),
                    "Categoria del negocio" to spinner3.selectedItem.toString(),
                    "Logo" to logo5.text.toString()
                )
            )
            finish()
            Toast.makeText(this, "Editado", Toast.LENGTH_SHORT).show()
        }

    }
}