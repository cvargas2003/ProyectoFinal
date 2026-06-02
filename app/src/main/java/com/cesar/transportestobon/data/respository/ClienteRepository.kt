package com.cesar.transportestobon.data.repository

import com.cesar.transportestobon.data.model.Cliente
import com.cesar.transportestobon.utils.FirebaseManager

class ClienteRepository {

    private val db = FirebaseManager.firestore

    fun guardarCliente(
        cliente: Cliente,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val id = db.collection("clientes").document().id

        val nuevoCliente = cliente.copy(id = id)

        db.collection("clientes")
            .document(id)
            .set(nuevoCliente)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }
    fun obtenerClientes(
        onResult: (List<Cliente>) -> Unit
    ) {

        db.collection("clientes")
            .get()
            .addOnSuccessListener { resultado ->

                val lista = resultado.documents.mapNotNull {
                    it.toObject(Cliente::class.java)
                }

                onResult(lista)
            }
    }
    fun eliminarCliente(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("clientes")
            .document(id)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }
    fun actualizarCliente(
        cliente: Cliente,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("clientes")
            .document(cliente.id)
            .set(cliente)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }


}