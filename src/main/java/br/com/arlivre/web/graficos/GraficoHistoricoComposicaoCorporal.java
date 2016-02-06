package br.com.arlivre.web.graficos;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.arlivre.web.controller.MensagemUtil;

public class GraficoHistoricoComposicaoCorporal {
	
	private LineChartModel grafico;
	LineChartSeries peso;
	LineChartSeries massaMagra;
	LineChartSeries massaGorda;
	LineChartSeries massaMuscular;
	private BarChartModel grafico2;
	private ChartSeries peso2;
	private ChartSeries massaMagra2;
	private ChartSeries massaGorda2;
	private ChartSeries massaMuscular2;
	private Double maxValue;
	private Integer size;
	private boolean mostraGrafico;
	private boolean mostraGrafico2;
	private boolean mostraFiltro;
	private boolean mostraFiltro2;
	
	public GraficoHistoricoComposicaoCorporal(String titulo, String peso, String massaMagra, String massaGorda, String massaMuscular, Integer size) {
		
		this.maxValue = 0.0;
		this.size = size;
		if (this.size > 1){
			mostraGrafico = true;
			mostraFiltro = true;
			mostraGrafico2 = false;
			mostraFiltro2 = false;
			this.grafico = new LineChartModel();
			this.peso = new LineChartSeries();
			this.massaMagra = new LineChartSeries();
			this.massaGorda = new LineChartSeries();
			this.massaMuscular = new LineChartSeries();
			
			
			this.grafico.setTitle(titulo);
			this.peso.setLabel(peso);
			this.massaMagra.setLabel(massaMagra);
			this.massaGorda.setLabel(massaGorda);
			this.massaMuscular.setLabel(massaMuscular);
			
		}
		else{
			mostraGrafico = false;
			mostraFiltro = false;
			mostraGrafico2 = true;
			mostraFiltro2 = true;
			this.grafico2 = new BarChartModel();
			this.peso2 = new ChartSeries();
			this.massaMagra2 = new ChartSeries();
			this.massaGorda2 = new ChartSeries();
			this.massaMuscular2 = new ChartSeries();
			
			this.grafico2.setTitle(titulo);
			this.peso2.setLabel(peso);
			this.massaMagra2.setLabel(massaMagra);
			this.massaGorda2.setLabel(massaGorda);
			this.massaMuscular2.setLabel(massaMuscular);
		}
	}
	
	public void setDados(Date d, Double peso, Double massaMagra, Double massaGorda, Double massaMuscular) {
		
		String data = d.toString();
		if (peso > this.maxValue) {
			this.maxValue = peso;
		}
		
		if(this.size > 1){
			this.peso.set(data, peso);
			this.massaMagra.set(data, massaMagra);
			this.massaGorda.set(data, massaGorda);
			this.massaMuscular.set(data, massaMuscular);
		}else{
			this.peso2.set(data, peso);
			this.massaMagra2.set(data, massaMagra);
			this.massaGorda2.set(data, massaGorda);
			this.massaMuscular2.set(data, massaMuscular);
		}
	}

	public void addSeries(Integer size, long c, Date primeira, Date ultima) {
		
		if (this.size > 1){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dataAux = new Date();
			
			this.grafico.addSeries(this.peso);
			this.grafico.addSeries(this.massaMagra);
			this.grafico.addSeries(this.massaGorda);
			this.grafico.addSeries(this.massaMuscular);
			
			this.grafico.setLegendCols(2);
			this.grafico.setZoom(true);
			this.grafico.setLegendPosition("n");
			//this.grafico.setShowPointLabels(true);
			this.grafico.setSeriesColors("EAA228,4BB2C5,C5B47F,579575");
			// Peso = EAA228
			// Massa Magra = 4BB2C5
			// Massa Gorda = C5B47F
			// Massa Muscular = 579575
			
			Axis yAxis = this.grafico.getAxis(AxisType.Y);
			yAxis.setLabel("kg");
			yAxis.setTickFormat("%.2f");
			yAxis.setMin(maxValue-maxValue);
			yAxis.setMax(this.maxValue + 60);
			
			DateAxis xAxis = new DateAxis();
			
			dataAux.setTime(primeira.getTime() - c);
			while (sdf.format(dataAux).equals(sdf.format(primeira))){
				dataAux.setTime(dataAux.getTime() - c); 
			}
			xAxis.setMin(sdf.format(dataAux));
			
			dataAux.setTime(ultima.getTime() + c);
			while (sdf.format(dataAux).equals(sdf.format(ultima))){
				dataAux.setTime(dataAux.getTime() + c);
			}
			xAxis.setMax(sdf.format(dataAux));
			xAxis.setTickFormat("%#d/%#m/%y");
			
			this.grafico.getAxes().put(AxisType.X, xAxis);
		}else{
			this.grafico2.addSeries(this.peso2);
			this.grafico2.addSeries(this.massaMagra2);
			this.grafico2.addSeries(this.massaGorda2);
			this.grafico2.addSeries(this.massaMuscular2);
			
			this.grafico2.setLegendCols(2);
			this.grafico2.setZoom(true);
			this.grafico2.setLegendPosition("n");
			//this.grafico.setShowPointLabels(true);
			this.grafico2.setSeriesColors("EAA228,4BB2C5,C5B47F,579575");

			Axis yAxis = grafico2.getAxis(AxisType.Y);
			yAxis.setLabel("kg");
			yAxis.setTickFormat("%.2f");
			yAxis.setMin(0);
			yAxis.setMax(this.maxValue + 50);
//				grafico.setShowDatatip(true);
//				grafico.setShowPointLabels(false);
		}
	}

	public LineChartModel getGrafico() {
		return grafico;
	}
	
	public BarChartModel getGrafico2() {
		return grafico2;
	}
	
	public boolean isMostraGrafico() {
		return mostraGrafico;
	}
	
	public boolean isMostraGrafico2() {
		return mostraGrafico2;
	}
	
	public void setMostraGrafico(boolean mostraGrafico) {
		this.mostraGrafico = mostraGrafico;
	}
	
	public void setMostraGrafico2(boolean mostraGrafico2) {
		this.mostraGrafico2 = mostraGrafico2;
	}
	
	public boolean isMostraFiltro() {
		return mostraFiltro;
	}
	
	public boolean isMostraFiltro2() {
		return mostraFiltro2;
	}
	
	public void filtraGrafico() {
		if(this.size.equals(1)){
			if(this.mostraGrafico2)
				MensagemUtil.mensagemInfo("Histórico da Composição Corporal visível.");
			else
				MensagemUtil.mensagemInfo("Histórico da Composição Corporal escondido.");
		}
		else{
			if(this.mostraGrafico)
				MensagemUtil.mensagemInfo("Histórico da Composição Corporal visível.");
			else
				MensagemUtil.mensagemInfo("Histórico da Composição Corporal escondido.");
		}
	}
}
