package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_inicio.*

class Inicio : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val bundle=intent.extras
        val email=bundle?.getString("email")
        val pass = bundle?.getString("pass")
        var imagen: String = ""
        var nombreNegocio = bundle?.getString("NombreNegocio")
        Toast.makeText(this, nombreNegocio, Toast.LENGTH_SHORT).show()
            var docRef =  db.collection("Tiendas").document(nombreNegocio.toString())
        docRef.get().addOnSuccessListener{ document ->
            if(document.exists()){
                imagen = document.data?.get("Logo").toString()
                tv8.text = nombreNegocio.toString()
                Picasso.get().load(imagen).error(R.drawable.nofoun)
                    .into(icon2)
            }}
        Toast.makeText(this, nombreNegocio.toString(), Toast.LENGTH_SHORT).show()


        verClientes.setOnClickListener{
            val intent = Intent(this, Clientes::class.java).apply {
                putExtra("email", email)
                putExtra("pass", pass)
                putExtra("NombreNegocio",nombreNegocio)
                }
            startActivity(intent)
        }

        productos.setOnClickListener {
            val intent = Intent(this,Productos::class.java).apply {
                putExtra("email", email)
                putExtra("pass", pass)
                putExtra("NombreNegocio",nombreNegocio)
            }
            startActivity(intent)
        }

        service.setOnClickListener {
            val intent = Intent(this,Servicios::class.java).apply {
                putExtra("email", email)
                putExtra("pass", pass)
                putExtra("NombreNegocio",nombreNegocio)
            }
            startActivity(intent)
        }

        setting.setOnClickListener {
                    val intent = Intent(this,Setting::class.java).apply {
                                putExtra("email", email)
                                putExtra("pass", pass)
                                putExtra("nombreNegocio",nombreNegocio)
                    }
                    startActivity(intent)
            }
        }

    }

