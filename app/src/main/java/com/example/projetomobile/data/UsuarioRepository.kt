import com.example.projetomobile.domain.Usuario
import com.google.firebase.firestore.FirebaseFirestore

class UsuarioRepository {

    private val db = FirebaseFirestore.getInstance()

    fun obterUsuario(usuarioID: String, callback: (Usuario?, String?) -> Unit) {
        db.collection("Usuarios").document(usuarioID)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val usuario = Usuario(
                        nome = document.getString("Usuario") ?: "",
                        email = document.getString("E-mail") ?: ""
                    )
                    callback(usuario, null)
                } else {
                    callback(null, "Usuário não encontrado.")
                }
            }
            .addOnFailureListener { e ->
                callback(null, e.message)
            }
    }
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
