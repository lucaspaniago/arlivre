package br.com.arlivre.web.controller;

import java.io.IOException;
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
import br.com.arlivre.model.service.ServiceException;

@Controller
// @ManagedBean
public class AvaliacaoController {

	// Objeto será vinculado com a tela
	private Avaliacao avaliacao;
	
	private Avaliacao ultimaAvaliacao;

	private Usuario avaliado;

	private Graficos graficos;

	// Ponto de injeção de dependência
	@Autowired
	private AvaliacaoService avaliacaoService;

	// Lista vinculada à tela
	private List<Avaliacao> avaliacoes;

	private List<Avaliacao> avaliacoesParaComparar;
	
	private String destino;
	private boolean avaliacaoSalva;
	private boolean avaliacaoNova;
	

	private boolean edicao;
	private boolean temAluno;

	public AvaliacaoController() {
		this.avaliacao = new Avaliacao();

		this.avaliacao.getAluno().setId(-1);
		this.avaliacao.getAluno().setDataDeNascimento(new Date());
		this.graficos = new Graficos();
	}

	// REVER ESSE MÉTODO POIS SOBRECARREGARÁ O SISTEMA (TALVEZ CARREGAR AS
	// AVALIAÇÕES DE UM ALUNO SERIA IDEAL)
	@PostConstruct
	public void init() {
		edicao = false;
		//this.avaliacoes = avaliacaoService.buscarTodas();
		this.avaliacoes = new ArrayList<Avaliacao>();
		this.avaliacoesParaComparar = new ArrayList<Avaliacao>();
		// if (!avaliacao.getUsuario().getId().equals(null)){
		// avaliacao.setIdade(avaliacao.getUsuario());
		// }
	}

	public String nova() {
		
		this.avaliacao = new Avaliacao();
		this.temAluno = false;
		this.avaliacao.getAluno().setId(-1);
		this.avaliacao.getAluno().setDataDeNascimento(new Date());
		//this.destino = "chama-aluno";

		return "nova-avaliacao";
	}

	public String salvar() {
		if (this.temAluno) {
			try {
				avaliacaoService.salvar(this.avaliacao);

				MensagemUtil.mensagemInfo("Salvo com sucesso.");

				if (!edicao) {
					avaliacoes.add(avaliacao);
					this.avaliacaoNova = true;
				}
				else
					this.avaliacaoNova = false;
				
//				if (!this.destino.equals("chama-aluno")){
//					this.ultimaAvaliacao = this.avaliacao;
//					this.avaliacao = new Avaliacao();
//				}

				// avaliacao = new Avaliacao();
				//this.edicao = false;
				this.avaliacaoSalva = true;

				// Dados impressos pelo toString
				//System.out.println(avaliacao);

				return this.destino;
			} catch (ServiceException serviceException) {
				MensagemUtil.mensagemErro(serviceException.getMessage());
				return "";
			}

		} else {
			// System.out.println("entrei aqui porque é igual a -1");
			MensagemUtil.mensagemErro("Escolha um aluno antes de salvar");
			return "";
		}
	}
	
	public void mensagemAvaliacaoSalva(){
		if (isAvaliacaoSalva()){
			if (isAvaliacaoNova() && !isEdicao())
				MensagemUtil.mensagemInfo("A nova avaliação de " + this.avaliado.getNome() + " foi salva.");
			else{
				MensagemUtil.mensagemInfo("Os dados da avaliação de  " + this.avaliado.getNome() + " foram salvos.");
				this.edicao = false;
			}
		}
		this.avaliacaoSalva = false;
	}

	public String prepararInclusao(Usuario aluno, String destino) throws IOException {
		this.avaliado = aluno;
		this.avaliado.setAvaliacoes(this.avaliacaoService.buscarAvaliacaoPeloAluno(this.avaliado));
		this.edicao = false;
		this.avaliacao = new Avaliacao(this.avaliado);
//		this.avaliacaoNova = true;
		this.temAluno = true;
		this.avaliacoes = this.avaliado.getAvaliacoes();
		
		this.destino = destino; 
		System.out.println("destinp: " + destino);
//		if (this.avaliado.getPerfil().getId().equals(3))
//			this.destino = "chama-aluno"; 
//		else if (this.avaliado.getPerfil().getId().equals(2))
//			this.destino = "chama-aluno"; 
//		else if (this.avaliado.getPerfil().getId().equals(1))
//			this.destino = "chama-aluno";
		
		return "chama-edicao-avaliacao";
		
	}

	public void prepararInclusao() {
		this.edicao = false;
		avaliacao = new Avaliacao();
		// avaliacao.setPerimetria(new Perimetria());
		// avaliacao.setComposicaoCorporal(new ComposicaoCorporal());
		// aluno = new Usuario();
		this.temAluno = false;
		this.avaliacao.getAluno().setId(-1);
		this.avaliacao.getAluno().setDataDeNascimento(new Date());
	}

	public void prepararEdicao(SelectEvent evento) {
		this.edicao = true;
		this.temAluno = true;
		this.avaliacao = (Avaliacao) evento.getObject();
		this.avaliado = this.avaliacao.getAluno();
		this.avaliacoes = this.avaliado.getAvaliacoes();
		
		if (this.avaliado.getPerfil().getId().equals(3))
			this.destino = "chama-aluno"; 
		else if (this.avaliado.getPerfil().getId().equals(2))
			this.destino = "chama-aluno"; 
		else if (this.avaliado.getPerfil().getId().equals(1))
			this.destino = "chama-aluno";
	}

