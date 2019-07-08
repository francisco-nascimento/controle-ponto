package br.ifpe.web2.acesso;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuController {

	@GetMapping("/pessoas")
	public String exibirIndex() {
		return "index";
	}
	
	@PostMapping("/login")
	public String efetuarLogin() {
		return "home";
	}

}
