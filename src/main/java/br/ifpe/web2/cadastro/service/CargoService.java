package br.ifpe.web2.cadastro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.ifpe.web2.cadastro.model.Cargo;
import br.ifpe.web2.cadastro.model.CargoDAO;
import br.ifpe.web2.cadastro.model.Situacao;

@Service
public class CargoService {

	@Autowired
	private CargoDAO cargoDao;
	
	public List<Cargo> listarTodosCargos(){
		Cargo cargoExemplo = new Cargo();
		cargoExemplo.setSituacao(Situacao.ATIVO);
		Example<Cargo> exemplo = Example.of(cargoExemplo);
		return this.cargoDao.findAll(exemplo, Sort.by("descricao"));
	}
}
