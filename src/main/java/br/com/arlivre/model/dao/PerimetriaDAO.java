package br.com.arlivre.model.dao;

import java.util.List;

import br.com.arlivre.model.entidade.Perimetria;
import br.com.arlivre.model.entidade.Usuario;

public interface PerimetriaDAO {
	void salvar (Perimetria perimetria) throws DAOException;
	List<Perimetria> buscarTodas();
	void excluir (Perimetria perimetria) throws DAOException;
	Perimetria buscarPeloId(Integer id) throws DAOException;
	void alterar(Perimetria perimetria) throws DAOException;
	List<Perimetria> buscarPerimetriaPeloAluno(Usuario aluno);
}
