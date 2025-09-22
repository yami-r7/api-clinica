package br.com.clinica.api_pro.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.clinica.api_pro.infra.ApiResponse;
import br.com.clinica.api_pro.medico.DadosAtualizacaoMedico;
import br.com.clinica.api_pro.medico.DadosCadastroMedico;
import br.com.clinica.api_pro.medico.DadosDetalhamentoMedico;
import br.com.clinica.api_pro.medico.DadosListagemMedico;
import br.com.clinica.api_pro.medico.Medico;
import br.com.clinica.api_pro.medico.MedicoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse<DadosDetalhamentoMedico>> cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        repository.save(medico);

        var medicoDetalhado = new DadosDetalhamentoMedico(medico);

        var response = new ApiResponse<>(
            LocalDateTime.now(),
            "Médico cadastrado com sucesso!",
            medicoDetalhado
        );

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse<DadosDetalhamentoMedico>> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medicoEncontrado = repository.findById(id);

        if (medicoEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var medico = medicoEncontrado.get();
        medico.atualizarInformacoes(dados);

        var response = new ApiResponse<>(
            LocalDateTime.now(),
            "Dados do médico atualizados com sucesso!",
            new DadosDetalhamentoMedico(medico)
    );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse<Object>> excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir(); 

        var response = new ApiResponse<> (
            LocalDateTime.now(),
            "Médico desativado com sucesso!",
            null
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/ativar")
    @Transactional
    public ResponseEntity<ApiResponse<DadosDetalhamentoMedico>> reativar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.reativar();

        var response = new ApiResponse<>(
            LocalDateTime.now(),
            "Médico reativado com sucesso!",
            new DadosDetalhamentoMedico(medico)
        );

        return ResponseEntity.ok(response);
    }
}