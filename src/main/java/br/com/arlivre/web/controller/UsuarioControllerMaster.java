package br.com.arlivre.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

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
public class UsuarioControllerMaster {

	// Objeto será vinculado com a tela
	private Usuario usuario;
	
	private Usuario busca;

	private Usuario ultimoUsuarioAlterado;

	private Avaliacao avaliacao;
	
	private Avaliacao ultimaAvaliacaoAlterada;

	// Ponto de injeção de dependência
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private PerfilService perfilService;

	// Lista vinculada à tela
	private List<Usuario> usuarios;

	private List<Avaliacao> avaliacoes;

	private List<Avaliacao> avaliacoesParaComparar;

	private String destino;
	
	private boolean usuarioSalvo;

	private boolean avaliacaoSalva;

	private boolean edicaoDeUsuario;

	private boolean edicaoDeAvaliacao;

	private boolean avaliacaoTemAluno;

	private Graficos graficos;

	public UsuarioControllerMaster() {
		this.usuario = new Usuario();
		this.busca = new Usuario();
		this.busca.setId(-1);
		this.ultimoUsuarioAlterado = this.usuario;
		this.usuarios = new ArrayList<Usuario>();
		this.avaliacao = new Avaliacao();
		this.avaliacoes = new ArrayList<Avaliacao>();
		this.avaliacoesParaComparar = this.avaliacoes;
		this.graficos = new Graficos();
	}

	@PostConstruct
	public void init() {
		this.edicaoDeUsuario = false;
		this.edicaoDeAvaliacao = false;
		// usuarios = usuarioService.buscarTodos();
		// if (!(this.usuario.getId() == null)) {
		// this.usuario.setAvaliacoes(avaliacaoService
		// .buscarAvaliacaoPeloAluno(this.usuario));
		// }
	}

	/*-----USUÁRIO-----*/

	/* NOVOS USUÁRIOS */
	public String novoUsuario(String destino) throws ServiceException{
		if (this.usuario.getPerfil().equals(3)){
			return this.novoAluno(destino);
		}
		else if (this.usuario.getPerfil().equals(2)){
			return this.novoProfessor(destino);
		}
		else
			return this.novoAdministrador(destino);
	}
	
	public String novoAluno(String destino) throws ServiceException {
		// Preparação do novo Aluno
		this.usuario = new Usuario();
		this.edicaoDeUsuario = false;
		this.usuario.setPerfil(perfilService.buscarPeloId(3));

		// Preparação do destino
		this.usuarios = usuarioService.buscarUsuariosPorIdDoPerfil(3);
		this.destino = destino;

		return "novo-usuario";
	}

	public String novoProfessor(String destino) throws ServiceException {
		// Preparação do novo Professor
		this.usuario = new Usuario();
		this.edicaoDeUsuario = false;
		this.usuario.setPerfil(perfilService.buscarPeloId(2));

		// Preparação do destino
		this.usuarios = usuarioService.buscarUsuariosPorIdDoPerfil(2);
		this.destino = destino;

		return "novo-usuario";
	}

	public String novoAdministrador(String destino) throws ServiceException {
		// Preparação do novo Administrador
		this.usuario = new Usuario();
		this.edicaoDeUsuario = false;
		this.usuario.setPerfil(perfilService.buscarPeloId(1));

		// Preparação do destino
		this.usuarios = usuarioService.buscarUsuariosPorIdDoPerfil(1);
		this.destino = destino;

		return "novo-usuario";
	}

	/*
	 * Verificar a possibilidade de enxugar novoAluno, novoProfessor,
	 * novoAdministrador em novoUsuario
	 */

	/* EDITAR USUÁRIOS */
	public String editarAluno(Usuario usuario, String destino) {
		//Prepara a pagina de edição
		this.edicaoDeUsuario = true;
		this.setUsuario(usuario);
		this.usuarios = usuarioService.buscarUsuariosPorIdDoPerfil(this.usuario.getPerfil().getId());

		this.destino = destino;

		return "chama-edicao-aluno";
	}

