//parei AQUI!!!! DEVO REALIZAR OS TESTES TESTUSUARIOSERVICE
package br.com.arlivre.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.arlivre.model.dao.DAOException;
import br.com.arlivre.model.dao.UsuarioDAO;
import br.com.arlivre.model.entidade.Usuario;

@Service
public class UsuarioService {

	// Injeçao de dependencia
	@Autowired
	@Qualifier("usuarioDAOJPA")
	private UsuarioDAO usuarioDAO;

	public void salvar(Usuario usuario) throws ServiceException {
		try {
			if (usuario.getId() != null) {
				usuarioDAO.alterar(usuario);
			} else {
				Usuario usuarioEsperado = usuarioDAO
						.buscarPeloLoginExato(usuario.getLogin());

				if (usuarioEsperado.getId() != null) {
					throw new ServiceException(
							"Usuário já existe. Tente outro.");
				}

				usuarioDAO.salvar(usuario);
			}
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}

	public List<Usuario> buscarTodos() {
		return usuarioDAO.buscarTodos();
	}

	public List<Usuario> buscarAlunos() {
		return usuarioDAO.buscarAlunos();
	}

	public List<Usuario> buscarProfessores() {
		return usuarioDAO.buscarProfessores();
	}

	public List<Usuario> buscarAdministradores() {
		return usuarioDAO.buscarAdministradores();
	}
	
	public List<Usuario> buscarUsuariosPorIdDoPerfil(Integer id){
		return usuarioDAO.buscarUsuariosPorIdDoPerfil(id);
	}

	public void excluir(Usuario usuario) throws ServiceException {
		try {
			usuarioDAO.excluir(usuario);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}

	public Usuario buscarPeloId(Integer id) throws ServiceException {
		try {
			return usuarioDAO.buscarPeloId(id);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}

	}

	public List<Usuario> buscarPeloNome(String nome) {
		return usuarioDAO.buscarPeloNome(nome);
	}

	public List<Usuario> buscarPeloLogin(String login) {
		return usuarioDAO.buscarPeloLogin(login);
	}

	public List<Usuario> buscarPeloLoginOuNome(String texto) {
		return usuarioDAO.buscarPeloLoginOuNome(texto);
	}

	public Usuario buscarPeloLoginExato(String login) {
		return usuarioDAO.buscarPeloLoginExato(login);
	}
}
