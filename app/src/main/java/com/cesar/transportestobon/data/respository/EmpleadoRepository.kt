package com.cesar.transportestobon.data.repository

import com.cesar.transportestobon.data.model.Empleado
import com.cesar.transportestobon.utils.FirebaseManager

class EmpleadoRepository {

    private val db = FirebaseManager.firestore

    fun guardarEmpleado(
        empleado: Empleado,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val id = db.collection("empleados").document().id

        val nuevoEmpleado = empleado.copy(id = id)

        db.collection("empleados")
            .document(id)
            .set(nuevoEmpleado)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun obtenerEmpleados(
        onResult: (List<Empleado>) -> Unit
    ) {

        db.collection("empleados")
            .get()
            .addOnSuccessListener { resultado ->

                val lista = resultado.documents.mapNotNull {
                    it.toObject(Empleado::class.java)
                }

                onResult(lista)
            }
    }

    fun eliminarEmpleado(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("empleados")
            .document(id)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun actualizarEmpleado(
        empleado: Empleado,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("empleados")
            .document(empleado.id)
            .set(empleado)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }
}