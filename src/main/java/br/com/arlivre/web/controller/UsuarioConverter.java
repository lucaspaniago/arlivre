	package br.com.arlivre.web.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arlivre.model.entidade.Usuario;
import br.com.arlivre.model.service.ServiceException;
import br.com.arlivre.model.service.UsuarioService;

@Component ("usuarioConverter")
public class UsuarioConverter implements Converter {
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent componente, String valor) {
		Integer id = null;
		try {
			id = Integer.parseInt(valor);
			return usuarioService.buscarPeloId(id);
		} catch (NumberFormatException | ServiceException e) {
			MensagemUtil.mensagemErro("Erro ao carregar usuário.");
		}
		return id;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent componente, Object valor) {
		if(valor == null || !(valor instanceof Usuario)) {
			return "";
		}
		Usuario usuario = (Usuario) valor;
		return usuario.getId().toString();
	}

}
