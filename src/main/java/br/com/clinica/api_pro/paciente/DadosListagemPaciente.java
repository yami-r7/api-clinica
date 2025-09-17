package br.com.clinica.api_pro.paciente;

import br.com.clinica.api_pro.endereco.Endereco;

public record DadosListagemPaciente(Long id, String nome, String email, String telefone, String cpf, Endereco endereco) {
    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), paciente.getEndereco());
    }
}