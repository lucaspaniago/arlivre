package br.com.arlivre.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.arlivre.model.entidade.Avaliacao;
import br.com.arlivre.model.entidade.AvaliacaoComparator;
import br.com.arlivre.model.entidade.Usuario;
import br.com.arlivre.model.service.AvaliacaoService;
import br.com.arlivre.model.service.PerfilService;
import br.com.arlivre.model.service.ServiceException;
import br.com.arlivre.model.service.UsuarioService;

@Controller
// @ManagedBean
public class UsuarioController {

	// Objeto será vinculado com a tela
	private Usuario usuario;

	private Usuario ultimoUsuario;

	// Ponto de injeção de dependência
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private PerfilService perfilService;

	// Lista vinculada à tela
	private List<Usuario> usuarios;

	private String destino;

	private boolean usuarioSalvo;

	private boolean edicao;

	public UsuarioController() {
		this.usuario = new Usuario();
		this.ultimoUsuario = new Usuario();

	}

	@PostConstruct
	public void init() {
		edicao = false;
		// usuarios = usuarioService.buscarTodos();
		if (!(this.usuario.getId() == null)) {
			this.usuario.setAvaliacoes(avaliacaoService
					.buscarAvaliacaoPeloAluno(this.usuario));
		}
	}

	public String novo(Integer id) throws ServiceException {
		this.edicao = false;
		this.usuario = new Usuario();
		this.usuario.setPerfil(perfilService.buscarPeloId(id));
		this.usuarios = usuarioService.buscarUsuariosPorIdDoPerfil(id);

		if (id.equals(3))
			this.destino = "chama-gerenciar-alunos";
		else if (id.equals(3))
			this.destino = "chama-gerenciar-professores";
		else if (id.equals(3))
			this.destino = "chama-gerenciar-administradores";

		return "novo-usuario";
	}

	// Método será chamado por um botão
	public String salvar() {
		try {

			usuarioService.salvar(usuario);
			//MensagemUtil.mensagemInfo("Salvo com sucesso.");

			if (!edicao) {
				usuarios.add(usuario);
			}

			this.ultimoUsuario = this.usuario;
			if (!this.destino.equals("chama-aluno")) {
				System.out.println("entrei no salva normal");
//				this.ultimoUsuario = this.usuario;
				this.usuario = new Usuario();
			}
			

			System.out.println(usuario);
			this.usuarioSalvo = true;

			return this.destino;
		} catch (ServiceException serviceException) {
			System.out.println("entrei na exception");
			MensagemUtil.mensagemErro(serviceException.getMessage());
			return "";
		}
	}

	public void mensagemAlunoSalvo() {
		if (isUsuarioSalvo()) {
			//if (this.usuario.getId() == null) {
			if(!isEdicao()){
				if (this.ultimoUsuario.getSexo().equals("feminino"))
					MensagemUtil
							.mensagemInfo("O novo aluno "
									+ this.ultimoUsuario.getNome()
									+ " foi cadastrado.");
				else
					MensagemUtil
							.mensagemInfo("A nova aluna "
									+ this.ultimoUsuario.getNome()
									+ " foi cadastrada.");
				this.edicao = false;
			} else
				MensagemUtil.mensagemInfo("Dados de " + this.ultimoUsuario.getNome()
						+ " foram salvos.");
		}
		this.usuarioSalvo = false;
	}

	public void mensagemProfessorSalvo() {
		if (isUsuarioSalvo()) {
			//if (this.usuario.getId() == null) {
			if(!isEdicao()){
				if (this.ultimoUsuario.getSexo().equals("feminino"))
					MensagemUtil
							.mensagemInfo("O novo professor "
									+ this.ultimoUsuario.getNome()
									+ " foi cadastrado.");
				else
					MensagemUtil
							.mensagemInfo("A nova professora "
									+ this.ultimoUsuario.getNome()
									+ " foi cadastrada.");
			} else
				MensagemUtil.mensagemInfo("Dados de " + this.ultimoUsuario.getNome()
						+ " foram salvos.");
		}
		this.usuarioSalvo = false;
	}

	public void mensagemAdministradorSalvo() {
		if (isUsuarioSalvo()) {
			//if (this.usuario.getId() == null) {
			if(!isEdicao()){
				if (this.ultimoUsuario.getSexo().equals("feminino"))
					MensagemUtil
							.mensagemInfo("O novo administrador "
									+ this.ultimoUsuario.getNome()
									+ " foi cadastrado.");
				else
					MensagemUtil
							.mensagemInfo("A nova administradora "
									+ this.ultimoUsuario.getNome()
									+ " foi cadastrada.");
			} else
				MensagemUtil.mensagemInfo("Dados de " + this.ultimoUsuario.getNome()
						+ " foram salvos.");
		}
		this.usuarioSalvo = false;
	}

	public void prepararInclusao() throws ServiceException {
		Integer id = this.usuario.getPerfil().getId();
		this.edicao = false;

		this.usuario = new Usuario();
		this.usuario.setPerfil(perfilService.buscarPeloId(id));

		if (id.equals(3))
			this.destino = "chama-gerenciar-alunos";
		else if (id.equals(2))
			this.destino = "chama-gerenciar-professores";
		else if (id.equals(1))
			this.destino = "chama-gerenciar-administradores";

	}

	public void prepararEdicao(SelectEvent evento) {// (Usuario usuario)
		this.edicao = true;
		this.usuario = (Usuario) evento.getObject();// usuario;
	}

