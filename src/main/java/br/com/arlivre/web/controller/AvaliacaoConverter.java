package br.com.arlivre.web.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arlivre.model.entidade.Avaliacao;
import br.com.arlivre.model.service.AvaliacaoService;
import br.com.arlivre.model.service.ServiceException;

@Component ("avaliacaoConverter")
public class AvaliacaoConverter implements Converter {
	@Autowired
	private AvaliacaoService avaliacaoService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent componente, String valor) {
		Integer id = null;
		try {
			id = Integer.parseInt(valor);
			return avaliacaoService.buscarPeloId(id);
		} catch (NumberFormatException | ServiceException e) {
			MensagemUtil.mensagemErro("Erro ao carregar perfil.");
		}
		return id;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent componente, Object valor) {
		if(valor == null || !(valor instanceof Avaliacao)) {
			return "";
		}
		Avaliacao avaliacao = (Avaliacao) valor;
		return avaliacao.getData().toString();
	}

}
