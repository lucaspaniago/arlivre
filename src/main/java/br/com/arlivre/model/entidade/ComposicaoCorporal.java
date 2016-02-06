package br.com.arlivre.model.entidade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.arlivre.model.service.ServiceException;

@Entity
public class ComposicaoCorporal {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@OneToOne(mappedBy = "composicaoCorporal", cascade = CascadeType.ALL)
	private Avaliacao avaliacao;
	@Column(nullable = false, precision = 4, scale = 2)
	private Double peso = 0.0;
	private Double tricipital = 0.0;
	private Double subescapular = 0.0;
	private Double abdominal = 0.0;
	private Double coxaMedial = 0.0;
	private Double panturrilhaMedial = 0.0;
	private Double peitoral = 0.0;
	private Double bicipital = 0.0;
	private Double axilarMedia = 0.0;
	private Double supraIliaca = 0.0;

	@Column(precision = 3, scale = 2)
	private Double imc = 0.0;
	@Column(precision = 4, scale = 2)
	private Double percentualDeGordura = 0.0;
	@Column(precision = 4, scale = 2)
	private Double massaMagra = 0.0;
	@Column(precision = 4, scale = 2)
	private Double percentualMuscular = 0.0;
	@Column(precision = 4, scale = 2)
	private Double massaGorda = 0.0;
	@Column(precision = 4, scale = 2)
	private Double massaMuscular = 0.0;

