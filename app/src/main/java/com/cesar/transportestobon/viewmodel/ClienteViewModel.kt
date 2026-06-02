package com.cesar.transportestobon.viewmodel

import androidx.lifecycle.ViewModel
import com.cesar.transportestobon.data.model.Cliente
import com.cesar.transportestobon.data.repository.ClienteRepository
import androidx.compose.runtime.mutableStateListOf

class ClienteViewModel : ViewModel() {
    val clientes = mutableStateListOf<Cliente>()
    private val repository = ClienteRepository()

    fun guardarCliente(
        cliente: Cliente,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        repository.guardarCliente(
            cliente,
            onSuccess,
            onError
        )
    }
    fun obtenerClientes() {

        repository.obtenerClientes { lista ->

            clientes.clear()
            clientes.addAll(lista)
        }
    }
    fun eliminarCliente(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        repository.eliminarCliente(
            id,
            onSuccess,
            onError
        )
    }
    fun actualizarCliente(
        cliente: Cliente,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        repository.actualizarCliente(
            cliente,
            onSuccess,
            onError
        )
    }
}