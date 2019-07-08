package br.ifpe.web2.cadastro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.ifpe.web2.cadastro.model.Empresa;
import br.ifpe.web2.cadastro.model.EmpresaDAO;
import br.ifpe.web2.cadastro.model.Situacao;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaDAO empresaDao;
	
	public List<Empresa> listarTodasEmpresas(){
		
		Empresa empresaExemp = new Empresa();
		empresaExemp.setSituacao(Situacao.ATIVO);
		Example<Empresa> exemplo = Example.of(empresaExemp);
		
		return this.empresaDao.findAll(exemplo, Sort.by("nome"));
	}
}
