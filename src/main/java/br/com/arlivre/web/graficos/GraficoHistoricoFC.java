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

public class GraficoHistoricoFC {
	
	private LineChartModel grafico;
	private LineChartSeries FC;
	private BarChartModel grafico2;
	private ChartSeries FC2;
	private Integer maxValue;
	private Integer size;
	private boolean mostraGrafico;
	private boolean mostraGrafico2;
	private boolean mostraFiltro;
	private boolean mostraFiltro2;
	
	public GraficoHistoricoFC(String titulo, String FC, Integer size) {
		
		this.maxValue = 0;
		this.size = size;
		if (size > 1){
			this.mostraGrafico = true;
			this.mostraFiltro = true;
			this.mostraGrafico2 = false;
			this.mostraFiltro2 = false;
			this.grafico = new LineChartModel();
			this.FC = new LineChartSeries();
			
			this.grafico.setTitle(titulo);
			this.FC.setLabel(FC);
		}else{
			this.mostraGrafico = false;
			this.mostraFiltro = false;
			this.mostraGrafico2 = true;
			this.mostraFiltro2 = true;
			this.grafico2 = new BarChartModel();
			this.FC2 = new ChartSeries();
			
			this.grafico2.setTitle(titulo);
			this.FC2.setLabel(FC);
		}
	}
	
	public void setDados(Date data, Integer FC) {

		if (FC > this.maxValue)
			this.maxValue = FC;

		if (this.size > 1)
			this.FC.set(data.toString(), FC);
		else
			this.FC2.set(data.toString(), FC);
	}

	public void addSeries(Integer size, long c, Date primeira, Date ultima) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dataAux = new Date();
		
		if (size > 1){
			this.grafico.addSeries(this.FC);
			
			this.grafico.setZoom(true);
			this.grafico.setLegendPosition("n");
			//this.grafico.setShowPointLabels(true);
			//this.grafico.setSeriesColors("EAA228,4BB2C5,C5B47F,579575");
			// Peso = EAA228
			// Massa Magra = 4BB2C5
			// Massa Gorda = C5B47F
			// Massa Muscular = 579575
			
			Axis yAxis = this.grafico.getAxis(AxisType.Y);
			yAxis.setLabel("FC (bpm)");
			yAxis.setTickFormat("%.2f");
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
			this.grafico2.addSeries(this.FC2);

			Axis yAxis = grafico2.getAxis(AxisType.Y);
			yAxis.setLabel("bpm");
			yAxis.setTickFormat("%.2f");
			yAxis.setMin(0);
			yAxis.setMax(this.maxValue + 50);
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
			if(mostraGrafico2)
				MensagemUtil.mensagemInfo("Histórico da Frequência Cardíaca visível.");
			else
				MensagemUtil.mensagemInfo("Histórico da Frequência Cardíaca escondido.");
		}
		else{
			if(mostraGrafico)
				MensagemUtil.mensagemInfo("Histórico da Frequência Cardíaca visível.");
			else
				MensagemUtil.mensagemInfo("Histórico da Frequência Cardíaca escondido.");
		}
	}
}
