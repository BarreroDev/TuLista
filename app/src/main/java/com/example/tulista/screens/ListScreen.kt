package com.example.tulista.screens

import android.R.attr.name
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tulista.data.ListViewModel
import com.example.tulista.ui.theme.añadirLinea


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ListScreen(navController: NavController, viewModel: ListViewModel){

    // Variables para recordar lo que escribe y elige el usuario
    var comercioSeleccionado by remember { mutableStateOf("Mercadona") }
    var categoriaSelecionada by remember { mutableStateOf("Carnicería") }
    var nombreProducto by remember { mutableStateOf("") }

    // Variable para los campos que empezarán con 4
    var camposProductos = remember { mutableStateListOf("", "", "") }

    //Listas para los campos desplegables
    var comercios =listOf("Mercadona", "Carrefour", "Lidl", "Aldi", "Dia", "Cash-Fresh", "El Jamón",
        "Primaprix", "Plaza de Abastos", "Action", "Rossman", "SuperCarmela", "Otros")

    var categorias =listOf("Carnicería", "Pescadería", "Panadería (Varios)", "Charcutería", "Frutería",
        "Verduras", "Legumbres", "Lácteos", "Pastas", "Dulces", "Bebidas", "Salsas", "Especial",
        "Congelados", "Nutrción-Deportiva", "Higiene-Personal", "Limpieza-Hogar", "Hogar (Menajes)", "Ferretería (Derivados)",
        "Tecnología", "Ropa", "Mascotas", " Jugetes", "Papelería", "Varios")

    // Estados para los desplgables
    var numComercioExpansor by remember { mutableStateOf(false) }
    var numCategoriaExpansor by remember { mutableStateOf(false) }


    // Una columna que tenga para hacer scroll para cuando tenga muchos campos nuevos añadidos
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),

    ) {
        Text(
            text = "Crear lista ($comercioSeleccionado)",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )


        //Primer desplegable que contiene los comercios.
        ExposedDropdownMenuBox(
            expanded = numComercioExpansor,
            onExpandedChange = {numComercioExpansor = !numComercioExpansor}
        ) {
            OutlinedTextField(
                value = comercioSeleccionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecciona Comercio:") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = numComercioExpansor)},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black
                )
            )
            ExposedDropdownMenu(
                expanded = numComercioExpansor,
                onDismissRequest = {numComercioExpansor = false},
                modifier = Modifier
                    .background(Color.White)
            ) {
                comercios.forEach { comercio ->
                    DropdownMenuItem(
                        text = {Text(comercio)},
                        onClick = {
                            comercioSeleccionado = comercio
                            numComercioExpansor = false
                        }
                    )
                }
            }
        }


        //Segundo desplegable que contiene los categorías.
        ExposedDropdownMenuBox(
            expanded = numCategoriaExpansor,
            onExpandedChange = {numCategoriaExpansor = !numCategoriaExpansor}
        ) {
            OutlinedTextField(

                value = categoriaSelecionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecciona Categoria:") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = numCategoriaExpansor)},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black
                )
            )
            ExposedDropdownMenu(
                expanded = numCategoriaExpansor,
                onDismissRequest = {numCategoriaExpansor = false},
                modifier = Modifier
                    .background(Color.White)
            ) {
                categorias.forEach { categoria ->
                    DropdownMenuItem(
                        text = {Text(categoria)},
                        onClick = {
                            categoriaSelecionada = categoria
                            numCategoriaExpansor = false
                        }

                    )
                }
            }
        }

        //Campo texto dinamico
        Text(
            text = "Productos:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.Start)
        )

        //Bucle para que se creen nuevos campos
        camposProductos.forEachIndexed{ indice, producto ->
            OutlinedTextField(
                value = producto,
                onValueChange = {nuevoTexto ->
                    camposProductos[indice] = nuevoTexto
                },
                label = {Text("Producto ${indice + 1}")},
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black
                )
            )
        }
        //Boton para añadir campos de productos
        Button(
            onClick = { camposProductos.add("") },
            colors = ButtonDefaults.buttonColors(
                containerColor = añadirLinea,

            ),
            modifier = Modifier.align(Alignment.End)
        ) {

            Text("Añadir línea", color = Color.Black)

        }

        //Boton de guardado
        Button(
            colors = ButtonDefaults.buttonColors(
               containerColor = Color.Black
            ),
            onClick = {
                camposProductos.forEach { textoProducto ->

                    if( textoProducto.isNotBlank()){
                        viewModel.addProducto(
                            name = textoProducto,
                            store = comercioSeleccionado,
                            department = categoriaSelecionada
                        )
                    }
                }
                camposProductos.clear()
                camposProductos.addAll(listOf("","",""))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)

        ) {
            Text("Guardar Productos",
                fontSize = 18.sp)
        }

    }
}