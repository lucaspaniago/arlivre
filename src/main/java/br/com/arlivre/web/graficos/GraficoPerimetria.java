package br.com.arlivre.web.graficos;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.arlivre.web.controller.MensagemUtil;

public class GraficoPerimetria {
	private BarChartModel grafico;
	private ChartSeries esquerda;
	private ChartSeries direita;
	private ChartSeries medida;
	private Double maxValue;
	private boolean mostraGrafico;
	private boolean mostraFiltro;

	public GraficoPerimetria(String esquerda, String direita, String perimetria) {

		this.grafico = new BarChartModel();
		this.esquerda = new ChartSeries();
		this.direita = new ChartSeries();
		this.maxValue = 0.0;
		this.mostraGrafico = true;
		this.mostraFiltro = true;

		this.esquerda.setLabel(esquerda);
		this.direita.setLabel(direita);

		grafico.setTitle(perimetria);
		grafico.setLegendPosition("ne");
		grafico.setShowPointLabels(true);
		grafico.setLegendCols(2);
	}

	public GraficoPerimetria(String perimetria) {

		this.grafico = new BarChartModel();
		this.medida = new ChartSeries();
		this.maxValue = 0.0;
		this.mostraGrafico = true;
		this.mostraFiltro = true;

		this.medida.setLabel(perimetria);

		this.grafico.setTitle(perimetria);
		this.grafico.setLegendPosition("ne");
		this.grafico.setShowPointLabels(true);
	}

	public void setDados(Date data, Double esquerda, Double direita) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("d/M/yy");
		String dataFormatada = sdf.format(data);
		
		if (esquerda > this.maxValue) {
			this.maxValue = esquerda;
		}
		this.esquerda.set(dataFormatada, esquerda);

		if (direita > this.maxValue) {
			this.maxValue = direita;
		}
		this.direita.set(dataFormatada, direita);
	}

	public void setDados(Date data, Double medida) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("d/M/yy");
		String dataFormatada = sdf.format(data);
		
		if (medida > this.maxValue) {
			this.maxValue = medida;
		}
		this.medida.set(dataFormatada, medida);
	}

	public void addSeries(Integer size) {

		grafico.addSeries(this.esquerda);
		grafico.addSeries(this.direita);
		
		Axis yAxis = grafico.getAxis(AxisType.Y);
		yAxis.setLabel("cm");
		yAxis.setTickFormat("%.1f");
		yAxis.setMin(0);
		yAxis.setMax(this.maxValue + 50);
		if (size > 5){
			grafico.getAxis(AxisType.X).setTickAngle(-50);
			grafico.setShowDatatip(true);
			grafico.setShowPointLabels(false);
		}
	}

	public void addSerie(Integer size) {

		grafico.addSeries(this.medida);
		
		if (size > 5){
			grafico.getAxis(AxisType.X).setTickAngle(-50);
			grafico.setShowDatatip(true);
			grafico.setShowPointLabels(false);
		}
		
		Axis yAxis = grafico.getAxis(AxisType.Y);
		yAxis.setLabel("cm");
		yAxis.setTickFormat("%.1f");
		yAxis.setMin(0);
		yAxis.setMax(this.maxValue + 50);
	}

	public BarChartModel getGrafico() {
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
