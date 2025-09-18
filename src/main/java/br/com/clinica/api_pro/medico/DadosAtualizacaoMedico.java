package br.com.clinica.api_pro.medico;

import br.com.clinica.api_pro.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
    String nome, 
    String email, 
    String telefone, 
    String crm, 
    DadosEndereco endereco
) {
}
