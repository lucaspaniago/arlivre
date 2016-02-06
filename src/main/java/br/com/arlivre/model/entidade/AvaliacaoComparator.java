package br.com.arlivre.model.entidade;

import java.util.Comparator;

public class AvaliacaoComparator implements Comparator<Avaliacao> {
	@Override
	public int compare(Avaliacao avaliacao0, Avaliacao avaliacao1) {
		int i = avaliacao0.getData().compareTo(avaliacao1.getData());
		if (i!=0)
			return i;
		else
			return avaliacao0.getHora().compareTo(avaliacao1.getHora());
	}

}
