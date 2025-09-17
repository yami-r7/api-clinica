package br.com.clinica.api_pro.paciente;

public record DadosListagemPaciente(Long id, String nome, String email, String telefone, String cpf, String cidade) {
    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), paciente.getCidade());
    }
}