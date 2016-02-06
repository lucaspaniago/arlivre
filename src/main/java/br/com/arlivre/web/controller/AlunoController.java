package br.com.arlivre.web.controller;
//package br.com.avaliacao.web.controller;
//
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//import br.com.avaliacao.model.entidade.Avaliacao;
//import br.com.avaliacao.model.entidade.Usuario;
//import br.com.avaliacao.model.service.AvaliacaoService;
//
//@Controller
////@ManagedBean
//public class AlunoController {
//
//	// Objeto será vinculado com a tela
//	private Usuario aluno;
//
//	// Ponto de injeção de dependência
////	@Autowired
////	private UsuarioService usuarioService;
//	@Autowired
//	private AvaliacaoService avaliacaoService;
//
//	// Lista vinculada à tela
//	private List<Avaliacao> avaliacoes;
//
////	private boolean edicao;
//
//	public AlunoController() {
//		aluno = new Usuario();
//	}
//
//	@PostConstruct
//	public void init() {
////		edicao = false;
//		avaliacoes = avaliacaoService.buscarAvaliacaoPeloAluno(aluno);
//	}
//
//	// Método será chamado por um botão
////	public void salvar() {
////		try {
////			//System.out.println("ENTREI AQUI E SALVEI");
////			// Chamando o salvar do service
////			usuarioService.salvar(aluno);
////
////			MensagemUtil.mensagemInfo("Salvo com sucesso.");
////
////			if (!edicao) {
////				avaliacoes.add(aluno);
////			}
////
////			aluno = new Usuario();
////			this.edicao = false;
////
////			// Dados impressos pelo toString
////			System.out.println(aluno);
////		} catch (ServiceException serviceException) {
////			MensagemUtil.mensagemErro(serviceException.getMessage());
////		}
////
////	}
//
////	public void prepararInclusao() {
////		this.edicao = false;
////		aluno = new Usuario();
////	}
//
////	public void prepararEdicao(SelectEvent evento) {//(Usuario usuario)
////		this.edicao = true;
////		this.aluno = (Usuario) evento.getObject();//usuario;
////	}
//	
////	public void editar (Usuario usuario) {
////		this.edicao = true;
////		this.aluno = usuario;
////	}
//	
////	public void excluir(Usuario usuario) {
////		try {
////			usuarioService.excluir(usuario);
////
////			MensagemUtil.mensagemInfo("Usuário excluído.");
////			avaliacoes.remove(usuario);
////		} catch (ServiceException e) {
////			MensagemUtil.mensagemErro("Erro ao excluir. Tente novamente.");
////		}
////	}
//	
//	public Usuario getAluno() {
//		return aluno;
//	}
//	
//	public void setAluno(Usuario aluno) {
//		this.aluno = aluno;
//	}
//	
//	public List<Avaliacao> getAvaliacoes() {
//		return avaliacoes;
//	}
//	
//	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
//		this.avaliacoes = avaliacoes;
//	}
//	
////	public boolean isEdicao() {
////		return edicao;
////	}
//
////	public List<Usuario> nomes(String letras) {
//////		List<String> palavras = Arrays.asList("Fabio", "Fabio Oliverira",
//////				"Debora", "Lucas", "Marcelo");
////		List<Usuario> usuariosEncontrados = new ArrayList<>();
////		for (Usuario usuario : avaliacoes) {
////			if (usuario.toString().contains(letras)) {
////				usuariosEncontrados.add(usuario);
////			}
////		}
////		return usuariosEncontrados;
////	}
//
//}
