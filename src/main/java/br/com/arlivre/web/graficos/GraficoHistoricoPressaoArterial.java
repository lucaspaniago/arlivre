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

public class GraficoHistoricoPressaoArterial {
	
	private LineChartModel grafico;
	private LineChartSeries PAS;
	private LineChartSeries PAD;
	private BarChartModel grafico2;
	private ChartSeries PAS2;
	private ChartSeries PAD2;
	private Integer maxValue;
	private Integer size;
	private boolean mostraGrafico;
	private boolean mostraGrafico2;
	private boolean mostraFiltro;
	private boolean mostraFiltro2;
	
	public GraficoHistoricoPressaoArterial(String titulo, String PAS, String PAD, Integer size) {
		this.maxValue = 0;
		this.size = size;
		if (size > 1){
			this.mostraGrafico = true;
			this.mostraFiltro = true;
			this.mostraGrafico2 = false;
			this.mostraFiltro2 = false;
			this.grafico = new LineChartModel();
			this.PAS = new LineChartSeries();
			this.PAD = new LineChartSeries();
			
			this.grafico.setTitle(titulo);
			this.PAS.setLabel(PAS);
			this.PAD.setLabel(PAD);
			this.PAS.setFill(true);
			this.PAD.setFill(true);
		}else{
			this.mostraGrafico = false;
			this.mostraFiltro = false;
			this.mostraGrafico2 = true;
			this.mostraFiltro2 = true;
			
			this.grafico2 = new BarChartModel();
			this.PAS2 = new ChartSeries();
			this.PAD2 = new ChartSeries();
			
			this.grafico2.setTitle(titulo);
			this.PAS2.setLabel(PAS);
			this.PAD2.setLabel(PAD);
		}
	}
	
	public void setDados(Date data, Integer PAS, Integer PAD) {
		if (PAS > this.maxValue) {
			this.maxValue = PAS;
		}
		
		if (size > 1){
			this.PAS.set(data.toString(), PAS);
			this.PAD.set(data.toString(), PAD);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("d/M/yy");
			String dataFormatada = sdf.format(data);
			this.PAS2.set(dataFormatada.toString(), PAS);
			this.PAD2.set(dataFormatada.toString(), PAD);
		}
	}

	public void addSeries(Integer size, long c, Date primeira, Date ultima) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dataAux = new Date();
		
		if (size > 1){
			this.grafico.addSeries(this.PAS);
			this.grafico.addSeries(this.PAD);
			
			this.grafico.setLegendCols(2);
			this.grafico.setZoom(true);
			this.grafico.setLegendPosition("n");
			//this.grafico.setShowPointLabels(true);
			//this.grafico.setSeriesColors("EAA228,4BB2C5,C5B47F,579575");
			// Peso = EAA228
			// Massa Magra = 4BB2C5
			// Massa Gorda = C5B47F
			// Massa Muscular = 579575
			
			Axis yAxis = this.grafico.getAxis(AxisType.Y);
			yAxis.setLabel("mmHg");
			//yAxis.setTickFormat("%.2f");
			yAxis.setMin(maxValue-maxValue);
			yAxis.setMax(this.maxValue + 50);
			
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
			this.grafico2.addSeries(this.PAS2);
			this.grafico2.addSeries(this.PAD2);
			
			this.grafico2.setLegendCols(2);
			this.grafico2.setZoom(true);
			this.grafico2.setLegendPosition("n");

			Axis yAxis = grafico2.getAxis(AxisType.Y);
			yAxis.setLabel("mmHg");
			//yAxis.setTickFormat("%.2f");
			yAxis.setMin(0);
			yAxis.setMax(this.maxValue + 50);
		}
	}

	public LineChartModel getGrafico() {
		return this.grafico;
	}
	
	public BarChartModel getGrafico2() {
		return this.grafico2;
	}
	
	public boolean isMostraGrafico() {
		return this.mostraGrafico;
	}
	
	public boolean isMostraGrafico2() {
		return this.mostraGrafico2;
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
			if(mostraGrafico2)
				MensagemUtil.mensagemInfo("Histórico da Pressão Arterial visível.");
			else
				MensagemUtil.mensagemInfo("Histórico da Pressão Arterial escondido.");
		}
		else{
			if(mostraGrafico)
				MensagemUtil.mensagemInfo("Histórico da Pressão Arterial visível.");
			else
				MensagemUtil.mensagemInfo("Histórico da Pressão Arterial escondido.");
		}
	}
}
