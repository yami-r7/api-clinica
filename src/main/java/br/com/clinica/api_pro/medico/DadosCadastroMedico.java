package br.com.clinica.api_pro.medico;

public record DadosCadastroMedico(String nome, String email, String telefone, String crm, Especialidade especialidade, String cidade) {}