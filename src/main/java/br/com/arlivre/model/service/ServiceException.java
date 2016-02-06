package br.com.arlivre.model.service;

public class ServiceException extends Exception {
	
	private static final long serialVersionUID = -3665103049747828575L;

	public ServiceException(String mensagem) {
		super(mensagem);
	}
	
	public ServiceException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	public ServiceException(Throwable causa) {
		super(causa);
	}
}