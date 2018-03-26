
package br.usjt.arqsw.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import br.usjt.arqsw.entity.Usuario;


/**
 * 
 * @author Carolina Cezareti 81620043
 *
 */

@Repository
public class UsuarioDAO {
	
	private Connection conn;
	@Repository
	public class LocalDAO {
	@PersistenceContext
	EntityManager manager;
	
	public void criar(Local local){
	manager.persist(local);
	}
	public void atualizar(Local local){
	manager.merge(local);
	}
	public void remover(Local local){
	manager.remove(local);
	}
	public Local selecionar(int id){
	return manager.find(Local.class, id);
	}
	@SuppressWarnings("unchecked")
	public List<Local> selecionarTodas(){
	return manager.createQuery("select l from Local l").getResultList();
	}
	}
	
	@Autowired
	public UsuarioDAO(DataSource dataSource) throws IOException{
	try{
	this.conn = dataSource.getConnection();
	} catch (SQLException e){
	throw new IOException(e);
	}
	}
	
	public ArrayList<Usuario> consultarUsuarios() throws IOException {
		String query = "select username, password from usuario";
		ArrayList<Usuario> lista = new ArrayList<>();
		
		try(PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();){
			
			while(rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setUsername(rs.getString("username"));
				usuario.setPassword(rs.getString("password"));
				lista.add(usuario);
			}
			
		} catch (SQLException e) {
			throw new IOException(e);
		}
		return lista;
	}

	public boolean validarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}

}
