package com.example.tulista.screens

import android.R.attr.fontWeight
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tulista.R
import com.example.tulista.data.ListViewModel
import com.example.tulista.ui.theme.contendorPrincipal
import com.example.tulista.ui.theme.contendorPrincipal2
import com.example.tulista.ui.theme.letras


@Composable
fun HomeScreen(
    navController: NavHostController, viewModel: ListViewModel
){
    Column(
        modifier = Modifier
            .fillMaxSize().background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Card(
          modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 24.dp, horizontal = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {

                Text(
                    text = "¡Bienvenidos a TuLista \uD83D\uDCC4!",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight
                        .Bold

                )
                Text(
                    text = "¡Hola Familia \uD83D\uDC4B!",
                    color = Color.Black,
                    fontSize = 35.sp,
                    fontWeight = FontWeight
                        .Bold

                )

                Text(
                    text = "¿Qué vamos a comprar hoy? \uD83D\uDED2",
                    fontSize = 23.sp,
                    color = Color.Black

                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.logotipos_comercios),
            contentDescription = "Logotipos",
            modifier = Modifier.size(350.dp)
        )
    }
}


