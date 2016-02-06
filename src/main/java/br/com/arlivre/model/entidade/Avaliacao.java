package br.com.arlivre.model.entidade;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Avaliacao implements Comparator<Avaliacao>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Usuario aluno;
	@Temporal(value = TemporalType.DATE)
	private Date data;
	@Temporal(value = TemporalType.TIME)
	private Date hora;
	
	private Integer idade;
	
	private Integer fcRepouso = 0;
	
	private Integer pasRepouso = 0;
	
	private Integer padRepouso = 0;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Perimetria perimetria;
	@OneToOne(cascade=CascadeType.ALL)
	private ComposicaoCorporal composicaoCorporal;
	
	private Boolean deletado;
	
	public Avaliacao() {
		this.aluno = new Usuario();
		
		this.deletado = false;
		
		this.data = new Date();
		this.hora = this.data;
		this.perimetria = new Perimetria();
		this.perimetria.recebeAvaliacao(this);
		this.composicaoCorporal = new ComposicaoCorporal();
		this.composicaoCorporal.recebeAvaliacao(this);
	}

	public Avaliacao(Usuario usuario) {
		this.aluno = usuario;
		
		this.deletado = false;
		this.setIdade();
		
		this.data = new Date();
		this.hora = data;
		this.perimetria = new Perimetria();
		this.perimetria.recebeAvaliacao(this);
		this.composicaoCorporal = new ComposicaoCorporal();
		this.composicaoCorporal.recebeAvaliacao(this);
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
		Avaliacao other = (Avaliacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Perimetria getPerimetria() {
		return perimetria;
	}

	public void setPerimetria(Perimetria perimetria) {
		this.perimetria = perimetria;
		perimetria.recebeAvaliacao(this);
	}
	
	public void recebePerimetria(Perimetria perimetria){
		this.perimetria = perimetria;
	}

	public ComposicaoCorporal getComposicaoCorporal() {
		return composicaoCorporal;
	}

	public void setComposicaoCorporal(ComposicaoCorporal composicaoCorporal) {
		this.composicaoCorporal = composicaoCorporal;
	}
	
	public void recebeComposicaoCorporal(ComposicaoCorporal composicaoCorporal){
		this.composicaoCorporal = composicaoCorporal;
	}

	public Integer getId() {
		return id;
	}

	public Usuario getAluno() {
		return aluno;
	}

	public Date getData() {
		return data;
	}

	public Integer getFcRepouso() {
		return fcRepouso;
	}

	public Integer getPasRepouso() {
		return pasRepouso;
	}

	public void setFcRepouso(Integer fcRepouso) {
		this.fcRepouso = fcRepouso;
	}

	public void setPasRepouso(Integer pasRepouso) {
		this.pasRepouso = pasRepouso;
	}
	
	public Integer getPadRepouso() {
		return padRepouso;
	}
	
	public void setPadRepouso(Integer padRepouso) {
		this.padRepouso = padRepouso;
	}

	public Integer getIdade() {
		return idade;
	}
	
	//FAZER UM SET USANDO O PROPRIO ALUNO DAQUI
	
	public void setIdade(Usuario usuario) {		
		this.idade = usuario.getIdade();
	}
	
	public void setIdade(){
		this.idade = this.aluno.getIdade();
	}
	
	public void setIdade(Usuario usuario, Date data) {		
		this.idade = usuario.getIdade(data);
	}
	
	public void setIdade(Integer idade) {		
		this.idade = idade;
	}
	
	public void setAluno(Usuario usuario) {
		this.aluno = usuario;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

//	@Override
//	public int compareTo(Avaliacao avaliacao) {
//		int i = this.data.compareTo(avaliacao.getData());
//		if (i!=0)
//			return i;
//		else
//			return this.hora.compareTo(avaliacao.getHora());
//	}

	@Override
	public int compare(Avaliacao arg0, Avaliacao arg1) {
		return 0;
	}
	
	public void setDeletado(Boolean deletado) {
		this.deletado = deletado;
	}
	
	public Boolean getDeletado() {
		return deletado;
	}
}
