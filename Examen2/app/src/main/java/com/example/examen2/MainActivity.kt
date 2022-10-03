package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.tv8

class MainActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        actionBar?.hide()

        var nombre : String = ""
        var email : String = ""
        var contra : String = ""

        registro.setOnClickListener {
            val intent = Intent(this, Registro2::class.java)
            startActivity(intent)

    }

        iniciar.setOnClickListener{
            var docRef =  db.collection("user").document(et1.text.toString())
            docRef.get().addOnSuccessListener{ document ->
                if(document.exists()){
                    email = document.data?.get("Correo").toString()
                    contra = document.data?.get("Contraseña").toString()
                    nombre = document.data?.get("Nombre").toString()
                    if(email == et1.text.toString() && contra == et2.text.toString()){
                        Toast.makeText(this, "iniciado", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, InicioCliente::class.java).apply {
                            putExtra("email", et1.text.toString())
                            putExtra("pass", et2.text.toString())
                            putExtra("Nombre", nombre)
                        }
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}