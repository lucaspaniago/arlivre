package br.com.arlivre.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import br.com.arlivre.model.entidade.Perimetria;
import br.com.arlivre.model.entidade.Usuario;

@Repository
public class PerimetriaDAOJPA implements PerimetriaDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.NESTED)
	public void salvar(Perimetria perimetria) throws DAOException {
		// EntityTransaction transacao = entityManager.getTransaction();
		// transacao.begin();
		// entityManager.merge(usuario);
		// transacao.commit();

		try {
			entityManager.persist(perimetria);
		} catch (Exception e) {
			throw new DAOException("Não foi possível salvar.", e);
		}
	}

	@Override @SuppressWarnings("unchecked")
	public List<Perimetria> buscarTodas(){
		return entityManager.createQuery("FROM Perimetria").getResultList();
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void excluir(Perimetria perimetria) throws DAOException {
		try {
			entityManager.remove(entityManager.getReference(Perimetria.class,
					perimetria.getId()));
		} catch (Exception e) {
			throw new DAOException("Não foi possível excluir.", e);
		}
	}

	@Override
	public Perimetria buscarPeloId(Integer id) {
		Perimetria perimetriaRetorno = (Perimetria) entityManager
				.find(Perimetria.class, id);

		if (perimetriaRetorno != null) {
			return perimetriaRetorno;
		} else {
			return new Perimetria();
		}
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void alterar(Perimetria perimetria) throws DAOException {
		try {
			entityManager.merge(perimetria);
		} catch (Exception e) {
			throw new DAOException("Não foi possível salvar.", e);
		}
	}

	@Override @SuppressWarnings("unchecked")
	public List<Perimetria> buscarPerimetriaPeloAluno(Usuario aluno) {
		
		String jpql = "SELECT perimetria FROM Perimetria perimetria WHERE perimetria.id = :idParametro";
		Query consulta = entityManager.createQuery(jpql);
		consulta.setParameter("idParametro", aluno.getId().toString());

		return consulta.getResultList();
	}

}
