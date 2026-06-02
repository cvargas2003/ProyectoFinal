package com.cesar.transportestobon.data.model

data class Pedido(
    val id: String = "",
    val clienteId: String = "",
    val vehiculoId: String = "",
    val conductorId: String = "",
    val ayudanteId: String = "",
    val direccionEntrega: String = "",
    val kilometros: String = "",
    val valor: String = "",
    val estado: String = ""
)