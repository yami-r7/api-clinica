package br.com.clinica.api_pro.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        @NotNull
        MotivoCancelamento motivo
) {

}
