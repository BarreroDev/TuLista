package com.example.tulista.data

import android.R
import java.util.UUID

data class Productos(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val store: String = "",
    val department: String = "",
    var isChecked: Boolean = false
)