package com.brezker.myapplication.extras

class Models {
    data class RespLogin(
        var idUsr:Int,
        var token:String,
        var nombre:String,
        var error:String,
    )
    data class Paciente(
        var nombre: String,
        var nss: String,
        var tipo_sangre: String,
        var alergias: String,
        var telefono: String,
        var domicilio: String,
    )
    data class Doctor(
        var nombre: String,
        var cedula: String,
        var especialidad: String,
        var turno: String,
        var telefono: String,
        var email: String,
    )
    data class Enfermedad(
        var nombre: String,
        var tipo: String,
        var descripcion: String,
    )
    data class Cita(
        var id_enfermedad: String,
        var id_paciente: String,
        var id_doctor: String,
        var consultorio: String,
        var domicilio: String,
        var fecha: String,
    )
}