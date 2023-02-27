package com.example.juegodescifrarpalabras

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import com.example.juegodescifrarpalabras.databinding.FragmentJuegoPalabrasBinding
import com.example.juegodescifrarpalabras.datasource.MAX_PALABRAS
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class JuegoPalabrasFragment : Fragment() {

    private val viewModel: JuegoPalabrasViewModel by viewModels()
    private var _binding: FragmentJuegoPalabrasBinding?  = null
    val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJuegoPalabrasBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmar.setOnClickListener {confirmarPalabra() }
        binding.paso.setOnClickListener { paso() }

        viewModel.puntos.observe(viewLifecycleOwner, {newScore -> binding.tvPuntos.text = getString(R.string.puntos_valor, newScore)})
        viewModel.numeroPalabras.observe(viewLifecycleOwner,{newPalabraContador -> binding.tvcontadorPalabra.text = getString(R.string.contador_palabra, newPalabraContador, MAX_PALABRAS)})
        viewModel.palabraActualDesordenada.observe(viewLifecycleOwner, {newPalabra -> binding.tvPalabra.text = newPalabra})
        viewModel.imagenActual.observe(viewLifecycleOwner, {newImagen -> binding.imageWord.setImageResource(newImagen)})
    }

    private fun confirmarPalabra(){
        if(viewModel.isPalabraCorrecta(binding.textField.text.toString())){
            setErrorTextField(false)
            if(!viewModel.verificarMaximoPalabra()) mostrarDialogoFinal()
        }
        else setErrorTextField(true)
    }

    private fun paso(){
        if(!viewModel.verificarMaximoPalabra()) mostrarDialogoFinal()
        else setErrorTextField(false)
    }

    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textFieldLayout.isErrorEnabled = true
            binding.textFieldLayout.error = getString(R.string.vuelva_intentarlo)
        } else {
            binding.textFieldLayout.isErrorEnabled = false
            binding.textField.text = null
        }
    }

    private fun exitGame() {
        activity?.finish()
    }

    private fun restartGame() {
        viewModel.reinicializarDatos()
        setErrorTextField(false)
    }

    private fun mostrarDialogoFinal(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.finish))
            .setMessage(getString(R.string.puntaje, viewModel.puntos.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.salir)){_, _  ->
                exitGame()}
            .setPositiveButton(getString(R.string.intentar_nuevo)) {_, _ ->
                Log.d("holapepe", "en efecto restart game")
                restartGame()}
            .show()

    }



}