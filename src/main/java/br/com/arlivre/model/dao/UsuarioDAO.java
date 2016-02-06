package br.com.arlivre.model.dao;

import java.util.List;

import br.com.arlivre.model.entidade.Usuario;

public interface UsuarioDAO {
	void salvar (Usuario usuario) throws DAOException;
	List<Usuario> buscarTodos();
	List<Usuario> buscarAlunos();
	List<Usuario> buscarProfessores();
	List<Usuario> buscarAdministradores();
	List<Usuario> buscarUsuariosPorIdDoPerfil(Integer id);
	void excluir (Usuario usuario) throws DAOException;
	Usuario buscarPeloId(Integer id) throws DAOException;
	void alterar(Usuario usuario) throws DAOException;
	List<Usuario> buscarPeloNome(String nome);
	List<Usuario> buscarPeloLogin(String login);
	List<Usuario> buscarPeloLoginOuNome(String texto);
	Usuario buscarPeloLoginExato(String login);
}
