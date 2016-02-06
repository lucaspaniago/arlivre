package br.com.arlivre.web.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.arlivre.model.entidade.Perfil;
import br.com.arlivre.model.service.PerfilService;

@Controller
public class PerfilController {

	private Perfil perfil;

	private List<Perfil> perfis;

	@Autowired
	private PerfilService perfilService;

	@Autowired
	public PerfilController(PerfilService perfilService) {
		this.perfilService = perfilService;
	}

	@PostConstruct
	public void init() {
		perfil = new Perfil();
		perfis = perfilService.buscarTodos();
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public Boolean isAdministrador(Perfil perfil){
		if (perfil.getDescricao().equals("Administrador"))
			return true;
		return false;
	}
	
	public Boolean isAluno(Perfil perfil){
		if (perfil.getDescricao().equals("Aluno"))
			return true;
		return false;
	}
	
	public Boolean isProfessor(Perfil perfil){
		if (perfil.getDescricao().equals("Professor"))
			return true;
		return false;
	}
	
}
