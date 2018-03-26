package br.usjt.arqsw.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.usjt.arqsw.entity.Usuario;
import br.usjt.arqsw.service.UsuarioService;

/**
 * 
 * @author Carolina Cezareti 81620043
 *
 */

@Controller
public class LoginController {
UsuarioService service;
	
	@Autowired
	public LoginController(UsuarioService srv){
		this.service = srv;
	}
	
	@Transactional
	@RequestMapping("loginForm")
	public String loginForm(){
		return "login";
	}
	
	@RequestMapping("fazer_login")
	public String fazerLogin(Usuario usuario, HttpSession session){
		try {
			if(service.validarUsuario(usuario)){
				session.setAttribute(Usuario.LOGADO, usuario);
				return "index";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
		return "redirect:loginForm";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session){
		session.setAttribute(Usuario.LOGADO, null);
		return "index";
	}

}