	public String editarAluno() {
		//Prepara pagina de edição
		this.edicaoDeUsuario = true;
		this.usuarios = usuarioService.buscarUsuariosPorIdDoPerfil(this.usuario.getPerfil().getId());

		this.destino = "chama-aluno";

		return "chama-edicao-aluno";
	}

	public String editarAluno(SelectEvent evento) {
		//Prepara pagina de edição
		this.edicaoDeUsuario = true;
		this.setUsuario((Usuario) evento.getObject());
		this.usuarios = usuarioService.buscarUsuariosPorIdDoPerfil(this.usuario.getPerfil().getId());
		
		this.destino = "chama-aluno";

		return "chama-edicao-aluno";
	}

	/* SALVAR USUÁRIOS */
	public String salvarUsuario() {
		try {

			usuarioService.salvar(this.usuario);
			this.usuarioSalvo = true;

			if (!this.edicaoDeUsuario) {
				usuarios.add(usuario);
			}

			this.ultimoUsuarioAlterado = this.usuario;

			if (!this.destino.equals("chama-aluno")) {
				this.usuario = new Usuario();
			}

			System.out.println(usuario);
			return this.destino;
		} catch (ServiceException serviceException) {
			System.out.println("entrei na exception");
			MensagemUtil.mensagemErro(serviceException.getMessage());
			return "";
		}
	}

	public void mensagemAlunoSalvo() {
		if (this.ultimoUsuarioAlterado.getPerfil() != null){
			if (isUsuarioSalvo() && this.ultimoUsuarioAlterado.getPerfil().getId().equals(3)) {
				if (!isEdicaoDeUsuario()) {
					if (this.ultimoUsuarioAlterado.getSexo().equalsIgnoreCase("Feminino"))
						MensagemUtil.mensagemInfo("A nova aluna "
								+ this.ultimoUsuarioAlterado.getNome()
								+ " foi cadastrada.");
					else
						MensagemUtil.mensagemInfo("O novo aluno "
								+ this.ultimoUsuarioAlterado.getNome()
								+ " foi cadastrado.");
					this.edicaoDeUsuario = false;
				} else
					MensagemUtil.mensagemInfo("Dados de "
							+ this.ultimoUsuarioAlterado.getNome()
							+ " foram salvos.");
				this.usuarioSalvo = false;
			}
		}
	}

	public void mensagemProfessorSalvo() {
		if (this.ultimoUsuarioAlterado.getPerfil() != null){
			if (isUsuarioSalvo() && this.ultimoUsuarioAlterado.getPerfil().getId().equals(2)) {
				if (!isEdicaoDeUsuario()) {
					if (this.ultimoUsuarioAlterado.getSexo().equalsIgnoreCase("Feminino"))
						MensagemUtil.mensagemInfo("A nova professora "
								+ this.ultimoUsuarioAlterado.getNome()
								+ " foi cadastrada.");
					else
						MensagemUtil.mensagemInfo("O novo professor "
								+ this.ultimoUsuarioAlterado.getNome()
								+ " foi cadastrado.");
				} else
					MensagemUtil.mensagemInfo("Dados de "
							+ this.ultimoUsuarioAlterado.getNome()
							+ " foram salvos.");
				this.usuarioSalvo = false;
			}
		}
	}

	public void mensagemAdministradorSalvo() {
		if (this.ultimoUsuarioAlterado.getPerfil() != null){
			if (isUsuarioSalvo() && this.ultimoUsuarioAlterado.getPerfil().getId().equals(1)) {
				if (!isEdicaoDeUsuario()) {
					if (this.ultimoUsuarioAlterado.getSexo().equalsIgnoreCase("Feminino"))
						MensagemUtil.mensagemInfo("A nova administradora "
								+ this.ultimoUsuarioAlterado.getNome()
								+ " foi cadastrada.");
					else
						MensagemUtil.mensagemInfo("O novo administrador "
								+ this.ultimoUsuarioAlterado.getNome()
								+ " foi cadastrado.");
				} else
					MensagemUtil.mensagemInfo("Dados de "
							+ this.ultimoUsuarioAlterado.getNome()
							+ " foram salvos.");
				this.usuarioSalvo = false;
			}
		}
	}

