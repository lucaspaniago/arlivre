package br.com.arlivre.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import br.com.arlivre.model.entidade.Avaliacao;
import br.com.arlivre.model.entidade.Usuario;

@Repository
public class AvaliacaoDAOJPA implements AvaliacaoDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.NESTED)
	public void salvar(Avaliacao avaliacao) throws DAOException {
		// EntityTransaction transacao = entityManager.getTransaction();
		// transacao.begin();
		// entityManager.merge(usuario);
		// transacao.commit();

		try {
			entityManager.persist(avaliacao);
		} catch (Exception e) {
			throw new DAOException("Não foi possível salvar.", e);
		}
	}

	@Override @SuppressWarnings("unchecked")
	public List<Avaliacao> buscarTodas(){
		return entityManager.createQuery("SELECT avaliacao FROM Avaliacao avaliacao WHERE avaliacao.deletado = FALSE").getResultList();
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void excluir(Avaliacao avaliacao) throws DAOException {
		try {
			entityManager.remove(entityManager.getReference(Avaliacao.class,
					avaliacao.getId()));
		} catch (Exception e) {
			throw new DAOException("Não foi possível excluir.", e);
		}
	}

	@Override
	public Avaliacao buscarPeloId(Integer id) {
		Avaliacao avaliacaoRetorno = (Avaliacao) entityManager
				.find(Avaliacao.class, id);

		if (avaliacaoRetorno != null) {
			return avaliacaoRetorno;
		} else {
			return new Avaliacao();
		}
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void alterar(Avaliacao avaliacao) throws DAOException {
		try {
			entityManager.merge(avaliacao);
		} catch (Exception e) {
			throw new DAOException("Não foi possível salvar.", e);
		}
	}

	@Override @SuppressWarnings("unchecked")
	public List<Avaliacao> buscarAvaliacaoPeloAluno(Usuario aluno) {
		String jpql = "SELECT a FROM Avaliacao a WHERE a.aluno = :alunoIdParametro AND a.deletado = FALSE";
		Query consulta = entityManager.createQuery(jpql);
		consulta.setParameter("alunoIdParametro", aluno);
		
		return (List<Avaliacao>) consulta.getResultList();
	}

}
