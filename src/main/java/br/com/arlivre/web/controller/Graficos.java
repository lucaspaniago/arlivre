package br.com.arlivre.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.arlivre.model.entidade.Avaliacao;
import br.com.arlivre.web.graficos.GraficoComposicaoAtual;
import br.com.arlivre.web.graficos.GraficoHistoricoComposicaoCorporal;
import br.com.arlivre.web.graficos.GraficoHistoricoFC;
import br.com.arlivre.web.graficos.GraficoHistoricoPressaoArterial;
import br.com.arlivre.web.graficos.GraficoPerimetria;

public class Graficos {

	private GraficoHistoricoComposicaoCorporal historicoComposicaoCorporal;
	private GraficoComposicaoAtual composicaoCorporalAtual;
	private GraficoComposicaoAtual massaMagraAtual;
	private GraficoHistoricoPressaoArterial historicoPressaoArterial;
	private GraficoHistoricoFC historicoFC;
	private GraficoPerimetria historicoPescoco;
	private GraficoPerimetria historicoCinturaEscapular;
	private GraficoPerimetria historicoToraxica;
	private GraficoPerimetria historicoCintura;
	private GraficoPerimetria historicoAbdomen;
	private GraficoPerimetria historicoQuadril;
	private GraficoPerimetria historicoCoxa;
	private GraficoPerimetria historicoPanturrilha;
	private GraficoPerimetria historicoBraco;
	private GraficoPerimetria historicoAntebraco;

	public Graficos() {

	}

	public void createGraficos(List<Avaliacao> avaliacoes, Avaliacao maisRecente) {

		Integer size = avaliacoes.size();
		Avaliacao primeira = avaliacoes.get(0);
		Avaliacao ultima = avaliacoes.get(size - 1);
		long dif;
		SimpleDateFormat sdfComposicao = new SimpleDateFormat("dd/MM/yyyy");

		this.historicoComposicaoCorporal = new GraficoHistoricoComposicaoCorporal(
				"Hist�rico da Composi��o Corporal", "Peso", "Massa Magra",
				"Massa Gorda", "Massa Muscular", size);
		this.composicaoCorporalAtual = new GraficoComposicaoAtual(
				sdfComposicao.format(maisRecente.getData()), maisRecente
						.getComposicaoCorporal().getMassaMagra(), maisRecente
						.getComposicaoCorporal().getMassaGorda(),
				"Massa Magra", "Massa Gorda", "Composi��o Corporal Atual",
				maisRecente.getComposicaoCorporal().getPeso());
		this.massaMagraAtual = new GraficoComposicaoAtual(
				sdfComposicao.format(maisRecente.getData()), maisRecente
						.getComposicaoCorporal().getPercentualMuscular(),
				(maisRecente.getComposicaoCorporal().getMassaMagra() - maisRecente
						.getComposicaoCorporal().getPercentualMuscular()),
				"Musculo", "Ossos e demais �rg�os.",
				"Composi��o Atual da Massa Magra", maisRecente
						.getComposicaoCorporal().getMassaMagra());
		this.historicoPressaoArterial = new GraficoHistoricoPressaoArterial(
				"Hist�rico da Press�o Arterial", "Press�o Arterial Sist�lica",
				"Press�o Arterial Diast�lica", size);
		this.historicoFC = new GraficoHistoricoFC(
				"Hist�rico da Frenqu�ncia Card�aca", "Frenqu�ncia Card�aca", size);
		this.historicoPescoco = new GraficoPerimetria("Pesco�o");
		this.historicoCinturaEscapular = new GraficoPerimetria(
				"Cintura Escapular");
		this.historicoToraxica = new GraficoPerimetria("T�rax");
		this.historicoCintura = new GraficoPerimetria("Cintura");
		this.historicoAbdomen = new GraficoPerimetria("Abd�men");
		this.historicoQuadril = new GraficoPerimetria("Quadril");
		this.historicoCoxa = new GraficoPerimetria("Coxa Esquerda",
				"Coxa Direita", "Coxa");
		this.historicoPanturrilha = new GraficoPerimetria(
				"Panturrilha Esquerda", "Panturrilha Direita", "Panturrilha");
		this.historicoBraco = new GraficoPerimetria("Bra�o Esquerdo",
				"Bra�o Direito", "Bra�o");
		this.historicoAntebraco = new GraficoPerimetria("Antebra�o Esquerdo",
				"Antebra�o Direito", "Antebra�o");

//		String dataFormatada = null;
//		SimpleDateFormat sdf = new SimpleDateFormat("d/M/yy");
//		Date data = new Date();

		for (Avaliacao a : avaliacoes) {
			
			Date data = a.getData();
//			data = a.getData();
//			dataFormatada = sdf.format(a.getData()); // a.getData().toString();

			this.historicoComposicaoCorporal.setDados(data, a
					.getComposicaoCorporal().getPeso(), a
					.getComposicaoCorporal().getMassaMagra(), a
					.getComposicaoCorporal().getMassaGorda(), a
					.getComposicaoCorporal().getMassaMuscular());
			this.historicoPressaoArterial.setDados(data,
					a.getPasRepouso(), a.getPadRepouso());
			this.historicoFC.setDados(data, a.getFcRepouso());
			this.historicoPescoco.setDados(data, a.getPerimetria()
					.getPescoco());
			this.historicoCinturaEscapular.setDados(data, a
					.getPerimetria().getCinturaEscapular());
			this.historicoToraxica.setDados(data, a.getPerimetria()
					.getToraxica());
			this.historicoAbdomen.setDados(data, a.getPerimetria()
					.getAbdomen());
			this.historicoCintura.setDados(data, a.getPerimetria()
					.getCintura());
			this.historicoQuadril.setDados(data, a.getPerimetria()
					.getQuadril());
			this.historicoCoxa.setDados(data, a.getPerimetria()
					.getCoxaEsquerda(), a.getPerimetria().getCoxaDireita());
			this.historicoPanturrilha.setDados(data, a.getPerimetria()
					.getPanturrilhaEsquerda(), a.getPerimetria()
					.getPanturrilhaDireita());
			this.historicoBraco.setDados(data, a.getPerimetria()
					.getBracoEsquerdo(), a.getPerimetria().getBracoDireito());
			this.historicoAntebraco.setDados(data, a.getPerimetria()
					.getAntebracoEsquerdo(), a.getPerimetria()
					.getAntebracoDireito());
		}

		dif = (avaliacoes.get(avaliacoes.size() - 1).getData().getTime() - avaliacoes
				.get(0).getData().getTime());
		dif = dif / 10;

		this.historicoComposicaoCorporal.addSeries(size, dif,
				primeira.getData(), ultima.getData());
		this.historicoPressaoArterial.addSeries(size, dif, primeira.getData(),
				ultima.getData());
		this.historicoFC.addSeries(size, dif, primeira.getData(),
				ultima.getData());
		this.historicoPescoco.addSerie(size);
		this.historicoCinturaEscapular.addSerie(size);
		this.historicoToraxica.addSerie(size);
		this.historicoAbdomen.addSerie(size);
		this.historicoCintura.addSerie(size);
		this.historicoQuadril.addSerie(size);
		this.historicoCoxa.addSeries(size);
		this.historicoPanturrilha.addSeries(size);
		this.historicoBraco.addSeries(size);
		this.historicoAntebraco.addSeries(size);

	}

