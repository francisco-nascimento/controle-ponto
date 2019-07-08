package br.ifpe.web2.util;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.InputMismatchException;

import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;

import br.ifpe.web2.acesso.Permissao;
import br.ifpe.web2.acesso.Usuario;

/**
 * Classe com métodos úteis para fins diversos como validações, criptografia, reflections, etc. 
 * @author fnascimento
 *
 */
public class Util {

	/**
	 * Criptografa uma string usando MD5
	 * Este método deve ser usado em duas ocasiões:
	 *   1) ao criar um novo usuário, no service, antes de enviar para o DAO
	 *   2) ao efetuar login, antes de enviar para o DAO o login/senha para ser verificado 
	 * @param senha
	 * @return senha criptografada
	 * @throws NoSuchAlgorithmException - reporta falhas na tentativa de criptografar
	 */
	public static String md5(String senha) throws NoSuchAlgorithmException  {  
		MessageDigest messageDigest  = MessageDigest.getInstance("MD5");  
		BigInteger hash = new BigInteger(1, messageDigest.digest(senha.getBytes()));  
		return  hash.toString(16);  
	} 
	
	/**
	 * Verificar se o usuário tem permissão para acessar um método do controller
	 * @param usuario - Usuário logado 
	 * @param hm - Metodo que está sendo solicitado para ser executado
	 * @return - True / false - O usuário pode ou não executar esse método
	 */
	public static boolean verificarPermissao(Usuario usuario, HandlerMethod hm) {
		if (usuario.getPermissao() == Permissao.ADMIN) {
			return true;
		}
	    Method method = hm.getMethod(); 
	    if(method.getDeclaringClass().isAnnotationPresent(Controller.class)) {
	        if(method.isAnnotationPresent(RolesAllowed.class)) {
	        	String[] permissoes = method.getAnnotation(RolesAllowed.class).value();
	        	for(String p : permissoes) {
	        		if (usuario.getPermissao().toString().equals(p)) {
	        			return true;
	        		}
	        	}
	        } else {
	        	return true;
	        }
	    } 
	    return false;
	}
	
	public static boolean validarDigitoVerificadorCPF(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11)) {
			return (false);
		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static int calcularIdade(Date dataNascimento) {
		if (dataNascimento == null) {
			throw new RuntimeException("Data de nascimento nula");
		}
		LocalDate hoje = LocalDate.now();
		LocalDate nascimento = dataNascimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period periodo = Period.between(nascimento, hoje);
		return periodo.getYears();
	}
	
	public static void main(String[] args) throws ParseException {
		Date data1 = new SimpleDateFormat("dd-MM-yyyy").parse("25-06-1980");
		System.out.println(data1);
		System.out.println(calcularIdade(data1));
	}

}
