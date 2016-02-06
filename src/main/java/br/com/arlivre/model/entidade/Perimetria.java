package br.com.arlivre.model.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Perimetria {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@OneToOne(mappedBy="perimetria", cascade=CascadeType.ALL)
	private Avaliacao avaliacao;
	@Column(nullable = false, precision =3, scale =2)
	private Double altura = 0.0;
	private Double panturrilhaDireita = 0.0;
	private Double panturrilhaEsquerda = 0.0;
	private Double coxaDireita = 0.0;
	private Double coxaEsquerda = 0.0;
	private Double quadril = 0.0;
	private Double cintura = 0.0;
	private Double abdomen = 0.0;
	private Double toraxica = 0.0;
	private Double cinturaEscapular = 0.0;
	private Double pescoco = 0.0;
	private Double bracoEsquerdo = 0.0;
	private Double bracoDireito = 0.0;
	private Double antebracoDireito = 0.0;
	private Double antebracoEsquerdo = 0.0;

	@Column (precision =3, scale =2)
	private Double icq;

	public Perimetria() {
	}

	public Perimetria(Avaliacao avaliacao, Double altura, Double quadril,
			Double cintura) {
		super();
		this.avaliacao = avaliacao;
		this.altura = altura;
		this.quadril = quadril;
		this.cintura = cintura;
	}

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
		Perimetria other = (Perimetria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getPanturrilhaDireita() {
		return panturrilhaDireita;
	}

	public void setPanturrilhaDireita(Double panturrilhaDireita) {
		this.panturrilhaDireita = panturrilhaDireita;
	}
	
	public Double getPanturrilhaEsquerda() {
		return panturrilhaEsquerda;
	}
	
	public void setPanturrilhaEsquerda(Double panturrilhaEsquerda) {
		this.panturrilhaEsquerda = panturrilhaEsquerda;
	}

	public Double getCoxaDireita() {
		return coxaDireita;
	}
	
	public void setCoxaDireita(Double coxaDireita) {
		this.coxaDireita = coxaDireita;
	}

	public void setCoxaEsquerda(Double coxaEsquerda) {
		this.coxaEsquerda = coxaEsquerda;
	}
	
	public Double getCoxaEsquerda() {
		return coxaEsquerda;
	}

	public Double getQuadril() {
		return quadril;
	}

	public void setQuadril(Double quadril) {
		this.quadril = quadril;
	}

	public Double getCintura() {
		return cintura;
	}

	public void setCintura(Double cintura) {
		this.cintura = cintura;
	}

	public Double getAbdomen() {
		return abdomen;
	}

	public void setAbdomen(Double abdomen) {
		this.abdomen = abdomen;
	}

	public Double getToraxica() {
		return toraxica;
	}

	public void setToraxica(Double toraxica) {
		this.toraxica = toraxica;
	}

	public Double getCinturaEscapular() {
		return cinturaEscapular;
	}

	public void setCinturaEscapular(Double cinturaEscapular) {
		this.cinturaEscapular = cinturaEscapular;
	}

	public Double getPescoco() {
		return pescoco;
	}

	public void setPescoco(Double pescoco) {
		this.pescoco = pescoco;
	}

	public Double getBracoEsquerdo() {
		return bracoEsquerdo;
	}

	public void setBracoEsquerdo(Double bracoEsquerdo) {
		this.bracoEsquerdo = bracoEsquerdo;
	}

	public Double getBracoDireito() {
		return bracoDireito;
	}

	public void setBracoDireito(Double bracoDireito) {
		this.bracoDireito = bracoDireito;
	}

	public Double getAntebracoDireito() {
		return antebracoDireito;
	}

	public void setAntebracoDireito(Double antebracoDireito) {
		this.antebracoDireito = antebracoDireito;
	}

	public Double getAntebracoEsquerdo() {
		return antebracoEsquerdo;
	}

	public void setAntebracoEsquerdo(Double antebracoEsquerdo) {
		this.antebracoEsquerdo = antebracoEsquerdo;
	}

	public Double getIcq() {
		return icq;
	}

	public Double setIcq() {
		if (!quadril.equals(0.0))
			return this.icq = cintura / quadril;
		else
			return this.icq = 0.00;
	}

	public Integer getId() {
		return id;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}
	
	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
		avaliacao.recebePerimetria(this);
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void recebeAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}
}
