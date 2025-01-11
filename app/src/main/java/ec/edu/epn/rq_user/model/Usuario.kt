package ec.edu.epn.rq_user.model

import java.util.Date

data class Usuario (
  val nombre: String,
  val apellido: String,
  val fechaNacimiento: Date,
  val cedula: String,
  val telefono: String,
  val email: String,
)