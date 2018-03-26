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

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import br.usjt.arqsw.entity.Fila;
/**
 * 
 * @author Carolina Cezareti 81620043
 *
 */
public class FilaDAO {
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

	/**
	 * 
	 * @return 
	 * @return
	 * @throws IOException
	 */
	
	
	
	public FilaDAO(DataSource dataSource) throws IOException{
		try{
		this.conn = dataSource.getConnection();
		} catch (SQLException e){
		throw new IOException(e);
		}}
		
	
		
	
	public ArrayList<Fila> listarFilas() throws IOException {
		String query = "select id_fila, nm_fila from fila";
		ArrayList<Fila> lista = new ArrayList<>();
		
		try(PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();){
			
			while(rs.next()) {
				Fila fila = new Fila();
				fila.setId(rs.getInt("id_fila"));
				fila.setNome(rs.getString("nm_fila"));
				lista.add(fila);
			}
			
		} catch (SQLException e) {
			throw new IOException(e);
		}
		return lista;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public Fila carregar(int id) throws IOException {
		Fila fila = new Fila();
		fila.setId(id);
		String query = "select nm_fila from fila where id_fila=?";

		try (PreparedStatement pst = conn.prepareStatement(query);) {
			pst.setInt(1, id);
			try (ResultSet rs = pst.executeQuery();) {

				if (rs.next()) {
					fila.setNome(rs.getString("nm_fila"));
				} else {
					fila.setNome(null);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		return fila;
	}
	

}
