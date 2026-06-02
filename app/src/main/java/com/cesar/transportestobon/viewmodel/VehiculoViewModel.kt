package com.cesar.transportestobon.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.cesar.transportestobon.data.model.Vehiculo
import com.cesar.transportestobon.data.repository.VehiculoRepository

class VehiculoViewModel : ViewModel() {

    val vehiculos = mutableStateListOf<Vehiculo>()

    private val repository = VehiculoRepository()

    fun guardarVehiculo(
        vehiculo: Vehiculo,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        repository.guardarVehiculo(
            vehiculo,
            onSuccess,
            onError
        )
    }

    fun obtenerVehiculos() {

        repository.obtenerVehiculos { lista ->

            vehiculos.clear()
            vehiculos.addAll(lista)
        }
    }

    fun eliminarVehiculo(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        repository.eliminarVehiculo(
            id,
            onSuccess,
            onError
        )
    }

    fun actualizarVehiculo(
        vehiculo: Vehiculo,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        repository.actualizarVehiculo(
            vehiculo,
            onSuccess,
            onError
        )
    }
}