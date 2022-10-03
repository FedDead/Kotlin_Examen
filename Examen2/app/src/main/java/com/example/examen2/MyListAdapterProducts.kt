package com.example.examen2

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_carrito.*

class MyListAdapterProducts(private val context: Activity, private val nombres: ArrayList<String>, private val dirrecciones: ArrayList<String>,
                            private val contacto: ArrayList<String>, private val imagen: ArrayList<String>, private val iden:ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.custom_list, nombres) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val subtitleText = rowView.findViewById(R.id.description) as TextView
        val subtitleText2 = rowView.findViewById(R.id.Telefono) as TextView
        val ids = rowView.findViewById(R.id.ids) as TextView

        if (imagen[position].isEmpty()){
        Picasso.get().load(imagen[position]).error(R.drawable.nofound)
            .into(imageView)
        }
        else{
            Picasso.get().load(imagen[position]).error(R.drawable.nofoun)
                .into(imageView)
        }

        titleText.text = nombres[position]
        subtitleText.text = dirrecciones[position]
        subtitleText2.text = contacto[position]
        ids.text = iden[position]


        return rowView
    }
}