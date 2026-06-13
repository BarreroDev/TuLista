package com.example.tulista.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
//import com.google.firebase.firestore.FirebaseFirestore

class ListViewModel : ViewModel(){
    // Conexión directa a la base de datos
    //private val db: FirebaseFirestore = Firebase.firestore

    //Lista reactiva que se leerá desde Compose
    private val _productos = mutableStateListOf<Productos>()
    val productos: List<Productos> get() = _productos


    // Funcion para añadir productos
    fun addProducto(name: String, store: String, department: String){
        if (name.isNotBlank()){

            val nuevoProducto = Productos(
                name = name,
                store = store,
                department = department
            )
            _productos.add(nuevoProducto)
        }
    }

    // Funcion para tachar y destachar un producto
    fun tacharProducto(producto: Productos){
        val index = _productos.indexOfFirst { it.id == producto.id }

        if (index != -1){
            _productos[index] = _productos[index].copy(isChecked = !producto.isChecked)
        }
    }

    // Función para borrar un producto concreto

    fun borrarProducto(producto: Productos){
        _productos.removeIf { it.id == producto.id }
    }

    // Función para vaciar la lista
    fun limpiarLista(){
        _productos.clear()
    }

    // Funcioón para borrar producto
    fun borrarProductosPorComercio(comercio: String) {
        _productos.removeIf { it.store == comercio }
    }
}