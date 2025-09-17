package br.com.clinica.api_pro.controller;

import br.com.clinica.api_pro.paciente.DadosCadastroPaciente;
import br.com.clinica.api_pro.paciente.DadosListagemPaciente;
import br.com.clinica.api_pro.paciente.Paciente;
import br.com.clinica.api_pro.paciente.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dados) {
        // Cria a entidade Paciente com os dados do DTO
        repository.save(new Paciente(dados));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(page = 0, size = 10, sort = {"nome"}) Pageable paginacao) {
        // Busca os pacientes, já paginado pelo Spring
        Page<Paciente> paginaDePacientes = repository.findAll(paginacao);
        
        // Converte a página de entidades Paciente para uma página de DTOs DadosListagemPaciente
        return paginaDePacientes.map(DadosListagemPaciente::new);
    }
}