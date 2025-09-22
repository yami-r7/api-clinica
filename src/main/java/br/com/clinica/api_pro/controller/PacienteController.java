package br.com.clinica.api_pro.controller;

import br.com.clinica.api_pro.paciente.DadosCadastroPaciente;
import br.com.clinica.api_pro.paciente.DadosListagemPaciente;
import br.com.clinica.api_pro.paciente.Paciente;
import br.com.clinica.api_pro.paciente.PacienteRepository;
import jakarta.validation.Valid;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.clinica.api_pro.infra.ApiResponse;
import br.com.clinica.api_pro.paciente.DadosAtualizacaoPaciente;
import br.com.clinica.api_pro.paciente.DadosDetalhamentoPaciente;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse<DadosDetalhamentoPaciente>> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dados);
        repository.save(paciente);

        var pacienteDetalhado = new DadosDetalhamentoPaciente(paciente);

        var response = new ApiResponse<>(
            LocalDateTime.now(),
            "Paciente cadastrado com sucesso!",
            pacienteDetalhado
        );

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(page = 0, size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse<DadosDetalhamentoPaciente>> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var pacienteEncontrado = repository.findById(id);

        if (pacienteEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var paciente = pacienteEncontrado.get();
        paciente.atualizarInformacoes(dados);

        var response = new ApiResponse<>(
            LocalDateTime.now(),
            "Dados do paciente atualizados com sucesso!",
            new DadosDetalhamentoPaciente(paciente)
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse<Object>> excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir(); 

        var response = new ApiResponse<>(
            LocalDateTime.now(),
            "Paciente desativado com sucesso",
            null
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/ativar")
    @Transactional
    public ResponseEntity<ApiResponse<DadosDetalhamentoPaciente>> reativar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.reativar();

        var response = new ApiResponse<>(
            LocalDateTime.now(),
            "Paciente reativado com sucesso",
            new DadosDetalhamentoPaciente(paciente)
        );

        return ResponseEntity.ok(response);
    }
}