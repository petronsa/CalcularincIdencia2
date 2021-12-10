package com.petron.calcularincidencia

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.petron.calcularincidencia.databinding.FragmentUnoBinding



class NavegacionFragment : Fragment() {
    private var _binding: FragmentUnoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUnoBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        binding.textViewCienMil.visibility = View.GONE
        binding.botonCalcular.setOnClickListener {incidenciaAcumulada()}

        return binding.root
    }
    private fun incidenciaAcumulada(){
        if (binding.editTextNumberContagios.text.isEmpty()||binding.editTextNumberPoblacion.text.isEmpty()){
            Toast.makeText(activity,(R.string.fallo),Toast.LENGTH_SHORT).show()
        }else {
            val numeroContagios =
                Integer.parseInt(binding.editTextNumberContagios.getText().toString()).toDouble()
            val poblacion = Integer.parseInt(binding.editTextNumberPoblacion.getText().toString())
            val calcular: Double = (numeroContagios / poblacion) * 100000
            val calcularformat = (String.format("%.2f", calcular))
            binding.textViewResultados.setText(calcularformat)
            binding.textViewCienMil.visibility = View.VISIBLE

            // Esconder el teclado al dar al botón calcular
            hideKeyboard()
        }

    }
    // Empieza el proceso para quitar el teclado
    //Primera función para fragmentos
    fun NavegacionFragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }
    //Esta para activities
    fun MainActivity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }
    //Esta sera el proceso ahora no la entiedo
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.opciones_menu, menu)
    }

    // Funciones del menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            // Llamada a compartir haber como exporto la variable que quiero enviar
            R.id.compartir -> {
                // Sacamos el dato de incidencia de textView
                val envio = binding.textViewResultados.getText()
                //Sacamos el texto que acompaña al numero de incidencia
                val cienMil = binding.textViewCienMil.getText()

                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    // Mandamos un texto y dentro la varible con la incidencia acumulada
                    putExtra(Intent.EXTRA_TEXT, "$envio $cienMil" )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            /*Aqui podemos añadir más elementos del menu con una función especia
            como por ejemplo finalizar la aplicación
                    // Llamada al boton salir
                    R.id.salir -> {
                finish()
            }*/

        }
        // Desde aqui por ejemplo llamamos about y cerramos la función
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) ||super.onOptionsItemSelected(item)
    }




}