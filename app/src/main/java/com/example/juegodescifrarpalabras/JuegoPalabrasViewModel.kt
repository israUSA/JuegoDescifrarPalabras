package com.example.juegodescifrarpalabras

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.juegodescifrarpalabras.datasource.INCREMENTO_PUNTOS
import com.example.juegodescifrarpalabras.datasource.MAX_PALABRAS
import com.example.juegodescifrarpalabras.datasource.listaPalabras

class JuegoPalabrasViewModel: ViewModel() {


    private val _puntos = MutableLiveData<Int>(0)
    val puntos: LiveData<Int>
    get() =_puntos

    private val _numeroPalabras = MutableLiveData<Int>(1)
    val numeroPalabras: LiveData<Int>
    get() = _numeroPalabras

    private val _palabraActualOrdenada = MutableLiveData<String>()
    val palabraActualOrdenada: LiveData<String>
    get() = _palabraActualOrdenada

    private val _imagenActual = MutableLiveData<Int>()
    val imagenActual: LiveData<Int>
    get() = _imagenActual

    private val _palabraActualDesordenada = MutableLiveData<String>()
    val palabraActualDesordenada: LiveData<String>
    get() = _palabraActualDesordenada



    private var listaPalabrasHistorial: MutableList<Pair<String, Int>> = mutableListOf()

    init {
        println("ViewModel Iniciado")
        siguientePalabra()
    }

    private fun siguientePalabra(){

        val par = listaPalabras.random()
        if (!listaPalabrasHistorial.contains(par)){
            listaPalabrasHistorial.add(par)
            _palabraActualOrdenada.value = par.first!!
            _imagenActual.value = par.second!!
            _palabraActualDesordenada.value = desordenarPalabra(_palabraActualOrdenada.value)

            while (_palabraActualDesordenada.value.equals(_palabraActualOrdenada.value, false)){
                println("La palabra desordenada es igual a la ordenada, desordenando de nuevo.....")
                _palabraActualDesordenada.value = desordenarPalabra(_palabraActualOrdenada.value)
            }

        } else{
            println("Esta palabra ya esta en la lista, por ende ya fue evaluada")
            siguientePalabra()
        }
    }


    private fun desordenarPalabra(palabraOrdenada: String?): String{
        val temporal = palabraOrdenada?.toCharArray()
        temporal?.shuffle()
        return String(temporal!!)
    }

    fun isPalabraCorrecta(palabra: String): Boolean{
        if (palabra.equals(palabraActualOrdenada.value, true)){
            incrementarPuntos()

            return true
        }

        else return false
    }

    fun verificarMaximoPalabra(): Boolean{
        return if (numeroPalabras.value!! < MAX_PALABRAS){
            incrementarContadorPalabras()
            true
        }
        else false
    }


    private fun incrementarPuntos(){
        _puntos.value = _puntos.value?.plus(INCREMENTO_PUNTOS)
        siguientePalabra()
    }

    private fun incrementarContadorPalabras(){
        _numeroPalabras.value = _numeroPalabras.value?.inc()
    }

    fun reinicializarDatos(){
        _puntos.value = 0
        _numeroPalabras.value = 0
        listaPalabrasHistorial.clear()
        siguientePalabra()
    }



}