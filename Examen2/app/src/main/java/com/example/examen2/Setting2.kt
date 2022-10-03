package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.activity_registro2.*
import kotlinx.android.synthetic.main.activity_registro2.correo
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_setting2.*


class Setting2 : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting2)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val bundle=intent.extras
        val email=bundle?.getString("email")
        val pass = bundle?.getString("pass")
        var Usuario : String = ""
        var nombreNegocio : String =  ""
        var refUser =  db.collection("user").document(email.toString())

        db.collection("user").document(email.toString()).get().addOnSuccessListener {
                nombreCompleto4.setText(it.get("Nombre")as String?)
                municipio4.setText(it.get("Municipio")as String?)
                colonia4.setText(it.get("Colonia")as String?)
                calle4.setText(it.get("Calle")as String?)
                telefono4.setText(it.get("Telefono")as String?)
                ife4.setText(it.get("IFE")as String?)
                correo4.setText(email)
                pass4.setText(pass)
            }

        btnEditar4.setOnClickListener(){
            db.collection("user").document(email.toString()).get().addOnSuccessListener { document ->
                if (document.exists()){
                    Usuario = document.data?.get("Usuario").toString()
                    if (Usuario == "Cliente"){
                        db.collection("user").document(email.toString()).set(
                            hashMapOf(
                                "Nombre" to nombreCompleto4.text.toString(),
                                "Municipio" to municipio4.text.toString(),
                                "Colonia" to colonia4.text.toString(),
                                "Calle" to calle4.text.toString(),
                                "Telefono" to telefono4.text.toString(),
                                "Correo" to correo4.text.toString(),
                                "Contraseña" to pass4.text.toString(),
                                "IFE" to ife4.text.toString(),
                                "Usuario" to "Cliente"
                            )
                        )
                    }
                    if (Usuario == "Cliente y Vendedor"){
                        refUser.get().addOnSuccessListener { document ->
                            nombreNegocio = document.data?.get("Nombre del Negocio").toString()
                            db.collection("user").document(email.toString()).set(
                                hashMapOf(
                                    "Nombre" to nombreCompleto4.text.toString(),
                                    "Municipio" to municipio4.text.toString(),
                                    "Colonia" to colonia4.text.toString(),
                                    "Calle" to calle4.text.toString(),
                                    "Telefono" to telefono4.text.toString(),
                                    "Correo" to correo4.text.toString(),
                                    "Contraseña" to pass4.text.toString(),
                                    "IFE" to ife4.text.toString(),
                                    "Usuario" to "Cliente y Vendedor",
                                    "Nombre del Negocio" to nombreNegocio
                                )
                            )
                        }
                    }
                }
            }
            finish()
            Toast.makeText(this, "Editado", Toast.LENGTH_SHORT).show()
        }
    }
}