	public void prepararComparacao(Usuario aluno) throws IOException {

		this.avaliado = aluno;

		this.avaliacoes = this.avaliado.getAvaliacoes();
		this.avaliacoesParaComparar = this.avaliacoes;

		this.graficos.createGraficos(this.avaliacoesParaComparar, this.avaliacoes.get(this.avaliacoes.size() - 1));
	}
	
	public void comparar (){
		//System.out.println(avaliacoesParaComparar.toString()+ "tamanho: " +avaliacoesParaComparar.size());
		
		if (this.avaliacoesParaComparar.size() > 1){
			AvaliacaoComparator avaliacaoComparator = new AvaliacaoComparator();
			Collections.sort(this.avaliacoesParaComparar, avaliacaoComparator);
			this.graficos.createGraficos(this.avaliacoesParaComparar, this.avaliacoes.get(this.avaliacoes.size() - 1));
		}else if(this.avaliacoesParaComparar.size() == 0){
			MensagemUtil.mensagemInfo("Escolha ao menos uma avaliação para visualizar os dados.");
		}
		else{
			this.graficos.createGraficos(this.avaliacoesParaComparar, this.avaliacoes.get(this.avaliacoes.size() - 1));
		}
	}

	public String editar(Avaliacao avaliacao, String destino) {
		this.edicao = true;
//		this.avaliacaoNova = false;
		this.temAluno = true;
		this.avaliacao = avaliacao;
		this.avaliado = avaliacao.getAluno();
		
		this.destino = destino;
//		if (this.avaliado.getPerfil().getId().equals(3))
//			this.destino = destino; 
//		else if (this.avaliado.getPerfil().getId().equals(2))
//			this.destino = "chama-aluno"; 
//		else if (this.avaliado.getPerfil().getId().equals(1))
//			this.destino = "chama-aluno";
		
		return "chama-edicao-avaliacao";
	}
	
	public void deletar(Avaliacao avaliacao) throws ServiceException {
		avaliacao.setDeletado(true);
		this.avaliacoes.remove(avaliacao);
		this.avaliacoesParaComparar.remove(avaliacao);
		this.avaliacaoService.salvar(avaliacao);
		MensagemUtil.mensagemInfo("Avaliação excluída.");
	}
	
	public void excluir(Avaliacao avaliacao) {
		try {
			avaliacaoService.excluir(avaliacao);

			this.avaliacoes.remove(avaliacao);
			this.avaliacoesParaComparar.remove(avaliacao);
			MensagemUtil.mensagemInfo("Avaliação excluída definitivamente.");
		} catch (ServiceException e) {
			MensagemUtil.mensagemErro("Erro ao excluir. Tente novamente.");
		}
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	// Retorna o aluno da this.avaliação
	public Usuario getAluno() {
		return this.avaliacao.getAluno();
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public boolean isEdicao() {
		return edicao;
	}

	public boolean isTemAluno() {
		return temAluno;
	}

	public void setIdade() {
		if (temAluno) {
			Date data = new Date();

			if (data.equals(this.avaliacao.getData()))
				this.avaliacao.setIdade(this.avaliacao.getAluno());
			else
				this.avaliacao.setIdade(this.avaliacao.getAluno(),
						this.avaliacao.getData());
		}
	}

	public void setAlunoDaAvaliacao(SelectEvent event) {

		this.avaliacao.setAluno((Usuario) event.getObject());
		this.avaliado = this.avaliacao.getAluno();
		this.temAluno = true;
		this.setIdade();
		this.avaliacoes = avaliacaoService.buscarAvaliacaoPeloAluno(this.avaliado);
		
//		if (id.equals(3))
//		this.destino = "chama-gerenciar-alunos"; 
//	else if (id.equals(3))
//		this.destino = "chama-gerenciar-professores"; 
//	else if (id.equals(3))
//		this.destino = "chama-gerenciar-administradores"; 
		//this.destino = "";
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
			this.avaliacao.getComposicaoCorporal().setPercentualDeGordura(this.avaliacao.getComposicaoCorporal().calcularPercentualDeGordura());
			
			String gordura = String.format("%.2f", this.avaliacao.getComposicaoCorporal().getPercentualDeGordura());

			calcularMassaGordaEMagra();

			addMessage("Percentual de gordura calculado com Pollock 7 Dobras: "
					+ gordura + "%.");
		} catch (ServiceException e) {
			addMessage(e.getMessage());
		}
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public List<Avaliacao> getAvaliacoesParaComparar() {
		return avaliacoesParaComparar;
	}

	public void setAvaliacoesParaComparar(List<Avaliacao> avaliacoesParaComparar) {
		this.avaliacoesParaComparar = avaliacoesParaComparar;
	}

	public Usuario getAvaliado() {
		return avaliado;
	}

	public void setAvaliado(Usuario avaliado) {
		this.avaliado = avaliado;
		this.temAluno = true;
	}

	public Integer numeroDeAvaliacoes() {
		return this.avaliacoes.size();
	}

	public Graficos getGraficos() {
		return graficos;
	}

	public void setGraficos(Graficos graficos) {
		this.graficos = graficos;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public boolean isAvaliacaoSalva() {
		return avaliacaoSalva;
	}

	public void setAvaliacaoSalva(boolean avaliacaoSalva) {
		this.avaliacaoSalva = avaliacaoSalva;
	}

	public Avaliacao getUltimaAvaliacao() {
		return ultimaAvaliacao;
	}

	public void setUltimaAvaliacao(Avaliacao ultimaAvaliacao) {
		this.ultimaAvaliacao = ultimaAvaliacao;
	}

	public boolean isAvaliacaoNova() {
		return avaliacaoNova;
	}

	public void setAvaliacaoNova(boolean avaliacaoNova) {
		this.avaliacaoNova = avaliacaoNova;
	}
}
