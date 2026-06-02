package com.cesar.transportestobon.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.cesar.transportestobon.data.model.Pedido
import com.cesar.transportestobon.data.repository.PedidoRepository

class PedidoViewModel : ViewModel() {

    val pedidos = mutableStateListOf<Pedido>()

    private val repository = PedidoRepository()

    fun guardarPedido(
        pedido: Pedido,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        repository.guardarPedido(
            pedido,
            onSuccess,
            onError
        )
    }

    fun obtenerPedidos() {

        repository.obtenerPedidos { lista ->

            pedidos.clear()
            pedidos.addAll(lista)
        }
    }

    fun eliminarPedido(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        repository.eliminarPedido(
            id,
            onSuccess,
            onError
        )
    }
}