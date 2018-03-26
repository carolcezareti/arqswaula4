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

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;

/**
 * 
 * @author Carolina Cezareti 81620043
 *
 */

@Repository
public class ChamadoDAO {
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
	
	
	public ArrayList<Chamado> listarChamados(Fila fila) throws IOException{
		ArrayList<Chamado> lista = new ArrayList<>();
		String query = "select c.id_chamado, c.descricao, c.dt_abertura, f.nm_fila "+
				"from chamado c, fila f where c.id_fila = f.id_fila and c.id_fila=?";
		
		try	(PreparedStatement pst = conn.prepareStatement(query);){
			pst.setInt(1, fila.getId());
			
			try(ResultSet rs = pst.executeQuery();){
				while(rs.next()){
					Chamado chamado = new Chamado();
					chamado.setNumero(rs.getInt("id_chamado"));
					chamado.setDescricao(rs.getString("descricao"));
					chamado.setDataAbertura(rs.getDate("dt_abertura"));
					fila.setNome(rs.getString("nm_fila"));
					chamado.setFila(fila);
					lista.add(chamado);
				}
			} catch(SQLException e){
				e.printStackTrace();
				throw new IOException(e);
			}
		} catch(SQLException e){
			e.printStackTrace();
			throw new IOException(e);
		}
		return lista;
	}
	
}
