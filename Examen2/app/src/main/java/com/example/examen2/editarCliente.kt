package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_editar_cliente.*
import java.util.*
import kotlin.collections.HashMap

class editarCliente : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cliente)

        val objetoIntent: Intent =intent
        var email=objetoIntent.getStringExtra("email")
        var pass =objetoIntent.getStringExtra("pass")
        val nombre = objetoIntent.getStringExtra("nombre")
        //val id = objetoIntent.getStringExtra("id")

        val hashMap:HashMap<String,String> = HashMap<String,String>()

        Toast.makeText(this, nombre.toString(), Toast.LENGTH_SHORT).show()
        //Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()
        db.collection("Vendedores").document(email.toString()).collection("clientes")
            .document(nombre.toString())
            .get().addOnSuccessListener{
                nombreClienteEditar.setText(it.get("Nombre")as String?)
                clienteDirrecionEditar.setText(it.get("Dirrecion")as String?)
                clienteTelefonoEditar.setText(it.get("Telefono")as String?)
                clienteCorreoEditar.setText(it.get("Correo")as String?)
            }

        btnEditar2.setOnClickListener {
            eliminar(email.toString(), nombre.toString())

            db.collection("Vendedores").document(email.toString()).collection("clientes").
            document(nombreClienteEditar.text.toString()).set(
                hashMapOf("Nombre" to nombreClienteEditar.text.toString(),
                    "Dirrecion" to clienteDirrecionEditar.text.toString(),
                    "Correo" to clienteCorreoEditar.text.toString(),
                    "Telefono" to clienteTelefonoEditar.text.toString()
                )
            )
        }
        btnEliminar.setOnClickListener {
            db.collection("user").document(email.toString()).collection("clientes").
            document(nombre.toString()).delete()
            finish()
        }
    }
    private fun eliminar(email:String, nombre:String){
        db.collection("user").document(email.toString()).collection("clientes")
            .document(nombre.toString()).delete()
    }
}