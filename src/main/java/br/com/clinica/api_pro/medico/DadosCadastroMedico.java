package br.com.clinica.api_pro.medico;

import br.com.clinica.api_pro.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroMedico(
    @NotBlank
    String nome,
    @NotBlank 
    String email,
    @NotBlank 
    String telefone,
    @NotBlank 
    String crm,
    @NotNull 
    Especialidade especialidade,
    @NotNull @Valid
    DadosEndereco endereco
) {
}