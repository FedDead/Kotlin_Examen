package com.example.examen2

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat.*

class chat : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        //Ocultar la barra de acciones
        val actionBar = supportActionBar
        //Acultar la barra de progreso
        actionBar?.hide()

        val objetoIntent: Intent =intent
        var email=objetoIntent.getStringExtra("email")
        var nombre=objetoIntent.getStringExtra("nombre")
        var nombreNegocio = objetoIntent.getStringExtra("NombreNegocio")
        Toast.makeText(this, nombreNegocio.toString(), Toast.LENGTH_SHORT).show()

        var text =""
        var texto=""
        var texto2=""
        var sinEditar =""




        //Toast.makeText(this, cont.toString(), Toast.LENGTH_SHORT).show()

        refresChat.setOnClickListener {
            db.collection("chat").document(nombreNegocio.toString()).collection("clientes")
                .document(nombreNegocio.toString())
                .get().addOnSuccessListener{
                    texto=(it.get("chat"))as String
                    sinEditar=(it.get("chat2"))as String

                }
            texto=sinEditar
            texto2=texto.replace("dat33"," \n ")
            TodoChat.setText(texto2)
        }

        deleteChat.setOnClickListener{
            db.collection("chat").document(nombreNegocio.toString())
                .collection("clientes").document(nombreNegocio.toString()).delete()
        }

        PreguntarChatbtn.setOnClickListener {
            text=TodoChat.text.toString()+ " dat33 " +TextChat.text.toString()
            sinEditar=sinEditar.toString()+ " dat33 " +TextChat.text.toString()

        db.collection("chat").document(nombreNegocio.toString()).collection("clientes").
        document(nombreNegocio.toString()).set(
            hashMapOf(
                "chat" to text.toString(),
                "chat2" to sinEditar.toString()
            )
        )

        }
    }
}