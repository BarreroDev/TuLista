package com.example.tulista.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class ListViewModel : ViewModel() {

    //Inicializamos la instancia con Firestore
    private val db = FirebaseFirestore.getInstance()

    //Creamos la colección productos
    private val productosRef = db.collection("productos")

    //lista reactiva
    private val _productos = mutableStateListOf<Productos>()
    val productos: List<Productos> get() = _productos

    //nos conectamos a Firebase para escuchar los cambios en tiempo real
    init {
        escucharProductosEnTiempoReal()
    }

    // Escuchador cada vez que Firebase cambie, actualiza tu lista _productos
    private fun escucharProductosEnTiempoReal() {
        productosRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (snapshot != null) {
                _productos.clear()

                for (documento in snapshot.documents) {
                    val prod = documento.toObject<Productos>()
                    if (prod != null) {
                        _productos.add(prod)
                    }
                }
            }
        }
    }

    // función para añadir productos
    fun addProducto(name: String, store: String, department: String) {
        if (name.isNotBlank()) {
            val nuevoProducto = Productos(
                name = name,
                store = store,
                department = department
            )
            productosRef.document(nuevoProducto.id).set(nuevoProducto)
        }
    }

    //función para tachar y destachar
    fun tacharProducto(producto: Productos) {
        productosRef.document(producto.id)
            .update("isChecked", !producto.isChecked)
    }

    //función para borrar un producto concreto
    fun borrarProducto(producto: Productos) {
        productosRef.document(producto.id).delete()
    }

    //función para vaciar la lista por completo
    fun limpiarLista() {
        // En Firestore hay que borrar los documentos uno a uno
        for (producto in _productos) {
            productosRef.document(producto.id).delete()
        }
    }

    //función para borrar los productos de un comercio concreto
    fun borrarProductosPorComercio(comercio: String) {
        // Buscamos a nivel local cuáles coinciden y los borramos de la nube
        _productos.forEach { producto ->
            if (producto.store == comercio) {
                productosRef.document(producto.id).delete()
            }
        }
    }
}