	public String editar(Usuario usuario) {
		this.edicao = true;
		this.usuario = usuario;

		if (usuario.getPerfil().getId().equals(3)) {
			this.destino = "chama-gerenciar-alunos";
		}
		
		return "chama-edicao-aluno.xhtml";
	}

	public void editar() {
		this.edicao = true;

		if (this.usuario.getPerfil().getId().equals(3)) {
			this.destino = "chama-aluno";
		}
	}

	public String selecionar(Usuario usuario) throws IOException {
		// this.edicao = true;
		this.usuario = usuario;
		this.usuario.setAvaliacoes(avaliacaoService
				.buscarAvaliacaoPeloAluno(this.usuario));

		AvaliacaoComparator avaliacaoComparator = new AvaliacaoComparator();
		Collections.sort(this.usuario.getAvaliacoes(), avaliacaoComparator);

		return "chama-aluno";
	}

	public void comparar() {
		System.out.println("entrei aqui\n");
		// System.out.println("comparadas: " +
		// this.dualListAvaliacoes.getTarget().toString());
	}

	public void deletar(Usuario usuario) throws ServiceException {
		usuario.setDeletado(true);
		this.usuarios.remove(usuario);
		usuarioService.salvar(usuario);

		this.ultimoUsuario = usuario;

		if (this.ultimoUsuario.getSexo().equalsIgnoreCase("feminino"))
			MensagemUtil.mensagemInfo(this.ultimoUsuario.getNome()
					+ " excluída.");
		else
			MensagemUtil.mensagemInfo(this.ultimoUsuario.getNome()
					+ " excluído.");
	}

	public void excluir(Usuario usuario) {
		try {
			usuarios.remove(usuario);

			this.ultimoUsuario = usuario;

			usuarioService.excluir(usuario);

			if (this.ultimoUsuario.getSexo().equalsIgnoreCase("feminino"))
				MensagemUtil.mensagemInfo(this.ultimoUsuario.getNome()
						+ " excluída definitivamente.");
			else
				MensagemUtil.mensagemInfo(this.ultimoUsuario.getNome()
						+ " excluído definitivamente.");

		} catch (ServiceException e) {
			MensagemUtil.mensagemErro("Erro ao excluir. Tente novamente.");
		}
	}

	public void deletar(Avaliacao avaliacao) throws ServiceException {
		avaliacao.setDeletado(true);
		this.usuario.getAvaliacoes().remove(avaliacao);
		this.avaliacaoService.salvar(avaliacao);
		MensagemUtil.mensagemInfo("Avaliação excluída.");
	}

	public void excluir(Avaliacao avaliacao) {
		try {
			avaliacaoService.excluir(avaliacao);

			this.usuario.getAvaliacoes().remove(avaliacao);
			MensagemUtil.mensagemInfo("Avaliação excluída definitivamente.");
		} catch (ServiceException e) {
			MensagemUtil.mensagemErro("Erro ao excluir. Tente novamente.");
		}
	}

	public String gerenciarAlunos() {

		this.usuarios = this.usuarioService.buscarAlunos();
		System.out.println("passei aqui gerenciar alunos ");

		return "chama-gerenciar-alunos";
	}

	public String gerenciarProfessores() {

		this.usuarios = this.usuarioService.buscarProfessores();

		return "chama-gerenciar-professores";
	}

	public String gerenciarAdministradores() {

		this.usuarios = this.usuarioService.buscarAdministradores();

		return "chama-gerenciar-administradores";
	}

	public void setPerfilDoUsuario() {
		System.out.println("entraivos aqui"
				+ this.usuario.getPerfil().getDescricao());
		this.usuarios = usuarioService.buscarUsuariosPorIdDoPerfil(this.usuario
				.getPerfil().getId());
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public boolean isEdicao() {
		return edicao;
	}

	public List<Usuario> nomes(String letras) {
		// List<String> palavras = Arrays.asList("Fabio", "Fabio Oliverira",
		// "Debora", "Lucas", "Marcelo");
		this.usuarios = this.usuarioService.buscarTodos();
		List<Usuario> usuariosEncontrados = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			if (usuario.toString().contains(letras)) {
				usuariosEncontrados.add(usuario);
			}
		}
		return usuariosEncontrados;
	}

	public Integer numeroDeAvaliacoes() {
		return this.usuario.getAvaliacoes().size();
	}

	public String getPerfilNoPlural() {
		if (this.usuario.getPerfil().getId().equals(3))
			return "Alunos";
		else if (this.usuario.getPerfil().getId().equals(1))
			return "Administradores";
		return "Professores";
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public boolean isUsuarioSalvo() {
		return usuarioSalvo;
	}

	public void setUsuarioSalvo(boolean alunoSalvo) {
		this.usuarioSalvo = alunoSalvo;
	}

	public Usuario getUltimoUsuario() {
		return ultimoUsuario;
	}

	public void setUltimoUsuario(Usuario ultimoUsuario) {
		this.ultimoUsuario = ultimoUsuario;
	}

	public void setAlunoDaAvaliacao(SelectEvent event) {

		this.usuario = (Usuario) event.getObject();
		this.usuario.setAvaliacoes(avaliacaoService
				.buscarAvaliacaoPeloAluno(this.usuario));

		System.out.println("entrei lokao");

		// if (id.equals(3))
		// this.destino = "chama-gerenciar-alunos";
		// else if (id.equals(3))
		// this.destino = "chama-gerenciar-professores";
		// else if (id.equals(3))
		// this.destino = "chama-gerenciar-administradores";
		// this.destino = "";
	}

}
