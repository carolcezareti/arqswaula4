package br.usjt.arqsw.entity;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Carolina Cezareti 81620043
 *
 */

@Entity
public class Usuario {
	public static final String LOGADO = "logado";
	
	@NotNull
	@Max(value = 50)
	private String username;
	@NotNull
	@Max(value = 50)
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String toString() {
		return "Usuario [username=" + username + ", password = " + password + ")";
	}
}
