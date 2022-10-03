package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.activity_registro2.*
import kotlinx.android.synthetic.main.activity_registro2.btnRegistro
import kotlinx.android.synthetic.main.activity_registro2.calle1
import kotlinx.android.synthetic.main.activity_registro2.colonia1
import kotlinx.android.synthetic.main.activity_registro2.correo
import kotlinx.android.synthetic.main.activity_registro2.ife
import kotlinx.android.synthetic.main.activity_registro2.municipio1
import kotlinx.android.synthetic.main.activity_registro2.nombreCompleto1
import kotlinx.android.synthetic.main.activity_registro2.pass
import kotlinx.android.synthetic.main.activity_registro2.telefono1


class Registro2 : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro2)

        val actionBar = supportActionBar
        actionBar?.hide()

        btnRegistro.setOnClickListener() {
            if (correo.text.isNotEmpty() && pass.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    correo.text.toString(), pass.text.toString())
                Toast.makeText(this, "Registrado", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else {
                showAlert()
            }
            db.collection("user").document(correo.text.toString()).set(
                    hashMapOf(
                        "Nombre" to nombreCompleto1.text.toString(),
                        "Municipio" to municipio1.text.toString(),
                        "Colonia" to colonia1.text.toString(),
                        "Calle" to calle1.text.toString(),
                        "Telefono" to telefono1.text.toString(),
                        "Correo" to correo.text.toString(),
                        "Contraseña" to pass.text.toString(),
                        "IFE" to ife.text.toString(),
                        "Usuario" to "Cliente"
                    )
                )
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Error al registrar")
        builder.setPositiveButton("aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}