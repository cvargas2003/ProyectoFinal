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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cesar.transportestobon.data.model.Pedido
import com.cesar.transportestobon.utils.NotificationHelper
import com.cesar.transportestobon.viewmodel.ClienteViewModel
import com.cesar.transportestobon.viewmodel.EmpleadoViewModel
import com.cesar.transportestobon.viewmodel.PedidoViewModel
import com.cesar.transportestobon.viewmodel.VehiculoViewModel

@Composable
fun PedidosScreen(
    pedidoViewModel: PedidoViewModel = viewModel(),
    clienteViewModel: ClienteViewModel = viewModel(),
    vehiculoViewModel: VehiculoViewModel = viewModel(),
    empleadoViewModel: EmpleadoViewModel = viewModel()
) {
    val context = LocalContext.current
    var clienteId by remember { mutableStateOf("") }
    var clienteNombre by remember { mutableStateOf("") }
    var vehiculoId by remember { mutableStateOf("") }
    var vehiculoPlaca by remember { mutableStateOf("") }
    var conductorId by remember { mutableStateOf("") }
    var ayudanteId by remember { mutableStateOf("") }
    var direccionEntrega by remember { mutableStateOf("") }
    var kilometros by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var pedidoEditandoId by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        pedidoViewModel.obtenerPedidos()
        clienteViewModel.obtenerClientes()
        vehiculoViewModel.obtenerVehiculos()
        empleadoViewModel.obtenerEmpleados()
    }

    val clientes = clienteViewModel.clientes
    val vehiculos = vehiculoViewModel.vehiculos
    val editando = pedidoEditandoId.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        Text(
            text = "Gestión de Pedidos",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = if (editando) "Editando pedido" else "Registro de pedidos",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.55f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = if (editando) "Actualizar datos" else "Nuevo pedido",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Selector de cliente
                Text(
                    text = "Cliente",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (clienteNombre.isNotBlank()) {
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
                                text = clienteNombre,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }

                if (clientes.isEmpty()) {
                    Text(
                        text = "No hay clientes registrados",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        clientes.forEach { cliente ->
                            FilterChip(
                                selected = clienteId == cliente.id,
                                onClick = {
                                    clienteId = cliente.id
                                    clienteNombre = cliente.nombre
                                },
                                label = {
                                    Text(
                                        cliente.nombre,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Selector de vehículo
                Text(
                    text = "Vehículo",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (vehiculoPlaca.isNotBlank()) {
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
                                Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = vehiculoPlaca,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }

                if (vehiculos.isEmpty()) {
                    Text(
                        text = "No hay vehículos registrados",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        vehiculos.forEach { vehiculo ->
                            FilterChip(
                                selected = vehiculoId == vehiculo.id,
                                onClick = {
                                    vehiculoId = vehiculo.id
                                    vehiculoPlaca = vehiculo.placa
                                    conductorId = vehiculo.conductorId
                                    ayudanteId = vehiculo.ayudanteId
                                },
                                label = {
                                    Text(
                                        vehiculo.placa,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = direccionEntrega,
                    onValueChange = { direccionEntrega = it },
                    label = { Text("Dirección de entrega *") },
                    leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = kilometros,
                    onValueChange = {
                        kilometros = it
                        val km = it.toDoubleOrNull() ?: 0.0
                        valor = (km * 2500).toString()
                    },
                    label = { Text("Kilómetros *") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = valor,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Valor calculado") },
                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val pedido = Pedido(
                            id = pedidoEditandoId,
                            clienteId = clienteId,
                            vehiculoId = vehiculoId,
                            conductorId = conductorId,
                            ayudanteId = ayudanteId,
                            direccionEntrega = direccionEntrega,
                            kilometros = kilometros,
                            valor = valor,
                            estado = "Pendiente"
                        )

                        if (pedidoEditandoId.isEmpty()) {
                            pedidoViewModel.guardarPedido(
                                pedido,
                                onSuccess = {
                                    NotificationHelper.mostrarNotificacion(
                                        context,
                                        "Pedido guardado",
                                        "El pedido de $clienteNombre fue registrado correctamente"
                                    )
                                    mensaje = "Pedido guardado"
                                    clienteId = ""; clienteNombre = ""
                                    vehiculoId = ""; vehiculoPlaca = ""
                                    conductorId = ""; ayudanteId = ""
                                    direccionEntrega = ""; kilometros = ""; valor = ""
                                    pedidoViewModel.obtenerPedidos()
                                },
                                onError = { mensaje = it }
                            )
                        } else {
                            pedidoViewModel.actualizarPedido(
                                pedido,
                                onSuccess = {
                                    NotificationHelper.mostrarNotificacion(
                                        context,
                                        "Pedido actualizado",
                                        "El pedido de $clienteNombre fue actualizado correctamente"
                                    )
                                    mensaje = "Pedido actualizado"
                                    pedidoEditandoId = ""
                                    clienteId = ""; clienteNombre = ""
                                    vehiculoId = ""; vehiculoPlaca = ""
                                    conductorId = ""; ayudanteId = ""
                                    direccionEntrega = ""; kilometros = ""; valor = ""
                                    pedidoViewModel.obtenerPedidos()
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
                        text = if (editando) "Actualizar Pedido" else "Guardar Pedido",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

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
            text = "Pedidos registrados",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(pedidoViewModel.pedidos) { pedido ->

                val cliente = clienteViewModel.clientes.find { it.id == pedido.clienteId }
                val vehiculo = vehiculoViewModel.vehiculos.find { it.id == pedido.vehiculoId }
                val conductor = empleadoViewModel.empleados.find { it.id == pedido.conductorId }
                val ayudante = empleadoViewModel.empleados.find { it.id == pedido.ayudanteId }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = cliente?.nombre ?: "Cliente desconocido",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            ) {
                                Text(
                                    text = pedido.estado,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                        }

                        Text(
                            text = "Vehículo: ${vehiculo?.placa ?: "Sin asignar"}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant,
                            thickness = 0.5.dp
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = pedido.direccionEntrega,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${pedido.kilometros} km",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "$ ${pedido.valor}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.secondary
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
                                    pedidoEditandoId = pedido.id
                                    clienteId = pedido.clienteId
                                    clienteNombre = cliente?.nombre ?: ""
                                    vehiculoId = pedido.vehiculoId
                                    vehiculoPlaca = vehiculo?.placa ?: ""
                                    conductorId = pedido.conductorId
                                    ayudanteId = pedido.ayudanteId
                                    direccionEntrega = pedido.direccionEntrega
                                    kilometros = pedido.kilometros
                                    valor = pedido.valor
                                    mensaje = "Editando pedido"
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
                                    pedidoViewModel.eliminarPedido(
                                        pedido.id,
                                        onSuccess = {
                                            NotificationHelper.mostrarNotificacion(
                                                context,
                                                "Pedido eliminado",
                                                "El pedido fue eliminado correctamente"
                                            )
                                            mensaje = "Pedido eliminado"
                                            pedidoViewModel.obtenerPedidos()
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