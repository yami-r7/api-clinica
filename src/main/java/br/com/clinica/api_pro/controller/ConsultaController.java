package br.com.clinica.api_pro.controller;

import br.com.clinica.api_pro.consulta.*;
import br.com.clinica.api_pro.medico.MedicoRepository;
import br.com.clinica.api_pro.paciente.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var medico = medicoRepository.getReferenceById(dados.idMedico());
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);

        consultaRepository.save(consulta);

        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(@PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {
        var page = consultaRepository.findAll(paginacao).map(DadosDetalhamentoConsulta::new);

        return ResponseEntity.ok(page);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancelar(@PathVariable Long id, @RequestBody @Valid DadosCancelamentoConsulta dados) {
        var consulta = consultaRepository.getReferenceById(id);
        consulta.cancelar(dados.motivo());

        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }
}
