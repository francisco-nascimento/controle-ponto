package br.ifpe.web2.cadastro.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.ifpe.web2.cadastro.model.Funcionario;
import br.ifpe.web2.cadastro.model.UF;
import br.ifpe.web2.cadastro.service.CargoService;
import br.ifpe.web2.cadastro.service.DepartamentoService;
import br.ifpe.web2.cadastro.service.EmpresaService;
import br.ifpe.web2.cadastro.service.FuncionarioService;
import br.ifpe.web2.util.ServiceException;

@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcService;
	@Autowired
	private CargoService cargoService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private DepartamentoService departamentoService;
	
	@GetMapping("/funcionarios")
	public ModelAndView exibirFuncionarios() {
		ModelAndView mv = new ModelAndView("/cadastros/funcionarios-list");		
		mv.addObject("listaFuncionarios", this.funcService.listarTodosFuncionarios());
		return mv;
	}
	
	@PostMapping("/funcionarios")
	public ModelAndView pesquisarFuncionarios(@RequestParam(required=false) String nomePesquisa) {
		ModelAndView mv = new ModelAndView("/cadastros/funcionarios-list");		
		
		List<Funcionario> listaFuncionarios;
		if (nomePesquisa == null || nomePesquisa.trim().isEmpty()) {
			listaFuncionarios = this.funcService.listarTodosFuncionarios();	
		} else {
			listaFuncionarios = this.funcService.pesquisarPorNome(nomePesquisa);
		}
		
		mv.addObject("listaFuncionarios", listaFuncionarios);
		return mv;
	}

	@GetMapping("/pesquisarFuncionarios")
	public ModelAndView exibirFormPesquisarFuncionarios(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("/cadastros/funcionarios-form");
		mv.addObject("listaCargos", this.cargoService.listarTodosCargos());
		mv.addObject("listaEmpresas", this.empresaService.listarTodasEmpresas());
		mv.addObject("listaDepartamentos", this.departamentoService.listarTodosDepartamentos());
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	@PostMapping("/pesquisarFuncionarios")
	public ModelAndView pesquisarFuncionariosPorFiltro(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("/cadastros/funcionarios-list");
		
		List<Funcionario> listaFuncionarios = this.funcService.pesquisarPorFiltro(funcionario);
		
		mv.addObject("listaFuncionarios", listaFuncionarios);
		return mv;
	}

	@GetMapping("/funcionariosForm")
	public ModelAndView exibirFormFuncionarios(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("/cadastros/funcionarios-form");		
		mv.addObject("listaCargos", this.cargoService.listarTodosCargos());
		mv.addObject("listaEmpresas", this.empresaService.listarTodasEmpresas());
		mv.addObject("listaUFs", UF.values());
		mv.addObject("listaDepartamentos", this.departamentoService.listarTodosDepartamentos());

		// Caso seja passado o Id do funcionario, esta chamada será para edição do funcionario
		if (funcionario != null && funcionario.getCodigo() != null) {
			funcionario = this.funcService.obterFuncionarioPorCodigo(funcionario.getCodigo());
		} else { // Caso contrário, será uma adição de novo funcionario
			funcionario = new Funcionario();
		}
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	@PostMapping("/salvarFuncionario")
	public ModelAndView salvarFuncionario(@Valid Funcionario funcionario, BindingResult br) {

		if (br.hasErrors()) {
			ModelAndView mv = new ModelAndView("/cadastros/funcionarios-form");
			mv.addObject("listaCargos", this.cargoService.listarTodosCargos());
			mv.addObject("listaEmpresas", this.empresaService.listarTodasEmpresas());
			mv.addObject("listaUFs", UF.values());
			mv.addObject("listaDepartamentos", this.departamentoService.listarTodosDepartamentos());

			return mv;
		} 

		try {
			this.funcService.salvar(funcionario);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
			
		return this.exibirFuncionarios();
	}
	
	
	@GetMapping("/funcionarioRemover")
	public String removerFuncionario(Funcionario funcionario) {
		if (funcionario != null && funcionario.getCodigo() != null) {
			this.funcService.remover(funcionario);
		}
		return "redirect:/funcionarios";
	}

}
