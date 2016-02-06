package br.com.arlivre.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.ui.context.Theme;

import br.com.arlivre.model.entidade.Avaliacao;
import br.com.arlivre.model.entidade.Usuario;
import br.com.arlivre.model.service.AvaliacaoService;

@ManagedBean
public class PickListAvaliacoes {

	@ManagedProperty("#{avaliacaoService}")
	private AvaliacaoService avaliacaoService;

	private DualListModel<String> cities;
	private DualListModel<Avaliacao> avaliacoes;


	@PostConstruct
	public void init() {
		// Cities
		List<String> citiesSource = new ArrayList<String>();
		List<String> citiesTarget = new ArrayList<String>();

		citiesSource.add("San Francisco");
		citiesSource.add("London");
		citiesSource.add("Paris");
		citiesSource.add("Istanbul");
		citiesSource.add("Berlin");
		citiesSource.add("Barcelona");
		citiesSource.add("Rome");

		cities = new DualListModel<String>(citiesSource, citiesTarget);

		// Themes
//		List<Avaliacao> avaliacoesSource = avaliacaoService
//				.buscarAvaliacaoPeloAluno(usuario);
//		List<Avaliacao> avaliacoesTarget = new ArrayList<Avaliacao>();

//		this.avaliacoes = new DualListModel<Avaliacao>(avaliacoesSource,
//				avaliacoesTarget);

	}
	
	public void inicializaAvaliacoes (Usuario usuario){
		List<Avaliacao> avaliacoesSource = avaliacaoService
				.buscarAvaliacaoPeloAluno(usuario);
		List<Avaliacao> avaliacoesTarget = new ArrayList<Avaliacao>();

		this.avaliacoes = new DualListModel<Avaliacao>(avaliacoesSource,
				avaliacoesTarget);
	}

	public DualListModel<String> getCities() {
		return cities;
	}

	public void setCities(DualListModel<String> cities) {
		this.cities = cities;
	}

	public AvaliacaoService getAvaliacaoService() {
		return avaliacaoService;
	}

	// public ThemeService getService() {
	// return service;
	// }

	public void setAvaliacaoService(AvaliacaoService avaliacaoService) {
		this.avaliacaoService = avaliacaoService;
	}

	// public void setService(ThemeService service) {
	// this.service = service;
	// }
	
	public DualListModel<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}
	// public DualListModel<Theme> getThemes() {
	// return themes;
	// }
	
	public void setAvaliacoes(DualListModel<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
	
	// public void setThemes(DualListModel<Theme> themes) {
	// this.themes = themes;
	// }

	 public void onTransfer(TransferEvent event) {
	 StringBuilder builder = new StringBuilder();
	 for(Object item : event.getItems()) {
	 builder.append(((Theme) item).getName()).append("<br />");
	 }
	
	 FacesMessage msg = new FacesMessage();
	 msg.setSeverity(FacesMessage.SEVERITY_INFO);
	 msg.setSummary("Items Transferred");
	 msg.setDetail(builder.toString());
	
	 FacesContext.getCurrentInstance().addMessage(null, msg);
	 }

	public void onSelect(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Item Selected", event.getObject().toString()));
	}

	public void onUnselect(UnselectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Item Unselected", event.getObject().toString()));
	}

	public void onReorder() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"List Reordered", null));
	}
}