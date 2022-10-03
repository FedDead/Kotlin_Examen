package com.example.examen2

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_inicio_cliente.*
import kotlinx.android.synthetic.main.activity_setting2.*

class InicioCliente : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_cliente)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val bundle=intent.extras
        val email=bundle?.getString("email")
        val pass = bundle?.getString("pass")
        val nombre =bundle?.getString("Nombre")
        var nombreNegocio: String = ""



        imageView6.setOnClickListener{
            var docRef =  db.collection("user").document(email.toString())
            docRef.get().addOnSuccessListener { document ->
                if(document.exists()){
                    nombreNegocio = document.data?.get("Nombre del Negocio").toString()
                    val intent = Intent(this, ProductoCliente::class.java).apply {
                        putExtra("email", email)
                        putExtra("pass", pass)
                        putExtra("Nombre", nombre)
                    }
                    startActivity(intent)
                }
            }
        }

        imageView7.setOnClickListener{
            val intent = Intent(this, Tienda::class.java).apply {
                putExtra("email", email)
                putExtra("pass", pass)
                putExtra("Nombre", nombre)
            }
            startActivity(intent)
        }

        imageView8.setOnClickListener{
            val intent = Intent(this, listaChat::class.java).apply {
                putExtra("email", email)
                putExtra("pass", pass)
                putExtra("Nombre", nombre)
                putExtra("NombreNegocio",nombreNegocio)
            }
            startActivity(intent)
        }

        imageView10.setOnClickListener{
            var usuario: String = ""
            var docRef =  db.collection("user").document(email.toString())
            var RefUserGet = db.collection("user").document(email.toString())
            docRef.get().addOnSuccessListener{ document ->
                if(document.exists()){
                    usuario = document.data?.get("Usuario").toString()
                    if(usuario == "Cliente y Vendedor"){
                        RefUserGet.get().addOnSuccessListener{ document ->
                        val intent = Intent(this, Inicio::class.java).apply {
                            putExtra("email", email)
                            putExtra("pass", pass)
                            putExtra("Nombre", nombre)
                                if (document.exists()){
                                        nombreNegocio = document.data?.get("Nombre del Negocio").toString()
                                        putExtra("NombreNegocio",nombreNegocio)
                                }
                        }
                            startActivity(intent)
                        }

                    }
                    else{
                        val intent = Intent(this, Registro::class.java).apply {
                            putExtra("email", email)
                            putExtra("pass", pass)
                            putExtra("Nombre", nombre)
                        }
                        startActivity(intent)
                    }
                    //Toast.makeText(this, "Usuario de tipo "+usuario, Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, InicioCliente::class.java)
                    startActivity(intent)
                }
            }
        }

        imageView11.setOnClickListener{
            val intent = Intent(this, Setting2::class.java).apply {
                putExtra("email", email)
                putExtra("pass", pass)
                putExtra("Nombre", nombre)
            }
            startActivity(intent)
        }

        imageView12.setOnClickListener{
            val intent = Intent(this, preguntas::class.java).apply {
                putExtra("email", email)
                putExtra("pass", pass)
                putExtra("Nombre", nombre)
            }
            startActivity(intent)
        }

        imageView13.setOnClickListener{
            val intent = Intent(this, ServicioCliente::class.java).apply {
                putExtra("email", email)
                putExtra("pass", pass)
                putExtra("Nombre", nombre)
            }
            startActivity(intent)
        }

        imageView14.setOnClickListener{
            val intent = Intent(this, Pedidos::class.java).apply {
                putExtra("email", email)
                putExtra("pass", pass)
                putExtra("Nombre", nombre)
            }
            startActivity(intent)
        }

        imageView53.setOnClickListener{
            //Log Out
            finish()
        }

        imageView45.setOnClickListener{
            var docRef =  db.collection("user").document(email.toString())
            docRef.get().addOnSuccessListener { document ->
                if(document.exists()){
                    nombreNegocio = document.data?.get("Nombre del Negocio").toString()
                    val intent = Intent(this, Carrito::class.java).apply {
                        putExtra("email", email)
                        putExtra("pass", pass)
                        putExtra("Nombre",nombre)
                    }
                    startActivity(intent)
                }
            }
        }

    }
}