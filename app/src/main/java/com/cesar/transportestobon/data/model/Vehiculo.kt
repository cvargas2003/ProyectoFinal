package com.cesar.transportestobon.data.model

data class Vehiculo(
    val id: String = "",
    val placa: String = "",
    val marca: String = "",
    val color: String = "",
    val pesoMaximo: String = "",

    // Relación con empleados
    val conductorId: String = "",
    val ayudanteId: String = ""
)