	/* Dar um jeito de enxugar essas mensagens */

	/* DELETAR/EXCLUÍR USUÁRIOS */
	public void deletar(Usuario usuario) throws ServiceException {
		// Seta deletado no banco de dados
		usuario.setDeletado(true);
		usuarioService.salvar(usuario);

		// Remove da lista de usuário em que se encontrava
		this.usuarios.remove(usuario);

		this.ultimoUsuarioAlterado = usuario;

		if (this.ultimoUsuarioAlterado.getSexo().equalsIgnoreCase("Feminino"))
			MensagemUtil.mensagemInfo(this.ultimoUsuarioAlterado.getNome()
					+ " excluída.");
		else
			MensagemUtil.mensagemInfo(this.ultimoUsuarioAlterado.getNome()
					+ " excluído.");
	}

	public void excluir(Usuario usuario) {
		try {
			this.ultimoUsuarioAlterado = usuario;
			usuarios.remove(usuario);

			usuarioService.excluir(usuario);

			if (this.ultimoUsuarioAlterado.getSexo().equalsIgnoreCase(
					"Feminino"))
				MensagemUtil.mensagemInfo(this.ultimoUsuarioAlterado.getNome()
						+ " excluída definitivamente.");
			else
				MensagemUtil.mensagemInfo(this.ultimoUsuarioAlterado.getNome()
						+ " excluído definitivamente.");

		} catch (ServiceException e) {
			MensagemUtil.mensagemErro("Erro ao excluir. Tente novamente.");
		}
	}

	/* PAGINA DO USUÁRIO */
	public String verPaginaAluno(Usuario usuario) throws IOException {
		// Preparação do Aluno
		this.setUsuario(usuario);
		
		// Ordenando as avaliações do aluno por data
		if(this.usuario.getAvaliacoes().size() > 0){
			AvaliacaoComparator avaliacaoComparator = new AvaliacaoComparator();
			Collections.sort(this.usuario.getAvaliacoes(), avaliacaoComparator);
		}

		return "chama-aluno";
	}

	/* GERENCIAR USUÁRIOS */
	public String gerenciarAlunos() {
		// Prepara a lista de alunos
		this.usuarios = this.usuarioService.buscarAlunos();

		return "chama-gerenciar-alunos";
	}

	public String gerenciarProfessores() {
		// Prepara a lista de professores
		this.usuarios = this.usuarioService.buscarProfessores();

		return "chama-gerenciar-professores";
	}

	public String gerenciarAdministradores() {
		// Prepara a lista de administradores
		this.usuarios = this.usuarioService.buscarAdministradores();

		return "chama-gerenciar-administradores";
	}

	/* OUTRAS FUNÇÕES DE CONTROLE DE USUÁRIOS */
	public void setUsuariosPorPerfilDoUsuarioAtual() {
		this.usuarios = usuarioService.buscarUsuariosPorIdDoPerfil(this.usuario
				.getPerfil().getId());
	}

