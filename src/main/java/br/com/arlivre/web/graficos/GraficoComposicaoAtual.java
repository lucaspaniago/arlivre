package br.com.arlivre.web.graficos;

import java.util.Locale;

import org.primefaces.model.chart.PieChartModel;

import br.com.arlivre.web.controller.MensagemUtil;

public class GraficoComposicaoAtual {

	private PieChartModel grafico;
	private boolean mostraGrafico;
	private boolean mostraFiltro;

	public GraficoComposicaoAtual(String data, Double valor1, Double valor2, String medida1, String medida2, String titulo, Double peso) {
		
		this.mostraGrafico = true;
		this.mostraFiltro = true;
//		String pesof = String.format(new Locale("en"),"%.2f", valor2);
//		String valor1f = String.format(new Locale("en"),"%.2f", valor2);
		String valor2f = String.format(new Locale("en"),"%.2f", valor2);
		
		this.grafico = new PieChartModel();
		
		this.grafico.set(medida1 + " = " + valor1 + "kg", valor1);
		this.grafico.set(medida2 + " = " + valor2f + "kg", valor2);
		
		this.grafico.setTitle(titulo + " (" + data + " - " + peso + "kg)");
		this.grafico.setShowDataLabels(true);
		this.grafico.setShadow(true);
		this.grafico.setLegendPosition("n");
		this.grafico.setDataLabelFormatString("%.2f%%");
	}

	public PieChartModel getGrafico() {
		return grafico;
	}
	
	public boolean isMostraGrafico() {
		return mostraGrafico;
	}
	
	public void setMostraGrafico(boolean mostraGrafico) {
		this.mostraGrafico = mostraGrafico;
	}
	
	
	public boolean isMostraFiltro() {
		return mostraFiltro;
	}
	
	public void filtraGrafico() {
			if(this.mostraGrafico)
				MensagemUtil.mensagemInfo(this.grafico.getTitle() + " visível.");
			else
				MensagemUtil.mensagemInfo(this.grafico.getTitle() + " escondido.");
	}
	
}
