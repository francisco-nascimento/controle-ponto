package br.ifpe.web2.cadastro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.ifpe.web2.cadastro.model.Funcionario;
import br.ifpe.web2.cadastro.model.FuncionarioDAO;
import br.ifpe.web2.util.ServiceException;
import br.ifpe.web2.util.Util;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioDAO funcDAO;

	public Funcionario salvar(Funcionario f) throws ServiceException {
		// Regras de negocio antes do salvar
		
		//[FS0003] – Validar CPF
		// Verificar digito do CPF
		if (!Util.validarDigitoVerificadorCPF(f.getCpf())) {
			throw new ServiceException("CPF inválido: " + f.getCpf());
		}
		// Verificar CPF já informado
		if (this.funcDAO.findByCpf(f.getCpf()) != null) {
			throw new ServiceException("Já existe funcionário com o CPF informado: " + f.getCpf());
		}
		
		// [FS0004] – Validar Data
		if (Util.calcularIdade(f.getDataNascimento()) < 18) {
			throw new ServiceException("O funcionário deve possuir, no mínimo, 18 anos de idade.");
		}
		
		return funcDAO.save(f);
	}
	
	public List<Funcionario> listarTodosFuncionarios(){
		return this.funcDAO.findAll(Sort.by("nome"));
	}
	
	public List<Funcionario> pesquisarPorNome(String nome){
		return this.funcDAO.findByNomeContainingIgnoreCase(nome);
	}

	public Funcionario obterFuncionarioPorCodigo(Integer codigo) {
		return this.funcDAO.findById(codigo).orElse(null);
	}

	public void remover(Funcionario funcionario) {
		this.funcDAO.delete(funcionario);
	}

	public List<Funcionario> pesquisarPorFiltro(Funcionario funcionario) {
		Example<Funcionario> exemplo = Example.of(funcionario);
		return this.funcDAO.findAll(exemplo, Sort.by("nome"));
	}
	
	
}
