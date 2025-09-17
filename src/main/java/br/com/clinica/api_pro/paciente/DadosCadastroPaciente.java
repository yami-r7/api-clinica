package br.com.clinica.api_pro.paciente;

import br.com.clinica.api_pro.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroPaciente(
    @NotBlank
    String nome,
    @NotBlank 
    String email,
    @NotBlank 
    String telefone,
    @NotBlank 
    String cpf,
    @NotNull @Valid 
    DadosEndereco endereco
) {

}