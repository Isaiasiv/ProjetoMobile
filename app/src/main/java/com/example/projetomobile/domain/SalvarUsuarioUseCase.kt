class SalvarUsuarioUseCase(private val repository: UsuarioRepository) {

    fun execute(usuarioID: String, nome: String, email: String, callback: (Boolean, String?) -> Unit) {
        repository.salvarUsuario(usuarioID, nome, email, callback)
    }
}
