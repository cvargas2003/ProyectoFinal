package com.cesar.transportestobon.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cesar.transportestobon.data.model.Pedido
import com.cesar.transportestobon.viewmodel.PedidoViewModel

@Composable
fun PedidosScreen(
    pedidoViewModel: PedidoViewModel = viewModel()
) {

    var mensaje by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        pedidoViewModel.obtenerPedidos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = {

                val pedido = Pedido(
                    clienteId = "Cliente 1",
                    vehiculoId = "ABC123",
                    conductorId = "Juan Pérez",
                    ayudanteId = "Carlos Gómez",
                    direccionEntrega = "Bogotá",
                    kilometros = "15",
                    valor = "30000",
                    estado = "Pendiente"
                )

                pedidoViewModel.guardarPedido(
                    pedido,
                    onSuccess = {

                        mensaje = "Pedido guardado"

                        pedidoViewModel.obtenerPedidos()
                    },
                    onError = {
                        mensaje = it
                    }
                )
            }
        ) {
            Text("Guardar Pedido de Prueba")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(mensaje)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Pedidos registrados",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {

            items(pedidoViewModel.pedidos) { pedido ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text("Cliente: ${pedido.clienteId}")
                        Text("Vehículo: ${pedido.vehiculoId}")
                        Text("Conductor: ${pedido.conductorId}")
                        Text("Ayudante: ${pedido.ayudanteId}")
                        Text("Dirección: ${pedido.direccionEntrega}")
                        Text("Kilómetros: ${pedido.kilometros}")
                        Text("Valor: ${pedido.valor}")
                        Text("Estado: ${pedido.estado}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {

                                pedidoViewModel.eliminarPedido(
                                    pedido.id,
                                    onSuccess = {

                                        mensaje = "Pedido eliminado"

                                        pedidoViewModel.obtenerPedidos()
                                    },
                                    onError = {
                                        mensaje = it
                                    }
                                )
                            }
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}