	public GraficoHistoricoComposicaoCorporal getHistoricoComposicaoCorporal() {
		return historicoComposicaoCorporal;
	}

	public GraficoComposicaoAtual getComposicaoCorporalAtual() {
		return composicaoCorporalAtual;
	}

	public GraficoPerimetria getHistoricoPescoco() {
		return historicoPescoco;
	}

	public GraficoPerimetria getHistoricoCinturaEscapular() {
		return historicoCinturaEscapular;
	}

	public GraficoPerimetria getHistoricoToraxica() {
		return historicoToraxica;
	}

	public GraficoPerimetria getHistoricoCintura() {
		return historicoCintura;
	}

	public GraficoPerimetria getHistoricoAbdomen() {
		return historicoAbdomen;
	}

	public GraficoPerimetria getHistoricoQuadril() {
		return historicoQuadril;
	}

	public GraficoPerimetria getHistoricoCoxa() {
		return historicoCoxa;
	}

	public GraficoPerimetria getHistoricoPanturrilha() {
		return this.historicoPanturrilha;
	}

	public GraficoPerimetria getHistoricoBraco() {
		return historicoBraco;
	}

	public GraficoPerimetria getHistoricoAntebraco() {
		return historicoAntebraco;
	}

	public GraficoComposicaoAtual getMassaMagraAtual() {
		return massaMagraAtual;
	}

	public GraficoHistoricoPressaoArterial getHistoricoPressaoArterial() {
		return historicoPressaoArterial;
	}

	public GraficoHistoricoFC getHistoricoFC() {
		return historicoFC;
	}

}