	public List<Usuario> buscaNomesDeUsuarios(String letras) {
		List<Usuario> usuarios = this.usuarioService.buscarTodos();
		List<Usuario> usuariosEncontrados = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			if (usuario.toString().contains(letras)) {
				usuariosEncontrados.add(usuario);
			}
		}
		return usuariosEncontrados;
	}

	/*-----AVALIACAO-----*/

	/* NOVAS AVALIAÇÕES */
	public String novaAvaliacao(String destino) {
		// Prepara a nova avaliação
		this.avaliacao = new Avaliacao();
		this.edicaoDeAvaliacao = false;
		this.avaliacaoTemAluno = false;
		this.avaliacao.getAluno().setId(-1);
		this.avaliacao.getAluno().setDataDeNascimento(new Date());

		// Prepara o destino pós salvar
		this.destino = destino;

		return "nova-avaliacao";
	}

	public String novaAvaliacao(Usuario usuario, String destino) {
		// Prepara a nova avaliação
		this.setUsuario(usuario);
		this.avaliacao = new Avaliacao(this.usuario);
		this.avaliacaoTemAluno = true;
		this.edicaoDeAvaliacao = false;

		// Prepara o destino pós salvar
		this.destino = destino;

		return "nova-avaliacao";
	}
	
	public String novaAvaliacaoParaUsuario(String destino) {
		// Prepara a nova avaliação
		this.avaliacao = new Avaliacao(this.usuario);
		this.avaliacaoTemAluno = true;
		this.edicaoDeAvaliacao = false;

		// Prepara o destino pós salvar
		this.destino = destino;

		return "nova-avaliacao";
	}

	/* SALVAR AVALIAÇÕES */
	public String salvarAvaliacao() {
		if (this.avaliacaoTemAluno) {
			try {
				avaliacaoService.salvar(this.avaliacao);
				this.avaliacaoSalva = true;

				MensagemUtil.mensagemInfo("Salvo com sucesso.");

				if (!this.edicaoDeAvaliacao) {
					//this.usuario.getAvaliacoes().add(avaliacao);//assim estava vindo com a data errada e dando pau se comparassemos logo em seguida
					this.usuario.setAvaliacoes(avaliacaoService.buscarAvaliacaoPeloAluno(this.usuario));
				}

				this.ultimaAvaliacaoAlterada = this.avaliacao;

				System.out.println(avaliacao);
				
				if(this.destino.equals("chama-comparar-avaliacao")){
					this.usuario.setAvaliacoes(avaliacaoService.buscarAvaliacaoPeloAluno(this.usuario));
					return prepararComparacao();
				}
				
				return this.destino;
				
			} catch (ServiceException serviceException) {
				MensagemUtil.mensagemErro(serviceException.getMessage());
				return "";
			}

		} else {
			MensagemUtil.mensagemErro("Escolha um aluno antes de salvar");
			return "";
		}
	}

	public void mensagemAvaliacaoSalva() {
		if (this.isAvaliacaoSalva()) {
			if (!isEdicaoDeAvaliacao())
				MensagemUtil.mensagemInfo("A nova avaliação de "
						+ this.ultimaAvaliacaoAlterada.getAluno().getNome()
						+ " foi salva.");
			else {
				SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");

				MensagemUtil.mensagemInfo("Os dados da avaliação de  "
						+ this.ultimaAvaliacaoAlterada.getAluno().getNome()
						+ " realizada no dia "
						+ data.format(this.ultimaAvaliacaoAlterada.getData())
						+ " foram salvos.");
				this.edicaoDeAvaliacao = false;
			}
			
			this.avaliacaoSalva = false;
		}
	}

	/* EDITAR AVALIAÇÕES */
	public String editar(Avaliacao avaliacao, String destino) {
		this.edicaoDeAvaliacao = true;
		this.avaliacaoTemAluno = true;
		this.avaliacao = avaliacao;

		this.destino = destino;

		return "chama-edicao-avaliacao";
	}

	/* DELETAR/EXCLUÍR AVALIAÇÕES */
	public void deletar(Avaliacao avaliacao) throws ServiceException {
		SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");

		avaliacao.setDeletado(true);
		this.usuario.getAvaliacoes().remove(avaliacao);
		this.avaliacoesParaComparar.remove(avaliacao);
		this.avaliacaoService.salvar(avaliacao);
		MensagemUtil.mensagemInfo("Avaliação de "
				+ avaliacao.getAluno().getNome() + " realizada no dia "
				+ data.format(avaliacao.getData()) + " foi excluída.");
	}

	public void excluir(Avaliacao avaliacao) {
		try {
			SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");

			avaliacaoService.excluir(avaliacao);

			this.usuario.getAvaliacoes().remove(avaliacao);
			this.avaliacoesParaComparar.remove(avaliacao);
			MensagemUtil.mensagemInfo("Avaliação de "
					+ avaliacao.getAluno().getNome() + " realizada no dia "
					+ data.format(avaliacao.getData())
					+ " foi excluída definitivamente.");
		} catch (ServiceException e) {
			MensagemUtil.mensagemErro("Erro ao excluir. Tente novamente.");
		}
	}

	/* OUTRAS FUNÇÕES DE CONTROLE DE AVALIAÇÕES */
	public Integer numeroDeAvaliacoes() {
		return this.usuario.getAvaliacoes().size();
	}

	public String prepararComparacao(){
		
		if(this.usuario.getId() != null){
			if(this.usuario.getAvaliacoes().size() > 0)	{
				
				this.avaliacoesParaComparar = this.usuario.getAvaliacoes();

				this.graficos.createGraficos(this.avaliacoesParaComparar, this.usuario
						.getAvaliacoes().get(this.usuario.getAvaliacoes().size() - 1));
				
				return "chama-comparar-avaliacao";
			}
			if(this.usuario.getSexo().equalsIgnoreCase("Feminino"))
				MensagemUtil.mensagemInfo("A " + this.usuario.getNome() + " ainda não possui avaliações.");
			else
				MensagemUtil.mensagemInfo("O " + this.usuario.getNome() + " ainda não possui avaliações.");
		}
		
		return null;
				
	}

	public void comparar() {
		// System.out.println(avaliacoesParaComparar.toString()+ "tamanho: "
		// +avaliacoesParaComparar.size());

		if (this.avaliacoesParaComparar.size() > 1) {
			AvaliacaoComparator avaliacaoComparator = new AvaliacaoComparator();
			Collections.sort(this.avaliacoesParaComparar, avaliacaoComparator);
			
			this.graficos.createGraficos(this.avaliacoesParaComparar,
					this.usuario.getAvaliacoes().get(this.usuario.getAvaliacoes().size() - 1));
		} else if (this.avaliacoesParaComparar.size() == 0) {
			MensagemUtil
					.mensagemInfo("Escolha ao menos uma avaliação para visualizar os dados.");
		} else {
			this.graficos.createGraficos(this.avaliacoesParaComparar,
					this.usuario.getAvaliacoes().get(this.usuario.getAvaliacoes().size() - 1));
		}
	}

	public void setAlunoDaAvaliacao(SelectEvent event) {

		this.setUsuario((Usuario) event.getObject());
		this.avaliacaoTemAluno = true;
		this.setIdadeDaAvaliacao();
		// this.usuario.setAvaliacoes(avaliacaoService
		// .buscarAvaliacaoPeloAluno(this.usuario));
	}

	public void setIdadeDaAvaliacao() {
		if (this.avaliacaoTemAluno) {
			Date data = new Date();

			if (data.equals(this.avaliacao.getData()))
				this.avaliacao.setIdade(this.avaliacao.getAluno());
			else
				this.avaliacao.setIdade(this.avaliacao.getAluno(),
						this.avaliacao.getData());
		}
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void setIcq() {
		String icq = String.format("%.2f", this.avaliacao.getPerimetria()
				.setIcq());
		if (this.avaliacao.getPerimetria().getCintura() > 0.0
				&& this.avaliacao.getPerimetria().getQuadril() > 0.0) {
			addMessage("Ídice de Cintura e Quadril calculado: " + icq + ".");
		}
	}

	public void setImc() {
		String imc = String.format("%.2f", this.avaliacao
				.getComposicaoCorporal().setImc());
		if (this.avaliacao.getComposicaoCorporal().getImc() > 0.0
				&& this.avaliacao.getComposicaoCorporal().getImc() > 0.0) {
			addMessage("Índice de massa corporal calculado: " + imc + ".");
		}
	}

	public void calcularMassaGordaEMagra() {
		//Ao atribuir os valores ele já calculou na própria classe
		String gorda = String.format("%.2f", this.avaliacao
				.getComposicaoCorporal().calculaMassaGorda());
		String magra = String.format("%.2f", this.avaliacao
				.getComposicaoCorporal().calculaMassaMagra());

		addMessage("Massa magra: " + magra + "kg, e massa gorda: " + gorda
				+ "kg, calculadas.");
	}

	public void calcularMassaMuscular() {
		addMessage("Massa muscular calculada: "
				+ String.format("%.2f", this.avaliacao.getComposicaoCorporal()
						.calculaMassaMuscular()) + ".");
	}

	public void calcularPercentualDeGordura(ActionEvent actionEvent)
			throws ServiceException {
		try {
			this.avaliacao.getComposicaoCorporal().setPercentualDeGordura(
					this.avaliacao.getComposicaoCorporal()
							.calcularPercentualDeGordura());

			String gordura = String.format("%.2f", this.avaliacao
					.getComposicaoCorporal().getPercentualDeGordura());

			calcularMassaGordaEMagra();

			addMessage("Percentual de gordura calculado com Pollock 7 Dobras: "
					+ gordura + "%.");
		} catch (ServiceException e) {
			addMessage(e.getMessage());
		}
	}
	/* Ver se consigo alterar esse addMessage igual ao dos outros */
	
/*-----PERFIL-----*/

	
	/* OUTRAS FUNÇÕES DE CONTROLE DE PERFIS */
	public String getPerfilNoPlural() {
		if (this.usuario.getPerfil().getId().equals(3))
			return "Alunos";
		else if (this.usuario.getPerfil().getId().equals(1))
			return "Administradores";
		return "Professores";
	}
	
	/*OUTRAS FUNÇÕES*/
	public boolean algoSalvo(){
		if (this.usuarioSalvo || this.avaliacaoSalva)
			return true;
		return false;
	}
	
	public void mensagens(){
		this.mensagemAlunoSalvo();
		this.mensagemProfessorSalvo();
		this.mensagemAdministradorSalvo();
		this.mensagemAvaliacaoSalva();
	}
	
	public String busca(SelectEvent evento){
		
		this.usuarios = usuarioService.buscarPeloLoginOuNome((String)evento.getObject());
		
		return "resultado-busca";
	}
	
	
	/* Getters and Setters */
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		this.usuario.setAvaliacoes(avaliacaoService
				.buscarAvaliacaoPeloAluno(this.usuario));
	}

	public Usuario getBusca() {
		return busca;
	}

	public void setBusca(Usuario busca) {
		this.busca = busca;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Usuario getUltimoUsuarioAlterado() {
		return ultimoUsuarioAlterado;
	}

	public void setUltimoUsuarioAlterado(Usuario ultimoUsuarioAlterado) {
		this.ultimoUsuarioAlterado = ultimoUsuarioAlterado;
	}

	public Avaliacao getUltimaAvaliacaoAlterada() {
		return ultimaAvaliacaoAlterada;
	}

	public void setUltimaAvaliacaoAlterada(Avaliacao ultimaAvaliacaoAlterada) {
		this.ultimaAvaliacaoAlterada = ultimaAvaliacaoAlterada;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public List<Avaliacao> getAvaliacoesParaComparar() {
		return avaliacoesParaComparar;
	}

	public void setAvaliacoesParaComparar(List<Avaliacao> avaliacoesParaComparar) {
		this.avaliacoesParaComparar = avaliacoesParaComparar;
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

	public void setUsuarioSalvo(boolean usuarioSalvo) {
		this.usuarioSalvo = usuarioSalvo;
	}

	public boolean isAvaliacaoSalva() {
		return avaliacaoSalva;
	}

	public void setAvaliacaoSalva(boolean avaliacaoSalva) {
		this.avaliacaoSalva = avaliacaoSalva;
	}

	public boolean isEdicaoDeUsuario() {
		return edicaoDeUsuario;
	}

	public void setEdicaoDeUsuario(boolean edicaoDeUsuario) {
		this.edicaoDeUsuario = edicaoDeUsuario;
	}

	public boolean isEdicaoDeAvaliacao() {
		return edicaoDeAvaliacao;
	}

	public void setEdicaoDeAvaliacao(boolean edicaoDeAvaliacao) {
		this.edicaoDeAvaliacao = edicaoDeAvaliacao;
	}

	public boolean isAvaliacaoTemAluno() {
		return avaliacaoTemAluno;
	}

	public void setAvaliacaoTemAluno(boolean avaliacaoTemAluno) {
		this.avaliacaoTemAluno = avaliacaoTemAluno;
	}
	
	public Graficos getGraficos() {
		return graficos;
	}
	
	public void setGraficos(Graficos graficos) {
		this.graficos = graficos;
	}


}
