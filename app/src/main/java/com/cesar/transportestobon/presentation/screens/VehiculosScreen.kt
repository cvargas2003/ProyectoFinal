package com.cesar.transportestobon.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cesar.transportestobon.data.model.Vehiculo
import com.cesar.transportestobon.viewmodel.VehiculoViewModel
import com.cesar.transportestobon.viewmodel.EmpleadoViewModel

@Composable
fun VehiculosScreen(
    vehiculoViewModel: VehiculoViewModel = viewModel(),
    empleadoViewModel: EmpleadoViewModel = viewModel()
) {
    var placa by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var pesoMaximo by remember { mutableStateOf("") }
    var conductorId by remember { mutableStateOf("") }
    var conductorNombre by remember { mutableStateOf("") }
    var ayudanteId by remember { mutableStateOf("") }
    var ayudanteNombre by remember { mutableStateOf("") }
    var vehiculoEditandoId by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        vehiculoViewModel.obtenerVehiculos()
        empleadoViewModel.obtenerEmpleados()
    }

    val conductores = empleadoViewModel.empleados.filter { it.cargo.equals("Conductor", true) }
    val ayudantes = empleadoViewModel.empleados.filter { it.cargo.equals("Ayudante", true) }
    val editando = vehiculoEditandoId.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        // Encabezado
        Text(
            text = "Gestión de Vehículos",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = if (editando) "Editando vehículo" else "Registro de vehículos",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.55f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Formulario en Card
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = if (editando) "Actualizar datos" else "Nuevo vehículo",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = placa,
                    onValueChange = { placa = it },
                    label = { Text("Placa *") },
                    leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = marca,
                    onValueChange = { marca = it },
                    label = { Text("Marca *") },
                    leadingIcon = { Icon(Icons.Default.Build, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = color,
                    onValueChange = { color = it },
                    label = { Text("Color") },
                    leadingIcon = { Icon(Icons.Default.ArrowForward, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = pesoMaximo,
                    onValueChange = { pesoMaximo = it },
                    label = { Text("Peso Máximo") },
                    leadingIcon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Selector de conductor
                Text(
                    text = "Conductor asignado",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (conductorNombre.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = conductorNombre,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }

                if (conductores.isEmpty()) {
                    Text(
                        text = "No hay conductores registrados",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        conductores.forEach { conductor ->
                            FilterChip(
                                selected = conductorId == conductor.id,
                                onClick = {
                                    conductorId = conductor.id
                                    conductorNombre = conductor.nombre
                                },
                                label = {
                                    Text(
                                        conductor.nombre,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Selector de ayudante
                Text(
                    text = "Ayudante asignado",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (ayudanteNombre.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = ayudanteNombre,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }

                if (ayudantes.isEmpty()) {
                    Text(
                        text = "No hay ayudantes registrados",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ayudantes.forEach { ayudante ->
                            FilterChip(
                                selected = ayudanteId == ayudante.id,
                                onClick = {
                                    ayudanteId = ayudante.id
                                    ayudanteNombre = ayudante.nombre
                                },
                                label = {
                                    Text(
                                        ayudante.nombre,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val vehiculo = Vehiculo(
                            id = vehiculoEditandoId,
                            placa = placa,
                            marca = marca,
                            color = color,
                            pesoMaximo = pesoMaximo,
                            conductorId = conductorId,
                            ayudanteId = ayudanteId
                        )

                        if (vehiculoEditandoId.isEmpty()) {
                            vehiculoViewModel.guardarVehiculo(
                                vehiculo,
                                onSuccess = {
                                    mensaje = "Vehículo guardado"
                                    placa = ""; marca = ""; color = ""; pesoMaximo = ""
                                    conductorId = ""; conductorNombre = ""
                                    ayudanteId = ""; ayudanteNombre = ""
                                    vehiculoViewModel.obtenerVehiculos()
                                },
                                onError = { mensaje = it }
                            )
                        } else {
                            vehiculoViewModel.actualizarVehiculo(
                                vehiculo,
                                onSuccess = {
                                    mensaje = "Vehículo actualizado"
                                    vehiculoEditandoId = ""
                                    placa = ""; marca = ""; color = ""; pesoMaximo = ""
                                    conductorId = ""; conductorNombre = ""
                                    ayudanteId = ""; ayudanteNombre = ""
                                    vehiculoViewModel.obtenerVehiculos()
                                },
                                onError = { mensaje = it }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Icon(
                        imageVector = if (editando) Icons.Default.Edit else Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (editando) "Actualizar Vehículo" else "Guardar Vehículo",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Mensaje de estado
        if (mensaje.isNotBlank()) {
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = mensaje,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Vehículos registrados",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(vehiculoViewModel.vehiculos) { vehiculo ->

                val conductor = empleadoViewModel.empleados.find { it.id == vehiculo.conductorId }
                val ayudante = empleadoViewModel.empleados.find { it.id == vehiculo.ayudanteId }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {

                        // Placa destacada + marca
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = vehiculo.placa,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = vehiculo.marca,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }

                        Text(
                            text = "Color: ${vehiculo.color}  •  Peso máx: ${vehiculo.pesoMaximo}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant,
                            thickness = 0.5.dp
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        // Personal asignado
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = conductor?.nombre ?: "Sin conductor",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.AccountCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = ayudante?.nombre ?: "Sin ayudante",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = {
                                    vehiculoEditandoId = vehiculo.id
                                    placa = vehiculo.placa
                                    marca = vehiculo.marca
                                    color = vehiculo.color
                                    pesoMaximo = vehiculo.pesoMaximo
                                    conductorId = vehiculo.conductorId
                                    ayudanteId = vehiculo.ayudanteId
                                    conductorNombre = conductor?.nombre ?: ""
                                    ayudanteNombre = ayudante?.nombre ?: ""
                                    mensaje = "Editando vehículo"
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Editar", style = MaterialTheme.typography.bodySmall)
                            }

                            Button(
                                onClick = {
                                    vehiculoViewModel.eliminarVehiculo(
                                        vehiculo.id,
                                        onSuccess = {
                                            mensaje = "Vehículo eliminado"
                                            vehiculoViewModel.obtenerVehiculos()
                                        },
                                        onError = { mensaje = it }
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer,
                                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                                )
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Eliminar", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}