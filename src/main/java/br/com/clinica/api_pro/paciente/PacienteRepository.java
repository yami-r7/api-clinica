package br.com.clinica.api_pro.paciente; // Lembre-se de criar a entidade e DTOs de paciente

import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {}