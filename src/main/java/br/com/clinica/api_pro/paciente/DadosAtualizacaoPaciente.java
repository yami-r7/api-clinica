package br.com.clinica.api_pro.paciente;

import br.com.clinica.api_pro.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
    String nome,
    String email,
    String telefone,
    String cpf,
    DadosEndereco endereco
) {
}
