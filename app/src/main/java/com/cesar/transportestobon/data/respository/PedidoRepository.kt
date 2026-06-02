package com.cesar.transportestobon.data.repository

import com.cesar.transportestobon.data.model.Pedido
import com.cesar.transportestobon.utils.FirebaseManager

class PedidoRepository {

    private val db = FirebaseManager.firestore

    fun guardarPedido(
        pedido: Pedido,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val id = db.collection("pedidos").document().id

        val nuevoPedido = pedido.copy(id = id)

        db.collection("pedidos")
            .document(id)
            .set(nuevoPedido)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun obtenerPedidos(
        onResult: (List<Pedido>) -> Unit
    ) {

        db.collection("pedidos")
            .get()
            .addOnSuccessListener { resultado ->

                val lista = resultado.documents.mapNotNull {
                    it.toObject(Pedido::class.java)
                }

                onResult(lista)
            }
    }

    fun eliminarPedido(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("pedidos")
            .document(id)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }
    fun actualizarPedido(
        pedido: Pedido,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("pedidos")
            .document(pedido.id)
            .set(pedido)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

}