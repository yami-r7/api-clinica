package br.com.clinica.api_pro.controller;

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
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.findById(id);

        if (medico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var medicoEncontrado = medico.get();
        medicoEncontrado.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medicoEncontrado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir(); 

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ativar")
    @Transactional
    public ResponseEntity reativar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.reativar();

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}