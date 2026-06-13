package com.example.tulista.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tulista.data.ListViewModel
import com.example.tulista.ui.theme.checkBox
import com.example.tulista.ui.theme.contendorPrincipal
import com.example.tulista.ui.theme.contendorPrincipal2
import com.example.tulista.ui.theme.contenedorLista
import com.example.tulista.ui.theme.letras

@Composable
fun CardScreen(navController: NavHostController, viewModel: ListViewModel) {

   // Llamamos al "almencen central"
    val listaProductos = viewModel.productos

    //Columna que aljo el interior de la vista CardScreen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mis cestas de la compra 🛒",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // ---Bloque condicionado para distintas acciones.---

        // Si la lista está vacía, mostramos un mensaje.
        if (listaProductos.isEmpty()) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "La cesta está vacía.\n¡Añade productos en el formulario! 📄",
                    fontSize = 21.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Variable para que cada tarjeta quede agrupada por comercio.
            val productosAgrupadosPorTienda = listaProductos.groupBy { it.store }

            // Usamos LazyColumn para que la lista sea súper fluida.
            LazyColumn(
                modifier = Modifier.weight(1f).padding(5.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                //Bucle paara organizar por comercio.
                productosAgrupadosPorTienda.forEach { (tienda, productosDeEstaTienda) ->
                    item {
                        // Creamos una única Tarjeta contenedora por comercio
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = contenedorLista
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {

                                // TÍTULO DEL COMERCIO
                                Text(
                                    text = tienda,
                                    textAlign = TextAlign.Center,
                                    textDecoration = TextDecoration.Underline,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = letras,
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .fillMaxWidth()
                                )

                                // Agrupa por deprtamentos dentro del mismo comercio
                                val productosPorCategoria = productosDeEstaTienda.groupBy { it.department }

                                productosPorCategoria.forEach { (categoria, listaProductosDeCategoria) ->

                                    // SUBTÍTULO DE LA CATEGORÍA
                                    Text(
                                        text = categoria,
                                        fontSize = 18.sp,
                                        textDecoration = TextDecoration.Underline,
                                        fontWeight = FontWeight.SemiBold,
                                        color = letras,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )

                                    // 4. Recorremos los productos que pertenecen exclusivamente a esta categoría
                                    listaProductosDeCategoria.forEach { producto ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 5.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Checkbox(
                                                checked = producto.isChecked,
                                                onCheckedChange = { viewModel.tacharProducto(producto) },
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = checkBox
                                                )
                                            )

                                            Text(
                                                text = producto.name,
                                                style = if (producto.isChecked) {
                                                    TextStyle(textDecoration = TextDecoration.LineThrough)
                                                } else {
                                                    TextStyle.Default
                                                },
                                                fontSize = 16.sp,
                                                modifier = Modifier.weight(1f)
                                            )

                                            IconButton(
                                                onClick = { viewModel.borrarProducto(producto) }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Eliminar Producto",
                                                    tint = MaterialTheme.colorScheme.error
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                }
                            }
                        }
                        // Botón de borardo general de ese comercio
                        Button(
                            onClick = { viewModel.borrarProductosPorComercio(tienda)},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = checkBox
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Vaciar lista $tienda", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

        }
    }
}