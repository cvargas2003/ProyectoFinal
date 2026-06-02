package com.cesar.transportestobon.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.cesar.transportestobon.data.model.Empleado
import com.cesar.transportestobon.data.repository.EmpleadoRepository

class EmpleadoViewModel : ViewModel() {

    val empleados = mutableStateListOf<Empleado>()

    private val repository = EmpleadoRepository()

    fun guardarEmpleado(
        empleado: Empleado,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        repository.guardarEmpleado(
            empleado,
            onSuccess,
            onError
        )
    }

    fun obtenerEmpleados() {

        repository.obtenerEmpleados { lista ->

            empleados.clear()
            empleados.addAll(lista)
        }
    }

    fun eliminarEmpleado(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        repository.eliminarEmpleado(
            id,
            onSuccess,
            onError
        )
    }

    fun actualizarEmpleado(
        empleado: Empleado,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        repository.actualizarEmpleado(
            empleado,
            onSuccess,
            onError
        )
    }
}