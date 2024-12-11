package com.example.projetomobile.ui.home.ui.perfil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.projetomobile.databinding.FragmentPerfilBinding
import com.example.projetomobile.domain.Usuario
import com.example.projetomobile.ui.alerta.EntradaAlerta
import com.example.projetomobile.ui.alerta.ExcluirPerfilAlerta
import com.example.projetomobile.ui.login.Login // Ajuste o caminho do LoginActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilBinding

    // Instância do ViewModel
    private val viewModel: PerfilViewModel by viewModels {
        PerfilViewModelFactory() // Implementar o Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciarComponents()
        observarViewModel()
        configurarAcoes()
    }

    private fun iniciarComponents() {
        // Acessando componentes via binding
        binding.textViewUsuario.text = "" // Inicializar vazio
        binding.textViewEmail.text = ""
    }

    private fun observarViewModel() {
        viewModel.usuario.observe(viewLifecycleOwner) { usuario ->
            atualizarUI(usuario)
        }

        viewModel.erro.observe(viewLifecycleOwner) { mensagemErro ->
            Toast.makeText(requireContext(), mensagemErro, Toast.LENGTH_LONG).show()
        }
    }

    private fun configurarAcoes() {
        binding.buttonSairDaConta.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        binding.buttonrecuperarSenha.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser

            if (currentUser != null) {
                val email = currentUser.email // Obter o e-mail do usuário logado

                // Abrir a caixa de diálogo para solicitar a senha
                val alerta = EntradaAlerta()
                alerta.showDesfocadoAlertBox(requireContext(), "Por favor, digite sua senha para confirmar") { confirmed, senha ->
                    if (confirmed) {
                        if (senha.isNotEmpty()) {
                            // Atualizar a senha com a nova senha fornecida
                            currentUser.updatePassword(senha)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Senha atualizada com sucesso
                                        Toast.makeText(requireContext(), "Senha atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        // Falha ao atualizar a senha
                                        Toast.makeText(requireContext(), "Erro ao atualizar a senha: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                        } else {
                            Toast.makeText(requireContext(), "A senha não pode estar vazia!", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Ação cancelada.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Nenhum usuário autenticado encontrado!", Toast.LENGTH_SHORT).show()
            }
        }


        binding.buttonExcluirConta.setOnClickListener {
            // Criar a instância do alerta
            val alerta = ExcluirPerfilAlerta()

            // Exibir o alerta de confirmação
            alerta.showDesfocadoAlertBox(requireContext(), "Tem certeza que deseja excluir sua conta?") { confirmed ->
                if (confirmed) {
                    // Se o usuário confirmar, prossegue com a exclusão

                    val currentUser = FirebaseAuth.getInstance().currentUser

                    if (currentUser != null) {
                        // Obter o ID do usuário
                        val userId = currentUser.uid

                        // Excluir os dados do Firestore
                        excluirDadosFirestore(userId)

                        // Excluir a conta do usuário
                        currentUser.delete()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Conta excluída com sucesso
                                    Toast.makeText(requireContext(), "Conta excluída com sucesso!", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(requireContext(), Login::class.java)
                                    startActivity(intent)
                                    requireActivity().finish()
                                } else {
                                    // Falha ao excluir a conta
                                    val exceptionMessage = task.exception?.message ?: "Erro desconhecido"
                                    Toast.makeText(requireContext(), "Erro ao excluir conta: $exceptionMessage", Toast.LENGTH_LONG).show()

                                    // Verificar se é necessário reautenticar o usuário
                                    if (task.exception?.message?.contains("recent login") == true) {
                                        // Chama a reautenticação
                                        reautenticarUsuario()
                                    }
                                }
                            }
                    } else {
                        Toast.makeText(requireContext(), "Nenhum usuário autenticado encontrado!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Caso o usuário cancele
                    Toast.makeText(requireContext(), "Exclusão de conta cancelada.", Toast.LENGTH_SHORT).show()
                }
            }
        }





        val currentUser = FirebaseAuth.getInstance().currentUser
        val usuarioID = currentUser?.uid ?: return
        viewModel.carregarUsuario(usuarioID)
    }
    private fun reautenticarUsuario() {
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return
        val email = currentUser.email ?: return

        // Adicionar log para verificar se a função está sendo chamada
        Log.d("ReautenticarUsuario", "Iniciando reautenticação")

        val alerta = EntradaAlerta()
        alerta.showDesfocadoAlertBox(requireContext(), "Digite sua senha para confirmar a exclusão da conta") { confirmed, senha ->
            // Adicionar log para verificar o fluxo da resposta
            Log.d("ReautenticarUsuario", "Resposta do diálogo: confirmado = $confirmed, senha = $senha")

            if (confirmed) {
                if (senha.isNotEmpty()) {
                    val credential = EmailAuthProvider.getCredential(email, senha)
                    currentUser.reauthenticate(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(requireContext(), "Reautenticação bem-sucedida!", Toast.LENGTH_SHORT).show()

                                // Após reautenticar, tenta excluir a conta
                                currentUser.delete()
                                    .addOnCompleteListener { deleteTask ->
                                        if (deleteTask.isSuccessful) {
                                            Toast.makeText(requireContext(), "Conta excluída com sucesso!", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(requireContext(), Login::class.java)
                                            startActivity(intent)
                                            requireActivity().finish()
                                        } else {
                                            Toast.makeText(requireContext(), "Erro ao excluir conta: ${deleteTask.exception?.message}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                            } else {
                                Toast.makeText(requireContext(), "Erro na reautenticação: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(requireContext(), "A senha não pode estar vazia!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(requireContext(), "Ação cancelada.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // Função para excluir os dados do Firestore
    private fun excluirDadosFirestore(userId: String) {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("Usuarios").document(userId)

        userRef.delete()
            .addOnSuccessListener {
                Log.d("Excluir Dados", "Dados do usuário excluídos com sucesso no Firestore")
            }
            .addOnFailureListener { e ->
                Log.w("Excluir Dados", "Erro ao excluir dados do usuário no Firestore", e)
            }
    }
    private fun editarUsuarioESenha(novoEmail: String?, novaSenha: String?) {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            // Atualizar o e-mail se um novo e-mail for fornecido
            novoEmail?.let {
                currentUser.updateEmail(it)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "E-mail atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Erro ao atualizar e-mail: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }

            // Atualizar a senha se uma nova senha for fornecida
            novaSenha?.let {
                currentUser.updatePassword(it)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Senha atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Erro ao atualizar senha: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        } else {
            Toast.makeText(requireContext(), "Nenhum usuário autenticado encontrado!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun atualizarUI(usuario: Usuario) {
        binding.textViewUsuario.text = usuario.nome
        binding.textViewEmail.text = usuario.email
    }
}
