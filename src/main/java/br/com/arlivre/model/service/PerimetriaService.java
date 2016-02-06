//parei AQUI!!!! DEVO REALIZAR OS TESTES TESTUSUARIOSERVICE
package br.com.arlivre.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.arlivre.model.dao.DAOException;
import br.com.arlivre.model.dao.PerimetriaDAO;
import br.com.arlivre.model.entidade.Perimetria;
import br.com.arlivre.model.entidade.Usuario;

@Service
public class PerimetriaService{

	// Injeçao de dependencia
	@Autowired
	@Qualifier("perimetriaDAOJPA")
	private PerimetriaDAO perimetriaDAO;
	
//	@Autowired
//	@Qualifier("usuarioDAOJPA")
//	private AvaliacaoDAO usuarioDAO;
	
	public void salvar (Perimetria perimetria) throws ServiceException{
		try {
			if (perimetria.getId() != null) {
				perimetriaDAO.alterar(perimetria);
			} else {
				perimetriaDAO.salvar(perimetria);
			}
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}
	
	public List<Perimetria> buscarTodas(){
			return perimetriaDAO.buscarTodas();
	}
	
	public void excluir(Perimetria perimetria) throws ServiceException {
		try {
				perimetriaDAO.excluir(perimetria);
		} catch(DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}
	
	
	public Perimetria buscarPeloId(Integer id) throws ServiceException {
		try {
			return perimetriaDAO.buscarPeloId(id);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
		
	}
	
	public List<Perimetria> buscarPeloAluno(Usuario aluno){
		return perimetriaDAO.buscarPerimetriaPeloAluno(aluno);
	}
	
}
