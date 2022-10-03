package com.example.examen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_agregar_producto.*

class AgregarProducto : AppCompatActivity() {
        private val db = FirebaseFirestore.getInstance()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_agregar_clientes2)

            val objetoIntent: Intent =intent
            var email=objetoIntent.getStringExtra("email")
            //val pass =objetoIntent.getStringExtra("pass")

            btnAgregarProduct.setOnClickListener {
                db.collection("user").document(email.toString()).collection("Productos").
                document(Producto.text.toString()).set(
                    hashMapOf("Producto" to Producto.text.toString(),
                        "Descripcion" to Descripcion.text.toString(),
                        "PrecioVenta" to PrecioVenta.text.toString(),
                        "PrecioCosto" to PrecioCosto.text.toString(),
                        "url" to FotoUrl.text.toString(),
                        "stock" to Stock.text.toString(),
                        "doc" to Producto.text.toString()
                    )
                )

                Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
}