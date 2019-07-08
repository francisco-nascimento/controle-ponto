package br.ifpe.web2.cadastro.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioDAO extends JpaRepository<Funcionario, Integer> {

	public Funcionario findByCpf(String cpf);
	
	public List<Funcionario> findByNomeContainingIgnoreCase(String nome);

}
