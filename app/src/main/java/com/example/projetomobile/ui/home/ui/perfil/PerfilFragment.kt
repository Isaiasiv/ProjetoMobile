package com.example.projetomobile.ui.home.ui.perfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.projetomobile.databinding.FragmentPerfilBinding
import com.example.projetomobile.domain.Usuario
import com.example.projetomobile.ui.login.Login // Ajuste o caminho do LoginActivity
import com.google.firebase.auth.FirebaseAuth

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

        val currentUser = FirebaseAuth.getInstance().currentUser
        val usuarioID = currentUser?.uid ?: return
        viewModel.carregarUsuario(usuarioID)
    }

    private fun atualizarUI(usuario: Usuario) {
        binding.textViewUsuario.text = usuario.nome
        binding.textViewEmail.text = usuario.email
    }
}
