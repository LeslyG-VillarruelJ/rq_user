package ec.edu.epn.rq_user.model

data class Ruta (
  val latitudInicial: Number,
  val latitudFinal: Number,
  val longitudInicial: Number,
  val longitudFinal: Number,
  val horaPartida: String,
  val conductorID: String,
  val estadoRuta: Boolean,
)