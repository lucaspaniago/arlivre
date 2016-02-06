package br.com.arlivre.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import br.com.arlivre.model.entidade.Usuario;

@Repository
public class UsuarioDAOJPA implements UsuarioDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.NESTED)
	public void salvar(Usuario usuario) throws DAOException {
		// EntityTransaction transacao = entityManager.getTransaction();
		// transacao.begin();
		// entityManager.merge(usuario);
		// transacao.commit();

		try {
			entityManager.persist(usuario);
		} catch (Exception e) {
			throw new DAOException("Não foi possível salvar.", e);
		}
	}
	
	@Override @SuppressWarnings("unchecked")
	public List<Usuario> buscarTodos(){
		return entityManager.createQuery("SELECT usuario FROM Usuario usuario WHERE usuario.deletado = FALSE").getResultList();
	}
	
	@Override @SuppressWarnings("unchecked")
	public List<Usuario> buscarAlunos(){
		return entityManager.createQuery("SELECT usuario FROM Usuario usuario WHERE usuario.deletado = FALSE AND usuario.perfil.id = 3").getResultList();
	}
	
	@Override @SuppressWarnings("unchecked")
	public List<Usuario> buscarProfessores(){
		return entityManager.createQuery("SELECT usuario FROM Usuario usuario WHERE usuario.deletado = FALSE AND usuario.perfil.id = 2").getResultList();
	}
	
	@Override @SuppressWarnings("unchecked")
	public List<Usuario> buscarAdministradores(){
		return entityManager.createQuery("SELECT usuario FROM Usuario usuario WHERE usuario.deletado = FALSE AND usuario.perfil.id = 1").getResultList();
	}
	
	@Override @SuppressWarnings("unchecked")
	public List<Usuario> buscarUsuariosPorIdDoPerfil(Integer id){
		return entityManager.createQuery("SELECT usuario FROM Usuario usuario WHERE usuario.deletado = FALSE AND usuario.perfil.id = " + id).getResultList();
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void excluir(Usuario usuario) throws DAOException {
		try {
			entityManager.remove(entityManager.getReference(Usuario.class,
					usuario.getId()));
		} catch (Exception e) {
			throw new DAOException("Não foi possível excluir.", e);
		}
	}

	@Override
	public Usuario buscarPeloId(Integer id) {
		Usuario usuarioRetorno = (Usuario) entityManager
				.find(Usuario.class, id);

		if (usuarioRetorno != null) {
			return usuarioRetorno;
		} else {
			return new Usuario();
		}
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void alterar(Usuario usuario) throws DAOException {
		try {
			entityManager.merge(usuario);
		} catch (Exception e) {
			throw new DAOException("Não foi possível salvar.", e);
		}
	}

	@Override @SuppressWarnings("unchecked")
	public List<Usuario> buscarPeloNome(String nome) {
		String jpql = "SELECT usuario FROM Usuario usuario WHERE upper(usuario.nome) LIKE TRIM(:nomeParametro)";
		Query consulta = entityManager.createQuery(jpql);
		consulta.setParameter("nomeParametro", "%" + nome.toUpperCase() + "%");

		return consulta.getResultList();
	}

	@Override @SuppressWarnings("unchecked")
	public List<Usuario> buscarPeloLogin(String login) {
		String jpql = "SELECT usuario FROM Usuario usuario WHERE upper(usuario.login) LIKE TRIM(:loginParametro)";
		Query consulta = entityManager.createQuery(jpql);
		consulta.setParameter("loginParametro", "%" + login.toUpperCase() + "%");
		try {
			return consulta.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public Usuario buscarPeloLoginExato(String login) {
		String jpql = "SELECT usuario FROM Usuario usuario WHERE upper(usuario.login) = TRIM(:loginParametro)";
		Query consulta = entityManager.createQuery(jpql);
		consulta.setParameter("loginParametro", login.toUpperCase());
		try {
			// tenta retornar um único usuário
			return (Usuario) consulta.getSingleResult();
		} catch (NoResultException e) {
			// se não consegue achar nenhum resultado para aquele login retorna
			// usuário vazio
			return new Usuario();
		}
		catch(Exception e){
			//throw new DAOException("Algum erro ocorreu ao buscar esse ID");
			return new Usuario();
		}
	}

	@Override @SuppressWarnings("unchecked")
	public List<Usuario> buscarPeloLoginOuNome(String texto){

		String jpql = "SELECT usuario FROM Usuario usuario WHERE (upper(usuario.login) LIKE TRIM(:loginParametro) OR upper(usuario.nome) LIKE TRIM(:nomeParametro))";
		Query consulta = entityManager.createQuery(jpql);
		consulta.setParameter("loginParametro", "%" + texto.toUpperCase() + "%");
		consulta.setParameter("nomeParametro", "%" + texto.toUpperCase() + "%");
		try {
			return consulta.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

}
