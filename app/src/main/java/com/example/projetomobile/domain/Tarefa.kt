package com.example.projetomobile.domain

data class Tarefa(
    var id: String? = null,
    val nome: String,
    val titulo: String,
    val objetivo: String,
    val descricao: String,
    val dataInicio: String,
    val dataFim: String,
    val status: String
)
