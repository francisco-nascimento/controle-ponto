package br.ifpe.web2.cadastro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.ifpe.web2.cadastro.model.Departamento;
import br.ifpe.web2.cadastro.model.DepartamentoDAO;
import br.ifpe.web2.cadastro.model.Situacao;

@Service
public class DepartamentoService {

	@Autowired
	private DepartamentoDAO depDao;
	
	public List<Departamento> listarTodosDepartamentos(){
		Departamento depExemplo = new Departamento();
		depExemplo.setSituacao(Situacao.ATIVO);
		Example<Departamento> exemplo = Example.of(depExemplo);
		return this.depDao.findAll(exemplo, Sort.by("nome"));
	}
}
