package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_agregar_clientes2.*
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.activity_registro2.*

class Registro : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val bundle=intent.extras
        val email=bundle?.getString("email")
        val pass = bundle?.getString("pass")
        val nombre =bundle?.getString("Nombre")
        var municipio : String = ""
        var colonia : String = ""
        var calle : String = ""
        var telefono : String = ""
        var ife : String = ""


        val metodo_pago = arrayOf("Efectivo", "Tarjeta", "Efectivo y Tarjeta")
        val adaptador1 =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, metodo_pago)
        spinner4.adapter = adaptador1

        val categoria_vendedor = arrayOf("Producto", "Servicio", "Productos y servicios")
        val adaptador2 =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoria_vendedor)
        spinner5.adapter = adaptador2

        val categoria_negocio = arrayOf("Farmacéuticas", "Automotrices", "Chocolateria","Electrónica", "Abarrotes", "Telecomunicaciones", "Restaurante de comida rapida")
        val adaptador3 =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoria_negocio)
        spinner6.adapter = adaptador3

        btnRegistro2.setOnClickListener() {
            var RefUser = db.collection("user").document(email.toString())
                RefUser.get().addOnSuccessListener { document ->
                    if (document.exists()){
                        municipio = document.data?.get("Municipio").toString()
                        colonia = document.data?.get("Colonia").toString()
                        calle = document.data?.get("Calle").toString()
                        telefono = document.data?.get("Telefono").toString()
                        ife = document.data?.get("IFE").toString()

                        RefUser.set(hashMapOf(
                            "Nombre" to nombre.toString(),
                            "Municipio" to municipio,
                            "Colonia" to colonia,
                            "Calle" to calle,
                            "Telefono" to telefono,
                            "Correo" to email.toString(),
                            "Contraseña" to pass.toString(),
                            "IFE" to ife,
                            "Nombre del Negocio" to nombreNegocio.text.toString(),
                            "Usuario" to "Cliente y Vendedor"))
                    }
                }
            var RefTienda = db.collection("Tiendas").document(nombreNegocio.text.toString())
                RefTienda.set(hashMapOf(
                    "Nombre del Negocio" to nombreNegocio.text.toString(),
                    "Nombre" to nombre.toString(),
                    "Municipio" to municipio,
                    "Correo" to email.toString(),
                    "Metodo de pago" to spinner4.selectedItem.toString(),
                    "Categoria del vendedor" to spinner5.selectedItem.toString(),
                    "Categoria del negocio" to spinner6.selectedItem.toString(),
                    "Logo" to logo2.text.toString()
                )
                )
            val intent = Intent(this, InicioCliente::class.java).apply {
                putExtra("email", email)
                putExtra("pass", pass)
                putExtra("Nombre", nombre)
                putExtra("Municipio", municipio)
                putExtra("Colonia", colonia)
                putExtra("Calle", calle)
                putExtra("Telefono", telefono)
                putExtra("IFE", ife)
                putExtra("NombreNegocio",nombreNegocio.text.toString())
            }
            startActivity(intent)
        }
    }


}