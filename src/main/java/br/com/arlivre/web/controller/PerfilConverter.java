package br.com.arlivre.web.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arlivre.model.entidade.Perfil;
import br.com.arlivre.model.service.PerfilService;
import br.com.arlivre.model.service.ServiceException;

@Component ("perfilConverter")
public class PerfilConverter implements Converter {
	@Autowired
	private PerfilService perfilService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent componente, String valor) {
		Integer id = null;
		try {
			id = Integer.parseInt(valor);
			return perfilService.buscarPeloId(id);
		} catch (NumberFormatException | ServiceException e) {
			MensagemUtil.mensagemErro("Erro ao carregar perfil.");
		}
		return id;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent componente, Object valor) {
		if(valor == null || !(valor instanceof Perfil)) {
			return "";
		}
		Perfil perfil = (Perfil) valor;
		return perfil.getId().toString();
	}

}
