package com.petron.calcularincidencia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.petron.calcularincidencia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Variable cajon de navegacion (menu lateral)
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Necesario layout en el xml o no funciona
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        //Inicializamos el drawer_layout

        drawerLayout = binding.drawerLayout

        // controlador de navegación para agregar un botón atras Arriba a su aplicación
        val navController = this.findNavController(R.id.myNavHostFragment)

        NavigationUI.setupActionBarWithNavController(this,navController)
        //Para mostrar el panel de navegación (menu lateral)
        NavigationUI.setupWithNavController(binding.navView, navController)
        // Para mostrar el icono del drawer_layout
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

    }


    //método para llamar navigateUp()al controlador de navegación
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}