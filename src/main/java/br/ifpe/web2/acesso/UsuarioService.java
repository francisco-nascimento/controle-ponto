package br.ifpe.web2.acesso;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ifpe.web2.util.ServiceException;
import br.ifpe.web2.util.Util;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	public Usuario efetuarLogin(String email, String senha) throws ServiceException {
		try {
			return this.usuarioDAO.efetuarLogin(email, Util.md5(senha));
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceException("Erro na criptografia");
		}
	}
	
	public Usuario findUsuarioByEmail(String email) {
		return usuarioDAO.findByEmail(email);
	}

	public Usuario save(Usuario novo) throws ServiceException {
		Usuario usuarioAtual = findUsuarioByEmail(novo.getEmail());
		usuarioAtual.setNome(novo.getNome());
		return usuarioDAO.save(usuarioAtual);
	}

	public Usuario alterar(Usuario entity) {
		
		return usuarioDAO.saveAndFlush(entity);
	}

	public Usuario findById(Integer id) {
		return usuarioDAO.findById(id).orElse(null);
	}

	public List<Usuario> listarTodos() {
		return usuarioDAO.findAll();
	}
	public boolean existsByEmail(String email) {
		return findUsuarioByEmail(email) != null;
	}
	
	public void criarUsuario(Usuario usuario) throws Exception {
		
	    if (existsByEmail(usuario.getEmail())) {
	        throw new EmailExistsException
	          ("Já existe usuário com este e-mail: " + usuario.getEmail());
	    }
	    if(!usuario.getSenha().equals(usuario.getSenhaRepetida())) {
	    	throw new Exception("Senhas não coincidem!");
	    }
		try {
			usuario.setSenha(Util.md5(usuario.getSenha()));
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceException("Erro na criptografia");
		}
		usuarioDAO.save(usuario);
	}
	
	public void registrarUltimoLogin(Usuario usuario) {
		this.usuarioDAO.saveAndFlush(usuario);
	}

	public Object findUsuarioByNomeEmailAprox(String nome, String email) {
		return usuarioDAO.findByNomeEmailAprox(nome, email);
	}
	
	
	
}