	public ComposicaoCorporal() {
	}

//	public ComposicaoCorporal(Avaliacao avaliacao, Double peso,
//			Double tricipital, Double subescapular, Double abdominal,
//			Double coxaMedial, Double peitoral, Double axilarMedia,
//			Double supraIliaca) {
//		super();
//		this.avaliacao = avaliacao;
//		this.peso = peso;
//		this.tricipital = tricipital;
//		this.subescapular = subescapular;
//		this.abdominal = abdominal;
//		this.coxaMedial = coxaMedial;
//		this.peitoral = peitoral;
//		this.axilarMedia = axilarMedia;
//		this.supraIliaca = supraIliaca;
//	}
//
//	public ComposicaoCorporal(Avaliacao avaliacao, Double peso,
//			Double tricipital, Double subescapular, Double abdominal,
//			Double coxaMedial, Double panturrilhaMedial, Double peitoral,
//			Double bicipital, Double axilarMedia, Double supraIliaca) {
//		super();
//		this.avaliacao = avaliacao;
//		this.peso = peso;
//		this.tricipital = tricipital;
//		this.subescapular = subescapular;
//		this.abdominal = abdominal;
//		this.coxaMedial = coxaMedial;
//		this.panturrilhaMedial = panturrilhaMedial;
//		this.peitoral = peitoral;
//		this.bicipital = bicipital;
//		this.axilarMedia = axilarMedia;
//		this.supraIliaca = supraIliaca;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComposicaoCorporal other = (ComposicaoCorporal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setTricipital(Double tricipital) {
		this.tricipital = tricipital;
	}

	public void setSubescapular(Double subescapular) {
		this.subescapular = subescapular;
	}

	public void setAbdominal(Double abdominal) {
		this.abdominal = abdominal;
	}

	public void setCoxaMedial(Double coxaMedial) {
		this.coxaMedial = coxaMedial;
	}

	public void setPanturrilhaMedial(Double panturrilhaMedial) {
		this.panturrilhaMedial = panturrilhaMedial;
	}

	public void setPeitoral(Double peitoral) {
		this.peitoral = peitoral;
	}

	public void setBicipital(Double bicipital) {
		this.bicipital = bicipital;
	}

	public void setAxilarMedia(Double axilarMedia) {
		this.axilarMedia = axilarMedia;
	}

	public void setSupraIliaca(Double supraIliaca) {
		this.supraIliaca = supraIliaca;
	}

	public Double getImc() {
		return imc;
	}

	public Double setImc() {
		Double altura = this.avaliacao.getPerimetria().getAltura() / 100;
		if (!altura.equals(0.0)) {
			return this.imc = this.peso / (altura * altura);
		} else
			return this.imc = 0.0;
	}

	public void setMassaMagra() {
		this.massaMagra = this.peso - this.massaGorda;
	}
	
	public void setMassaMagra(Double massaMagra) {
		this.massaMagra = massaMagra;
	}

	public void setMassaGorda() {
		this.massaGorda = (this.peso * this.percentualDeGordura) / (100);
	}
	
	public void setMassaGorda(Double massaGorda) {
		this.massaGorda = massaGorda;
	}
	
	public Double calculaMassaGorda (){
		return this.massaGorda = (this.peso * this.percentualDeGordura) / 100;
	}
	
	public Double calculaMassaMagra (){
		return this.massaMagra = this.peso - this.massaGorda;
	}
	
	public Double calculaMassaMuscular(){
		return this.massaMuscular = (this.peso * this.percentualMuscular)/100;
	}

	public void setPercentualDeGordura(Double percentual) {
		this.percentualDeGordura = percentual;
	}

	// Utilizo apenas o método Pollock 7 dobras
	public Double calcularPercentualDeGordura() throws ServiceException {
		return this.PollockSeteDobras();
	}

	public Double PollockSeteDobras() throws ServiceException {
		List<String> dobras = new ArrayList<String>();

		if (subescapular.equals(0.0)) {
			dobras.add("subescapular");
		}
		if (axilarMedia.equals(0.0)) {
			dobras.add("axilar média");
		}
		if (tricipital.equals(0.0)) {
			dobras.add("tricipital");
		}
		if (coxaMedial.equals(0.0)) {
			dobras.add("coxa medial");
		}
		if (supraIliaca.equals(0.0)) {
			dobras.add("supra ilíaca");
		}
		if (abdominal.equals(0.0)) {
			dobras.add("abdominal");
		}
		if (peitoral.equals(0.0)) {
			dobras.add("peitoral");
		}

		if (dobras.size() == 0) {
			
			Double st = subescapular + axilarMedia + tricipital + coxaMedial
					+ supraIliaca + abdominal + peitoral;
			Double dc;

			// MASCULINO
			if (avaliacao.getAluno().getSexo().equalsIgnoreCase("Masculino")) {
				System.out.println("é masculino");
				dc = 1.11200000
						- ((0.00043499 * (st)) + (0.00000055 * (st * st)))
						- (0.0002882 * (avaliacao.getIdade()));
			}// FEMININO
			else {
				System.out.println("é feminino");
				dc = 1.0970 - ((0.00046971 * (st)) + (0.00000056 * (st * st)))
						- (0.00012828 * (avaliacao.getIdade()));
			}

			System.out.println("soma das doras: " + st + "\ndc: " + dc
					+ "\nresultado:" + ((4.95 / dc) - 4.50) * 100);

			return ((4.95 / dc) - 4.50) * 100;// Utilizando
												// fórmula de
												// SIRI

		} else{
			String frase = "Para calcular o percentual de gordura preencha as seguintes dobras: ";
			for(String dobra : dobras){
				frase = frase + dobra;
				if ((dobras.size() - 1) == dobras.indexOf(dobra))
					frase = frase + ".";
				else
					frase = frase + ", ";
			}
			throw new ServiceException(frase);
		}
			
	}

	public Double getTricipital() {
		return tricipital;
	}

	public Double getSubescapular() {
		return subescapular;
	}

	public Double getAbdominal() {
		return abdominal;
	}

	public Double getCoxaMedial() {
		return coxaMedial;
	}

	public Double getPanturrilhaMedial() {
		return panturrilhaMedial;
	}

	public Double getPeitoral() {
		return peitoral;
	}

	public Double getBicipital() {
		return bicipital;
	}

	public Double getAxilarMedia() {
		return axilarMedia;
	}

	public Double getSupraIliaca() {
		return supraIliaca;
	}

	public Double getPercentualDeGordura() {
		return percentualDeGordura;
	}

	public Double getMassaMagra() {
		return massaMagra;
	}

	public Double getMassaGorda() {
		return massaGorda;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getPeso() {
		return peso;
	}

	public void recebeAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	public void setPercentualMuscular(Double massaMuscular) {
		this.percentualMuscular = massaMuscular;
	}
	
	public Double getPercentualMuscular() {
		return percentualMuscular;
	}
	
	public void setMassaMuscular(Double massaMuscular) {
		this.massaMuscular = massaMuscular;
	}
	
	public Double getMassaMuscular() {
		return massaMuscular;
	}

}
