package br.com.arlivre.model.dao;

import java.util.List;

import br.com.arlivre.model.entidade.Perfil;

public interface PerfilDAO {
	void salvar (Perfil perfil) throws DAOException;
	List<Perfil> buscarTodos();
	void excluir(Perfil usuario) throws DAOException;
	Perfil buscarPeloId(Integer id) throws DAOException;
}
