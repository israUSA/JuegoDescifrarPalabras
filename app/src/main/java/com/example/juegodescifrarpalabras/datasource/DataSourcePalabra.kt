package com.example.juegodescifrarpalabras.datasource

import android.graphics.drawable.Drawable
import com.example.juegodescifrarpalabras.R

const val MAX_PALABRAS: Int = 5
const val INCREMENTO_PUNTOS = 2

val listaPalabras: List<Pair<String, Int>> = listOf(
        Pair("agua", R.drawable.agua),
        Pair("arroz", R.drawable.arroz),
        Pair("adobe",R.drawable.adobe),
        Pair("mar", R.drawable.mar ),
        Pair("mano",R.drawable.mano),
        Pair("música",R.drawable.musica),
        Pair("oso",R.drawable.oso),
        Pair("oro",R.drawable.oro),
        Pair("ornitorrinco",R.drawable.ornitorrinco),
        Pair("perro",R.drawable.perro),
        Pair("palmera",R.drawable.palmera),
        Pair("pintura",R.drawable.pintura),
)