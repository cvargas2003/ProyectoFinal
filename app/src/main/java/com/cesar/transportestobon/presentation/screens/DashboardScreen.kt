package com.cesar.transportestobon.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cesar.transportestobon.R
import com.cesar.transportestobon.presentation.navigation.Routes
import com.cesar.transportestobon.utils.NotificationHelper

data class DashboardItem(
    val titulo: String,
    val ruta: String,
    val icono: ImageVector
)

@Composable
fun DashboardScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val opciones = listOf(
        DashboardItem(
            stringResource(R.string.clientes),
            Routes.CLIENTES,
            Icons.Default.Person
        ),
        DashboardItem(
            stringResource(R.string.empleados),
            Routes.EMPLEADOS,
            Icons.Default.AccountCircle   // reemplaza Badge
        ),
        DashboardItem(
            stringResource(R.string.vehiculos),
            Routes.VEHICULOS,
            Icons.Default.Check        // reemplaza DirectionsBus
        ),
        DashboardItem(
            stringResource(R.string.pedidos),
            Routes.PEDIDOS,
            Icons.Default.List            // reemplaza Assignment
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        // Encabezado
        Text(
            text = stringResource(R.string.dashboard_title),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Transportes Tobón",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.55f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de notificación
        OutlinedButton(
            onClick = {
                NotificationHelper.mostrarNotificacion(
                    context,
                    context.getString(R.string.notificacion_titulo),
                    context.getString(R.string.notificacion_mensaje)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.probar_notificacion),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                )
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Módulos",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Grid de tarjetas
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(opciones) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(item.ruta) },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = item.icono,
                            contentDescription = item.titulo,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = item.titulo,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}