package br.com.arlivre.model.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 892538849816368738L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 50, nullable = false)
	private String nome;
	@Column(length = 15, nullable = false, unique = true)
	private String login;
	@Column(nullable = false)
	private String senha;
	@Temporal(TemporalType.DATE)
	private Date dataDeNascimento;
	@Column(nullable = false)
	private Boolean ativo;
	private Boolean deletado;
	
	@Column(length = 200)
	private String email;

	@Column(nullable = false)
	private String sexo;
	
	@ManyToOne
	@JoinColumn
	private Perfil perfil;

	// Atributos Extras
//	@Transient
//	private Integer idade;

	
	@Transient
	private List<Avaliacao> avaliacoes;

	public Usuario() {
		this.ativo = true;
		this.deletado = false;
		this.avaliacoes = new ArrayList<Avaliacao>();
//		this.nome = "";
//		this.login = "";
//		this.senha = "";
	}

	public Usuario(String nome, String login, String senha) {
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public Integer getIdade() {
		Calendar dataDeNascimento = new GregorianCalendar();

		dataDeNascimento.setTime(this.dataDeNascimento);

		Calendar dataAtual = Calendar.getInstance();

		Integer idade = dataAtual.get(Calendar.YEAR)
				- dataDeNascimento.get(Calendar.YEAR);

		dataDeNascimento.add(Calendar.YEAR, idade);

		if (dataAtual.before(dataDeNascimento)) {
			idade--;
		}

		return idade;
	}
	
	public Integer getIdade(Date data) {
		Calendar dataDeNascimento = new GregorianCalendar();

		dataDeNascimento.setTime(this.dataDeNascimento);
		
		//SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
		
		Calendar dataAtual = new GregorianCalendar();
		
		dataAtual.setTime(data);

		Integer idade = dataAtual.get(Calendar.YEAR)
				- dataDeNascimento.get(Calendar.YEAR);

		dataDeNascimento.add(Calendar.YEAR, idade);

		if (dataAtual.before(dataDeNascimento)) {
			idade--;
		}

		return idade;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", login=" + login
				+ ", senha=" + senha + "]";
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}
	
	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
	
	public Boolean getDeletado() {
		return deletado;
	}
	
	public void setDeletado(Boolean deletado) {
		this.deletado = deletado;
	}

}
