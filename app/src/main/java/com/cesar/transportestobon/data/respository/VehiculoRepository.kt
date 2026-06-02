package com.cesar.transportestobon.data.repository
import com.cesar.transportestobon.data.model.Vehiculo
import com.cesar.transportestobon.utils.FirebaseManager

class VehiculoRepository {

    private val db = FirebaseManager.firestore

    fun guardarVehiculo(
        vehiculo: Vehiculo,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val id = db.collection("vehiculos").document().id

        val nuevoVehiculo = vehiculo.copy(id = id)

        db.collection("vehiculos")
            .document(id)
            .set(nuevoVehiculo)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun obtenerVehiculos(
        onResult: (List<Vehiculo>) -> Unit
    ) {

        db.collection("vehiculos")
            .get()
            .addOnSuccessListener { resultado ->

                val lista = resultado.documents.mapNotNull {
                    it.toObject(Vehiculo::class.java)
                }

                onResult(lista)
            }
    }

    fun eliminarVehiculo(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("vehiculos")
            .document(id)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }

    fun actualizarVehiculo(
        vehiculo: Vehiculo,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        db.collection("vehiculos")
            .document(vehiculo.id)
            .set(vehiculo)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error")
            }
    }
}