import com.google.firebase.firestore.FirebaseFirestore

class UsuarioRepository {

    private val db = FirebaseFirestore.getInstance()

    fun salvarUsuario(usuarioID: String, nome: String, email: String, callback: (Boolean, String?) -> Unit) {
        val usuarios = hashMapOf(
            "Usuario" to nome,
            "E-mail" to email
        )

        db.collection("Usuarios").document(usuarioID)
            .set(usuarios)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { e ->
                callback(false, e.message)
            }
    }
}
